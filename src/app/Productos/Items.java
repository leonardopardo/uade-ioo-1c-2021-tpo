package app.Productos;

import controllers.PrecioController;
import dto.ItemDTO;
import modelos.enums.Rubro;
import modelos.enums.TipoItem;
import modelos.enums.Unidad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Items extends JDialog{
    private JPanel pnlMain;
    private JButton GUARDARButton;
    private JButton CANCELARButton;
    private JComboBox comboBoxTipo;
    private JComboBox comboBoxRubro;
    private JComboBox comboBoxUnidad;
    private JTextField textFieldTitulo;
    private JTextField textFieldCodigo;


    public Items(JFrame parent){
        this.setContentPane(this.pnlMain);
        this.setResizable(false);
        this.setModal(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(this.pnlMain.getPreferredSize());
        this.pack();
        this.positionScreen();
        this.setVisible(true);

        this.populateComboTipo();
        this.populateComboRubro();
        this.populateComboUnidad();

        this.actionGuardarNuevoItem();

    }

    void populateComboTipo(){
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<String>();
        this.comboBoxRubro.addItem("-- Seleccione --");
        comboBoxModel.addElement(TipoItem.PRODUCTO);
        comboBoxModel.addElement(TipoItem.SERVICIO);

        this.comboBoxTipo.setModel(comboBoxModel);
    }

    void populateComboUnidad(){
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<String>();
        this.comboBoxRubro.addItem("-- Seleccione --");
        comboBoxModel.addElement(Unidad.UNIDAD);
        comboBoxModel.addElement(Unidad.HORA);
        comboBoxModel.addElement(Unidad.PESO);

        this.comboBoxUnidad.setModel(comboBoxModel);
    }

    void populateComboRubro(){
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<String>();
        this.comboBoxRubro.addItem("-- Seleccione --");
        for (Rubro rubro : Rubro.values()) {
            this.comboBoxRubro.addItem(rubro);
            comboBoxModel.addElement(rubro);
        }

        this.comboBoxRubro.setModel(comboBoxModel);
    }

    void actionGuardarNuevoItem() {
        Items self = this;
        this.GUARDARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String codigoItem = self.textFieldCodigo.getText();
                    String tituloItem = self.textFieldTitulo.getText();

                    if (codigoItem.equals("")) {
                        throw new Exception("Debe asignar un código al item.");
                    }
                    if (tituloItem.equals("")) {
                        throw new Exception("Debe asignar un título al item.");
                    }

                    PrecioController.getInstance().agregar(self.valuestoItemDTO());
                    self.dispose();

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

    ItemDTO valuestoItemDTO() {
        ItemDTO iDto = new ItemDTO();
        iDto.codigo = this.textFieldCodigo.getText();
        iDto.titulo = this.textFieldTitulo.getText();
        iDto.rubro = Rubro.valueOf(this.comboBoxRubro.getSelectedItem().toString());
        iDto.unidad = Unidad.valueOf(this.comboBoxUnidad.getSelectedItem().toString());
        iDto.tipo = TipoItem.valueOf(this.comboBoxTipo.getSelectedItem().toString());

        return iDto;
    }

    void positionScreen() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2
        );
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
