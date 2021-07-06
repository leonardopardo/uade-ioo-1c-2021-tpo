package app.Catalogo.Item;

import controllers.PrecioController;
import dto.ItemDTO;
import modelos.enums.Rubro;
import modelos.enums.TipoItem;
import modelos.enums.Unidad;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Items extends JPanel {
    private JTable tableItems;
    private JPanel pnlMain;
    private JPanel pnlForm;
    private JPanel pnlTable;
    private JPanel pnlActions;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JButton eliminarButton;
    private JTextField textFieldCodigo;
    private JTextField textFieldTitulo;
    private JComboBox comboBoxTipo;
    private JComboBox comboBoxRubro;
    private JComboBox comboBoxUnidad;
    private JLabel lblCodigo;
    private JLabel lblTitulo;
    private JLabel lblTipo;
    private JLabel lblRubro;
    private JLabel lblUnidad;
    private PrecioController precioController;
    private DefaultTableModel tblModel;

    public Items() throws Exception{

        this.add(this.pnlMain);

        this.populateTableItems();
        this.populateComboRubro();
        this.populateComboTipo();
        this.populateComboUnidad();

        this.actionGuardar();
        this.actionEliminarItem();
        this.actionCancelarItem();
        this.actionSelectedRowItems();

        this.precioController = PrecioController.getInstance();
    }

    void populateInputs(String selectedRow) throws Exception {
        ItemDTO item = PrecioController.getInstance().obtenerItemPorTitulo(selectedRow);
        this.textFieldCodigo.setText(item.codigo);
        this.textFieldTitulo.setText(item.titulo);
        this.comboBoxRubro.setSelectedItem(item.rubro);
        this.comboBoxUnidad.setSelectedItem(item.unidad);
        this.comboBoxTipo.setSelectedItem(item.tipo);
    }

    void populateTableItems() {

        try {
            List<ItemDTO> items = PrecioController.getInstance().listarItems();

            String[] columns = new String[]{
                    "codigo".toUpperCase(),
                    "título".toUpperCase(),
                    "unidad".toUpperCase(),
                    "rubro".toUpperCase(),
                    "tipo".toUpperCase()
            };

            this.tblModel = new DefaultTableModel(columns, 0);

            items.stream().forEach(x -> {
                Object[] o = {
                        x.codigo,
                        x.titulo,
                        x.unidad,
                        x.rubro,
                        x.tipo
                };

                tblModel.addRow(o);
            });

            this.tableItems.setModel(tblModel);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(
                    pnlMain,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    void populateComboTipo(){
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<String>();
        comboBoxModel.addElement(TipoItem.PRODUCTO);
        comboBoxModel.addElement(TipoItem.SERVICIO);

        this.comboBoxTipo.setModel(comboBoxModel);
    }

    void populateComboUnidad(){
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<String>();
        comboBoxModel.addElement(Unidad.UNIDAD);
        comboBoxModel.addElement(Unidad.HORA);
        comboBoxModel.addElement(Unidad.PESO);

        this.comboBoxUnidad.setModel(comboBoxModel);
    }

    void populateComboRubro(){
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<String>();
        for (Rubro rubro : Rubro.values()) {
            comboBoxModel.addElement(rubro);
        }

        this.comboBoxRubro.setModel(comboBoxModel);
    }

    void actionSelectedRowItems() {
        this.tableItems.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable target = (JTable) e.getSource();
                try {
                    populateInputs(tableItems.getValueAt(target.getSelectedRow(), 1).toString());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    void actionGuardar() {
        Items self = this;
        this.guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PrecioController pController = PrecioController.getInstance();
                    ItemDTO check = pController.obtenerItemPorCodigo(self.textFieldCodigo.getText());

                    if (check != null) {
                        String message = "Usted está a punto de editar el item " + check.titulo + " ¿está seguro?";

                        int confirmResult = JOptionPane.showConfirmDialog(pnlMain, message,
                                "Actualizar item", JOptionPane.YES_NO_OPTION
                        );
                        if (confirmResult == JOptionPane.YES_OPTION) {
                            pController.actualizar(self.crearActualizarItem());
                        }
                    } else {
                        ItemDTO iDto = self.crearActualizarItem();
                        pController.agregar(iDto);
                    }
                    self.populateTableItems();

                    JOptionPane.showMessageDialog(
                            pnlMain,
                            "Item guardado",
                            "",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            pnlMain,
                            ex.getStackTrace(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    ItemDTO crearActualizarItem() {
        ItemDTO iDto = new ItemDTO();
        iDto.codigo = this.textFieldCodigo.getText();
        iDto.titulo = this.textFieldTitulo.getText();
        iDto.rubro = Rubro.valueOf(this.comboBoxRubro.getSelectedItem().toString());
        iDto.unidad = Unidad.valueOf(this.comboBoxUnidad.getSelectedItem().toString());
        iDto.tipo = TipoItem.valueOf(this.comboBoxTipo.getSelectedItem().toString());

        return iDto;
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

