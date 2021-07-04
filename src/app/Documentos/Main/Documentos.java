package app.Documentos.Main;

import app.Documentos.OrdenCompra.Formulario;
import app.Main.Main;
import controllers.ProveedorController;
import dto.OrdenCompraDTO;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Properties;

public class Documentos extends JFrame {
    private JPanel pnlMain;
    private JPanel pnlHeader;
    private JPanel pnlBody;
    private JTabbedPane tabbedPaneMain;
    private JLabel lblIcono;
    private JPanel pnlOrdenCompra;
    private JPanel pnlFactura;
    private JTable tableOrdenesCompra;
    private JButton btnNuevaOrden;
    private JComboBox comboBoxOCProveedores;
    private JButton btnFiltrarOC;
    private JPanel pnlOCform;
    private JPanel pnlOCTable;
    private JPanel pnlOCActions;
    private JLabel lblOCProveedores;
    private JPanel pnlOCFormProveedores;
    private JPanel pnlOCFormFechaDesde;
    private JPanel pnlOCFormFechaHasta;
    private JPanel pnlOCFormFechaDesdeDP;
    private JPanel pnlOCFormFechaHastaDP;
    private JTextField textFieldOCFormCUIT;
    private JPanel pnlOCFormCUIT;
    private JButton eliminarButton;
    private JButton limpiarFiltroButton;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JTable tableFacturas;
    private JButton guardarButton;
    private JButton eliminarButton1;
    private JButton filtrarButton;
    private JButton limpiarFiltroButton1;
    private JDatePickerImpl opFechaInicio;
    private JDatePickerImpl opFechaFin;
    private Formulario frmOrdenCompra;

    public Documentos(String title) {

        super(title);

        //region Settings
        this.setContentPane(this.pnlMain);
        this.setSize(pnlMain.getPreferredSize());
        this.setResizable(false);
        this.positionScreen();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        //endregion

        //region Default Values
        this.textFieldOCFormCUIT.setEnabled(false);
        //endregion

        //region Factory Elements
        this.opFechaInicio = this.nuevoDatePicker();
        this.appendDatePicker(this.pnlOCFormFechaDesdeDP, opFechaInicio);

        this.opFechaFin = this.nuevoDatePicker();
        this.appendDatePicker(this.pnlOCFormFechaHastaDP, opFechaFin);
        //endregion

        //region Populate
        this.populateOCComboBoxProveedores();
        this.populateOCTable();
        //endregion

        //region Register Actions
        this.closeModule();
        this.actionSelectedOCProveedor();
        this.actionNuevaOrden();
        //endregion

    }

    //region Factory
    JDatePickerImpl nuevoDatePicker() {
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

        return datePicker;
    }

    void appendDatePicker(JPanel panel, JDatePickerImpl picker) {
        panel.setLayout(new GridLayout());
        panel.add(picker);
    }
    //endregion

    //region Populate
    void populateOCComboBoxProveedores() {
        try {
            List<ProveedorDTO> proveedores = ProveedorController.getInstance().listar();

            this.comboBoxOCProveedores.addItem("-- Seleccione --");
            proveedores.stream().forEach(x -> {
                this.comboBoxOCProveedores.addItem(x.razonSocial);
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

    void populateOCTable() {
        try {
            List<OrdenCompraDTO> ordenes = ProveedorController.getInstance().listarOrdenes();

            String[] columns = new String[]{
                    "NUMERO",
                    "FECHA",
                    "RAZON SOCIAL",
                    "CUIT"
            };

            DefaultTableModel tblModel = new DefaultTableModel(columns, 0);

            ordenes.stream().forEach(x -> {
                Object[] o = {
                        x.numero,
                        x.fecha,
                        x.razonSocial.toUpperCase(),
                        x.cuitProveedor
                };

                tblModel.addRow(o);
            });

            this.tableOrdenesCompra.setModel(tblModel);
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
    void closeModule() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    Main m = null;
                    m = new Main("Main");
                    m.setVisible(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }


    void actionNuevaOrden() {

        Documentos self = this;

        this.btnNuevaOrden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    self.frmOrdenCompra = new Formulario(self);

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

    void actionSelectedOCProveedor() {

        Documentos self = this;

        this.comboBoxOCProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String razonSocial = self.comboBoxOCProveedores.getSelectedItem().toString();
                    ProveedorDTO dto = ProveedorController.getInstance().obtenerPorRazonSocial(razonSocial);

                    if (dto != null) {
                        self.textFieldOCFormCUIT.setText(dto.cuit);
                    } else {
                        self.textFieldOCFormCUIT.setText("");
                    }

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
    //endregion

    //region Load
    void positionScreen() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2
        );
    }

    //endregion
}
