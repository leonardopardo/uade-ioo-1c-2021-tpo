package app.Documentos.OrdenCompra;

import controllers.ProveedorController;
import dto.DetalleDTO;
import dto.ProveedorDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Formulario extends JDialog {

    private JPanel pnlMain;
    private JTable tblDetalle;
    private JComboBox comboBoxItem;
    private JPanel pnlItem;
    private JLabel lblItem;
    private JComboBox comboBoxCantidad;
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
    private List<DetalleDTO> detalle;

    public Formulario(JFrame parent) {
        super(parent);
        this.detalle = new ArrayList<>();

        //region Settings
        this.setContentPane(this.pnlMain);
        this.setResizable(false);
        this.setModal(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(this.pnlMain.getPreferredSize());
        this.pack();
        this.positionScreen();
        this.setVisible(true);
        this.setTableSchemma();
        //endregion

        //region Populate
        this.populateComboBoxProveedores();
        //endregion
    }

    void setTableSchemma(){
        try{
            String[] columns = new String[]{
                "CÓDIGO",
                "DESCRIPCIÓN",
                "CANTIDAD",
            };

            DefaultTableModel tblModel = new DefaultTableModel(columns, 0);

            this.detalle.forEach(d->{
                Object[] o = {
                    d.codItem,
                    d.descripcion,
                    d.cantItem
                };

                tblModel.addRow(o);
            });

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

    void populateComboBoxProveedores(){
        try {

            List<ProveedorDTO> proveedores = ProveedorController.getInstance().listar();

            this.comboBoxProveedor.addItem("-- Seleccione --");

            proveedores.forEach(p -> {
                this.comboBoxProveedor.addItem(p.razonSocial);
            });

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
