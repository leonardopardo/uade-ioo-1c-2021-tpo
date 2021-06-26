package app.Documentos.OrdenCompra;

import controllers.DocumentosController;
import dto.OrdenCompraDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Formulario extends JDialog {

    private JPanel pnlMain;
    private JTable tblDetalle;
    private JComboBox comboBoxItem;
    private JPanel pnlItem;
    private JLabel lblItem;
    private JComboBox comboBox1;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JComboBox comboBoxProveedor;
    private JButton btnAgregarDetalle;
    private JTextField textFieldDescription;
    private JTextField textFieldCuit;
    private JLabel lblCantidad;
    private JLabel lblDescription;
    private JPanel pnlFechaDP;
    private JLabel lblFecha;
    private JLabel lblCuit;
    private JLabel lblProveedor;
    private JButton eliminarButton;
    private JPanel pnlTable;
    private JPanel pnlFormDetalle;
    private JPanel pnlFormCabecera;
    private JPanel pnlActions;

    public Formulario() {
        this.setContentPane(this.pnlMain);
        this.setResizable(false);
        this.setModal(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(this.pnlMain.getPreferredSize());
        this.pack();
        this.positionScreen();
        this.setVisible(true);

        this.setTableSchemma();
    }

    public Formulario(Integer id){
        // code here for update ...
    }

    void setTableSchemma(){
        try{

            String[] columns = new String[]{
                "CÓDIGO",
                "DESCRIPCIÓN",
                "CANTIDAD",
            };

            DefaultTableModel tblModel = new DefaultTableModel(columns, 0);

            this.tblDetalle.setModel(tblModel);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    pnlMain,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    void populateTablaDetalle(){
        try {
            //  populate table
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    pnlMain,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    void positionScreen(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width/2-this.getSize().width/2,
                dim.height/2-this.getSize().height/2
        );
    }
}
