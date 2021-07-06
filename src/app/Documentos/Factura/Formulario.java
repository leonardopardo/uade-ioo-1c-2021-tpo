package app.Documentos.Factura;

import modelos.enums.Unidad;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Formulario extends JDialog{
    private JPanel pnlFormCabecera;
    private JLabel lblProveedor;
    private JComboBox comboBoxProveedor;
    private JLabel lblFecha;
    private JLabel lblCuit;
    private JTextField textFieldCuit;
    private JPanel pnlFormDetalle;
    private JPanel pnlItem;
    private JLabel lblItem;
    private JLabel lblCantidad;
    private JComboBox comboBox1;
    private JButton btnAgregarDetalle;
    private JButton eliminarButton;
    private JLabel lblDescription;
    private JPanel pnlTable;
    private JTable tblDetalle;
    private JPanel pnlActions;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JPanel pnlMain;
    private JComboBox comboBoxRubros;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField5;
    private JComboBox comboBox2;
    private JTextField textField4;
    private JTextField textField6;

    public Formulario(JFrame parent) {
        super(parent);

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

    //region Settings
    void positionScreen() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2
        );
    }
    //endregion
}
