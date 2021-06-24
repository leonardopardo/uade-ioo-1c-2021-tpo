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
    private JTextField textFieldInicio;
    private JTextField textFieldFin;
    private JLabel lblCodigo;
    private JLabel lblTitulo;
    private JLabel lblTipo;
    private JLabel lblRubro;
    private JLabel lblUnidad;
    private JLabel lblFin;
    private JLabel lblInicio;
    private JPanel pnlFecha;
    private PrecioController precioController;

    public Items() throws Exception{

        this.add(this.pnlMain);
        this.pnlFecha.setVisible(false);

        populateTableItems();
        populateComboRubro();
        populateComboTipo();
        populateComboUnidad();
        actionSelectedTipo();

        this.actionGuardarItem();

        this.precioController = PrecioController.getInstance();
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

            DefaultTableModel tblModel = new DefaultTableModel(columns, 0);

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

    void actionSelectedTipo(){

        Items self = this;

        this.comboBoxTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = self.comboBoxTipo.getSelectedItem().toString();

                if(value.equals(TipoItem.SERVICIO.name())){
                    self.pnlFecha.setVisible(true);
                } else {
                    self.pnlFecha.setVisible(false);
                }

            }
        });
    }

    void actionGuardarItem() {
        Items self = this;

        this.guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ItemDTO iDto = new ItemDTO();
                    iDto.codigo = self.textFieldCodigo.getText();
                    iDto.titulo = self.textFieldTitulo.getText();
                    iDto.rubro = Rubro.valueOf(self.comboBoxRubro.getSelectedItem().toString());
                    iDto.unidad = Unidad.valueOf(self.comboBoxUnidad.getSelectedItem().toString());
                    iDto.tipo = TipoItem.valueOf(self.comboBoxTipo.getSelectedItem().toString());

                    self.precioController.agregar(iDto);


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
}

