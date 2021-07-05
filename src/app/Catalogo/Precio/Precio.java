package app.Catalogo.Precio;

import app.Catalogo.Item.Items;
import app.Proveedores.Main.Proveedores;
import controllers.PrecioController;
import controllers.ProveedorController;
import dto.CompulsaPrecioDTO;
import dto.ItemDTO;
import dto.ProveedorDTO;
import modelos.Proveedor;
import modelos.enums.Role;
import modelos.enums.Rubro;
import modelos.enums.TipoItem;
import modelos.enums.Unidad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JTextField txtITem;
    private JLabel Item;
    private JTextField txtprecio;
    private JPanel pnlRubro;
    private JPanel pnlProveedor;
    private JComboBox comboBoxRubro;
    private JLabel lblRubro;
    private JComboBox comboBoxProveedor;
    private JLabel lblProveedor;
    private JTable table1;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JButton eliminarButton;
    private JList listaProveedor;

    public Precio() throws Exception{
        this.add(this.pnlMain);
        this.populateComboRubro();
        this.populateComboProveedor();


    }

    void populateComboRubro(){
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<String>();
        for (Rubro rubro : Rubro.values()){
            comboBoxModel.addElement(rubro);
        }
    }

    void populateComboProveedor() throws Exception{

        DefaultListModel model = new DefaultListModel();

        for(ProveedorDTO proveedor : ProveedorController.getInstance().listar()){
            this.comboBoxProveedor.addItem(proveedor.razonSocial);
            model.addElement(proveedor);
        }

        listaProveedor.setModel(model);

    }

    void actionGuardarPrecio() {
        Precio self = this;

        this.guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CompulsaPrecioDTO precioDTO = new CompulsaPrecioDTO();
                    precioDTO.itemTitulo = self.txtITem.getText();
                    precioDTO.rubro = Rubro.valueOf(self.comboBoxRubro.getSelectedItem().toString());

                    for (int i = 0; i < this.listaProveedor.getModel().getSize(); i++) {
                        Rubro r = Rubro.valueOf(this.listRubros.getModel().getElementAt(i).toString());
                        pDto.rubros.add(r);
                    }
                }

                    iDto.codigo = self.textFieldCodigo.getText();
                    iDto.titulo = self.textFieldTitulo.getText();
                    iDto.rubro = Rubro.valueOf(self.comboBoxRubro.getSelectedItem().toString());
                    iDto.unidad = Unidad.valueOf(self.comboBoxUnidad.getSelectedItem().toString());
                    iDto.tipo = TipoItem.valueOf(self.comboBoxTipo.getSelectedItem().toString());

                    self.precioController.agregar(iDto);
                    tblModel.addRow(new Object[]{iDto.codigo, iDto.titulo, iDto.unidad, iDto.rubro, iDto.tipo});

                    JOptionPane.showMessageDialog(
                            pnlMain,
                            "Item guardado",
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
                textFieldCodigo.setText("");
                textFieldTitulo.setText("");
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
                if(tableItems.getSelectedRow() != -1) {
                    tblModel.removeRow(tableItems.getSelectedRow());

                    JOptionPane.showMessageDialog(
                            pnlMain,
                            "Item eliminado",
                            "",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });
    }






}
