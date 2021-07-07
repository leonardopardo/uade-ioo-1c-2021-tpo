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

public class Items extends JDialog {
    private JPanel pnlMain;
    private JButton GUARDARButton;
    private JButton CANCELARButton;
    private JComboBox comboBoxTipo;
    private JComboBox comboBoxRubro;
    private JComboBox comboBoxUnidad;
    private JTextField textFieldTitulo;
    private JTextField textFieldCodigo;
    private JLabel lblRubro;
    private JLabel lblTipo;
    private JLabel lblCodigo;
    private JLabel lblUnidad;
    private ItemDTO item;

    public Items(JFrame parent) {
        super(parent);

        //region Populate
        this.populateComboTipo();
        this.populateComboRubro();
        this.populateComboUnidad();
        //endregion

        //region Actions
        this.actionGuardarNuevoItem();
        this.actionCancelarNuevoItem();
        //endregion

        //region Settings
        this.setContentPane(this.pnlMain);
        this.setResizable(false);
        this.setModal(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(this.pnlMain.getPreferredSize());
        this.pack();
        this.positionScreen();
        this.setVisible(true);
        //endregion
    }

    public Items(JFrame parent, ItemDTO item) {
        super(parent);

        this.item = item;

        //region Populate
        this.populateComboTipo();
        this.populateComboRubro();
        this.populateComboUnidad();
        this.loadedFields();
        //endregion

        //region Actions
        this.actionGuardarNuevoItem();
        this.actionCancelarNuevoItem();
        //endregion

        //region Settings
        this.setContentPane(this.pnlMain);
        this.setResizable(false);
        this.setModal(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(this.pnlMain.getPreferredSize());
        this.pack();
        this.positionScreen();
        this.setVisible(true);
        //endregion
    }

    void loadedFields() {
        this.textFieldTitulo.setText(this.item.titulo);
        this.textFieldCodigo.setText(this.item.codigo);
        this.comboBoxRubro.setSelectedItem(this.item.rubro);
        this.comboBoxUnidad.setSelectedItem(this.item.unidad);
        this.comboBoxTipo.setSelectedItem(this.item.tipo);
    }

    void populateComboTipo() {
        this.comboBoxTipo.addItem("-- Seleccione --");
        for (TipoItem t:TipoItem.values()) {
            this.comboBoxTipo.addItem(t);
        }
    }

    void populateComboUnidad() {
        this.comboBoxUnidad.addItem("-- Seleccione --");
        for (Unidad u : Unidad.values()) {
            this.comboBoxUnidad.addItem(u);
        }
    }

    void populateComboRubro() {
        this.comboBoxRubro.addItem("-- Seleccione --");
        for (Rubro rubro : Rubro.values()) {
            this.comboBoxRubro.addItem(rubro);
        }
    }

    void actionGuardarNuevoItem() {
        Items self = this;
        this.GUARDARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String codigoItem = self.textFieldCodigo.getText();
                    String tituloItem = self.textFieldTitulo.getText();

                    if(self.comboBoxRubro.getSelectedItem().equals(self.comboBoxTipo.getItemAt(0))) {
                        throw new Exception("Debe seleccionar un rubro para el item.");
                    }
                    if(self.comboBoxTipo.getSelectedItem().equals(self.comboBoxTipo.getItemAt(0))) {
                        throw new Exception("Debe seleccionar un tipo de item.");
                    }
                    if (codigoItem.equals("")) {
                        throw new Exception("Debe asignar un código al item.");
                    }
                    if(self.comboBoxUnidad.getSelectedItem().equals(self.comboBoxUnidad.getItemAt(0))) {
                        throw new Exception("Debe seleccionar una unidad de medida para el item.");
                    }
                    if (tituloItem.equals("")) {
                        throw new Exception("Debe asignar un título al item.");
                    }

                    ItemDTO item = self.valuestoItemDTO();

                    if(self.item == null) {
                        PrecioController.getInstance().agregar(item);
                    } else {
                        PrecioController.getInstance().actualizar(item);
                    }

                    Productos parent = (Productos) self.getParent();
                    parent.loadItems();
                    parent.loadTableItems();

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
                            ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    void actionCancelarNuevoItem() {
        Items self = this;
        this.CANCELARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.dispose();
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
}
