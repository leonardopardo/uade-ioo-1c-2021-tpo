package app.Documentos.OrdenCompra;

import controllers.PrecioController;
import controllers.ProveedorController;
import dto.DetalleDTO;
import dto.ItemDTO;
import dto.ProveedorDTO;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Formulario extends JDialog {

    private JPanel pnlMain;
    private JTable tblDetalle;
    private JComboBox comboBoxItem;
    private JPanel pnlItem;
    private JLabel lblCodigoItem;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JComboBox comboBoxProveedor;
    private JButton btnAgregarDetalle;
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
    private JSpinner spinnerCantidad;
    private JComboBox comboBoxItemTitulo;
    private JTextField textFieldCodigoItem;
    private List<DetalleDTO> detalle;
    private JDatePickerImpl datePickerfecha;

    public Formulario(JFrame parent) {
        super(parent);
        this.detalle = new ArrayList<>();
        this.textFieldCuit.setEnabled(false);

        //region Factory
        this.datePickerfecha = this.nuevoDatePicker();
        this.appendDatePicker(this.pnlFechaDP, this.datePickerfecha);
        //endregion

        //region Default Values
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
        this.datePickerfecha.getJFormattedTextField().setText(LocalDate.now().format(formatters).toString());
        //endregion

        //region Populate
        this.populateComboBoxProveedores();
        this.populateComboBoxItemTitulo();
        //endregion

        //region Actions
        this.actionSelectedComboBoxProveedor();
        this.actionSelectedComboBoxItem();
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
        this.setTableSchemma();
        //endregion
    }

    //region Factory
    JDatePickerImpl nuevoDatePicker(){
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        datePicker.setLayout(new GridLayout());
        datePicker.setPreferredSize(new Dimension(100, 30));
        datePicker.setMinimumSize(new Dimension(100, 30));

        return datePicker;
    }

    void appendDatePicker(JPanel panel, JDatePickerImpl picker){
        panel.setLayout(new GridLayout());
        panel.add(picker);
    }
    //endregion

    //region Populate
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

    void populateComboBoxItemTitulo(){
        try {
            List<ItemDTO> items = PrecioController.getInstance().listarItems();

            this.comboBoxItemTitulo.addItem("-- Seleccione --");

            items.forEach(item -> {
                this.comboBoxItemTitulo.addItem(item.titulo);
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
    //endregion

    //region Actions
    void actionSelectedComboBoxProveedor(){
        Formulario self = this;
        this.comboBoxProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String razonSocial = self.comboBoxProveedor.getSelectedItem().toString();

                    ProveedorDTO proveedor = ProveedorController.getInstance().obtenerPorRazonSocial(razonSocial);

                    if(proveedor == null)
                        self.textFieldCuit.setText("");
                    else
                        self.textFieldCuit.setText(proveedor.cuit);

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

    void actionSelectedComboBoxItem(){
        Formulario self = this;
        this.comboBoxItemTitulo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String selectItemCod = self.comboBoxItemTitulo.getSelectedItem().toString();

                    ItemDTO item = PrecioController.getInstance().obtenerItemPorTitulo(selectItemCod);

                    if(item != null)
                        self.textFieldCodigoItem.setText(item.codigo);
                    else
                        self.textFieldCodigoItem.setText("");

                } catch(Exception ex){
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
    //endregion

    //region Settings
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
    void positionScreen(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width/2-this.getSize().width/2,
                dim.height/2-this.getSize().height/2
        );
    }
    //endregion

}
