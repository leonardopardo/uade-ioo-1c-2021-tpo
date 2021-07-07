package app.Catalogo.Precio;

import controllers.PrecioController;
import controllers.ProveedorController;
import dto.*;
import modelos.enums.Rubro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Precio extends JPanel{
    private JPanel pnlMain;
    private JPanel pnlForm;
    private JPanel pnlTable;
    private JPanel pnlEjecutar;
    private JPanel pnlCodigo;
    private JPanel pnlItem;
    private JPanel pnlprecio;
    private JTextField txtCodigo;
    private JLabel lblCodigo;
    private JComboBox comboBoxItem;
    private JLabel Item;
    private JTextField txtprecio;
    private JPanel pnlRubro;
    private JPanel pnlProveedor;
    private JComboBox comboBoxRubro;
    private JLabel lblRubro;
    private JComboBox comboBoxProveedor;
    private JLabel lblProveedor;
    private JTable tablePrecio;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JButton eliminarButton;
    private JList listaProveedor;
    private PrecioController precioController;
    private DefaultTableModel tblModel;

    public Precio() throws Exception{
        this.add(this.pnlMain);
        this.populateTablePrecio();
        this.populateComboRubro();
        this.populateComboProveedor();
        this.populateComboBoxItem();
        this.txtCodigo.setEnabled(false);

        this.actionSelectedItem();
        this.actionGuardarPrecio();
        this.actionCancelarItem();
        this.actionEliminarItem();
        this.precioController = PrecioController.getInstance();

    }

    void populateTablePrecio() {
        try {
            List<PrecioDTO> precios = PrecioController.getInstance().listarPrecios();

            String[] columns = new String[]{
                    "CODIGO",
                    "ITEM",
                    "RUBRO",
                    "PROVEEDOR",
                    "PRECIO UNITARIO"
            };

            this.tblModel = new DefaultTableModel(columns, 0);

            precios.stream().forEach(x -> {
                Object[] o = {
                        x.itemCodigo,
                        x.itemTitulo,
                        x.rubro,
                        x.razonSocial,
                        x.precio
                };
                tblModel.addRow(o);
            });

            this.tablePrecio.setModel(tblModel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    pnlMain,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    void populateComboRubro(){
        for (Rubro rubro : Rubro.values()){
            this.comboBoxRubro.addItem(rubro);
        }
    }

    void populateComboProveedor() throws Exception{

        for(ProveedorDTO proveedor : ProveedorController.getInstance().listar()){
            this.comboBoxProveedor.addItem(proveedor.razonSocial);
        }

    }

    void populateComboBoxItem() throws Exception {
        for (ItemDTO item : PrecioController.getInstance().listarItems()){
            this.comboBoxItem.addItem(item.titulo);
        }
    }

    void actionSelectedItem() {
        Precio self = this;
        this.comboBoxItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String titulo = self.comboBoxItem.getSelectedItem().toString();
                    ItemDTO dto = PrecioController.getInstance().obtenerItemPorTitulo(titulo);

                    if (dto != null) {
                        self.txtCodigo.setText(dto.codigo);
                    } else {
                        self.txtCodigo.setText("");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            pnlMain,
                            ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    void actionGuardarPrecio() {
        Precio self = this;

        this.guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PrecioDTO precioDTO = new PrecioDTO();
                    String titulo = self.comboBoxItem.getSelectedItem().toString();
                    String razonSocial = self.comboBoxProveedor.getSelectedItem().toString();
                    precioDTO.itemTitulo = PrecioController.getInstance().obtenerItemPorTitulo(titulo).titulo;
                    precioDTO.rubro = Rubro.valueOf(self.comboBoxRubro.getSelectedItem().toString());
                    precioDTO.precio = Double.parseDouble(self.txtprecio.getText().trim());
                    precioDTO.razonSocial = ProveedorController.getInstance().obtenerProveedorPorRazonSocial(razonSocial).getRazonSocial();

                    self.precioController.agregar(precioDTO);
                    self.populateTablePrecio();

                    JOptionPane.showMessageDialog(
                            pnlMain,
                            "Precio guardado",
                            "",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            pnlMain,
                            ex.getMessage(),
                            "",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    void actionCancelarItem() {
        this.cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtCodigo.setText("");
                txtprecio.setText("");
                JOptionPane.showMessageDialog(
                        pnlMain,
                        "Item cancelado",
                        "",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }

    void actionEliminarItem() {
        this.eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tablePrecio.getSelectedRow() != -1) {
                    tblModel.removeRow(tablePrecio.getSelectedRow());

                    JOptionPane.showMessageDialog(
                            pnlMain,
                            "Precio eliminado",
                            "",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });
    }

}
