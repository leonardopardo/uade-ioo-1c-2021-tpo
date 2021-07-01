package app.Catalogo.Precio;

import app.Catalogo.Item.Items;
import controllers.PrecioController;
import controllers.ProveedorController;
import dto.ItemDTO;
import dto.ProveedorDTO;
import modelos.Proveedor;
import modelos.enums.Rubro;
import modelos.enums.TipoItem;
import modelos.enums.Unidad;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Precio extends JPanel {

    private JPanel pnlMain;
    private JPanel pnlForm;
    private JPanel pnlEjecutar;
    private JPanel pnlTable;
    private JTextField txtCodigo;
    private JLabel lblCodigo;
    private JPanel pnlCodigo;
    private JTextField txtITem;
    private JPanel pnlItem;
    private JLabel Item;
    private JPanel pnlprecio;
    private JTextField txtprecio;
    private JLabel lblRubro;
    private JPanel pnlRubro;
    private JPanel pnlProveedor;
    private JComboBox comboBoxProveedor;
    private JLabel lblProveedor;
    private JComboBox comboBoxRubro;
    private JTable table1;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JButton eliminarButton;


    public Precio() throws Exception{
        this.add(this.pnlMain);
        this.populateComboRubro();
        this.populateComboProveedor();



     }

    void populateComboRubro(){
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<String>();
        for (Rubro rubro : Rubro.values()) {
            comboBoxModel.addElement(rubro);
        }

        this.comboBoxRubro.setModel(comboBoxModel);
    }

    void populateComboProveedor() throws Exception {
        // DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<String>();
        for (ProveedorDTO proveedor : ProveedorController.getInstance().listar()) {
            this.comboBoxProveedor.addItem(proveedor.razonSocial);
        }

       // this.comboBoxProveedor.setModel(comboBoxModel);
    }

}
