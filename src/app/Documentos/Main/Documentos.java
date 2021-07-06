package app.Documentos.Main;

import app.Documentos.OrdenCompra.Formulario;
import app.Main.Main;
import app.Pagos.Ordenes;
import controllers.DocumentoController;
import controllers.OrdenPagoController;
import controllers.ProveedorController;
import dto.OrdenCompraDTO;
import dto.ProveedorDTO;
import helpers.Helpers;
import modelos.Factura;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
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
    private JButton btnLimpiarFiltros;
    private JComboBox comboBoxProveedoresFactura;
    private JTextField cuitDocFactura;
    private JTable tableFacturas;
    private JButton btnNuevaFactura;
    private JButton btnEliminarFactura;
    private JButton btnFiltrarFacturas;
    private JButton btnLimpiarFiltroFacturas;
    private JButton modificarOrdenButton;
    private JButton modificarFacturaButton;
    private JPanel fechaDesdeFactura;
    private JPanel fechaHastaFactura;
    private JDatePickerImpl factFechaDesde;
    private JDatePickerImpl factFechaHasta;
    private JDatePickerImpl ocFechaDesde;
    private JDatePickerImpl ocFechaHasta;
    private JDialog frmOrdenCompra;
    private JDialog frmFactura;
    private List<OrdenCompraDTO> ordenes;
    private List<Factura> facturas;

    public Documentos(String title) throws Exception {

        super(title);

        //region Register Actions
        this.closeModule();
        this.actionSelectedOCProveedor();
        this.actionSelectedRowOrdenesCompra();
        this.actionNuevaOrden();
        this.actionEliminarOrden();
        this.actionOnClickBtnFiltrarOC();
        this.actionLimpiarFiltros();

        this.actionNuevaFactura();
        //endregion

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
        this.ordenes = DocumentoController.getInstance().listarOrdenes();
        //endregion

        //region Factory Elements
        this.ocFechaDesde = Helpers.nuevoDatePicker();
        Helpers.appendDatePicker(this.pnlOCFormFechaDesdeDP, this.ocFechaDesde);

        this.ocFechaHasta = Helpers.nuevoDatePicker();
        Helpers.appendDatePicker(this.pnlOCFormFechaHastaDP, this.ocFechaHasta);

        this.factFechaDesde = Helpers.nuevoDatePicker();
        Helpers.appendDatePicker(this.fechaDesdeFactura, this.factFechaDesde);

        this.factFechaHasta = Helpers.nuevoDatePicker();
        Helpers.appendDatePicker(this.fechaHastaFactura, this.factFechaHasta);
        //endregion

        //region Populate
        this.populateComboBoxProevedores();
        this.populateOCTable();
        //endregion

    }

    //region Ordenes

    //region Populate
    void populateComboBoxProevedores() {
        try {
            List<ProveedorDTO> proveedores = ProveedorController.getInstance().listar();

            this.comboBoxOCProveedores.addItem("-- Seleccione --");
            this.comboBoxProveedoresFactura.addItem("-- Seleccione --");
            proveedores.stream().forEach(x -> {
                this.comboBoxOCProveedores.addItem(x.razonSocial);
                this.comboBoxProveedoresFactura.addItem(x.razonSocial);
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
            List<OrdenCompraDTO> ordenes = this.ordenes;

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
                        x.cuit
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
                    self.populateOCTable();
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

    void actionSelectedRowOrdenesCompra() {
        this.tableOrdenesCompra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable target = (JTable) e.getSource();
                try {
                    System.out.println(tableOrdenesCompra.getValueAt(target.getSelectedRow(), 0).toString());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    void actionEliminarOrden() {
        Documentos self = this;
        this.eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ProveedorController pController = ProveedorController.getInstance();
                    //Integer numeroOrden = self.
                } catch (Exception exception) {
                    exception.printStackTrace();
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

    void actionOnClickBtnFiltrarOC() {
        Documentos self = this;
        this.btnFiltrarOC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String cuit = self.textFieldOCFormCUIT.getText();

                    LocalDate fechaDesde = null;

                    LocalDate fechaHasta = null;

                    if (self.ocFechaDesde.getJFormattedTextField().getValue() != null)
                        fechaDesde = Helpers.datePickerFormatter(self.ocFechaDesde);

                    if (self.ocFechaHasta.getJFormattedTextField().getValue() != null)
                        fechaHasta = Helpers.datePickerFormatter(self.ocFechaHasta);

                    if (fechaDesde != null
                            && fechaHasta != null
                            && !(fechaDesde.isBefore(fechaHasta) || fechaDesde.equals(fechaHasta))) {

                        throw new Exception("La fecha desde no puede ser mayor que la fecha hasta.");
                    }

                    DocumentoController controller = DocumentoController.getInstance();

                    self.ordenes = null;

                    if (!cuit.equals("") && fechaDesde == null && fechaHasta == null) { // si viene cuit y no vienen fechas
                        self.ordenes = controller.listarOrdenes(cuit);
                    } else if (!cuit.equals("") && fechaDesde == null && fechaHasta != null) { // si viene cuit y fecha hasta
                        self.ordenes = controller.listarOrdenes(cuit, LocalDate.MIN, fechaHasta);
                    } else if (!cuit.equals("") && fechaDesde != null && fechaHasta == null) { // si viene cuit y fecha desde
                        self.ordenes = controller.listarOrdenes(cuit, fechaDesde, LocalDate.MAX);
                    } else if (!cuit.equals("") && fechaDesde != null && fechaHasta != null) { // si viene el cuit y las dos fechas
                        self.ordenes = controller.listarOrdenes(cuit, fechaDesde, fechaHasta);
                    } else if (!cuit.equals("") && fechaDesde != null && fechaHasta != null && fechaDesde.equals(fechaHasta)) { // si viene las fechas son iguales
                        self.ordenes = controller.listarOrdenes(cuit, fechaDesde);
                    } else if (cuit.equals("") && fechaDesde != null && fechaHasta != null) { // si viene fecha desde y fecha hasta
                        self.ordenes = controller.listarOrdenes(fechaDesde, fechaHasta);
                    } else if (cuit.equals("") && fechaDesde != null && fechaHasta == null) { // si viene solo fecha desde
                        self.ordenes = controller.listarOrdenes(fechaDesde, LocalDate.MAX);
                    } else if (cuit.equals("") && fechaDesde == null && fechaHasta != null) { // si viene solo fecha hasta
                        self.ordenes = controller.listarOrdenes(LocalDate.MIN, fechaHasta);
                    } else if (cuit.equals("") && fechaDesde != null && fechaHasta != null && fechaDesde.equals(fechaHasta)) {
                        self.ordenes = controller.listarOrdenes(fechaDesde);
                    } else {
                        self.ordenes = controller.listarOrdenes(); // no viene nada
                    }

                    self.populateOCTable();

                    self.actionLimpiarFiltros();

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

    void actionLimpiarFiltros() {
        Documentos self = this;

        this.btnLimpiarFiltros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    self.ocFechaDesde.getJFormattedTextField().setValue(null);
                    self.ocFechaHasta.getJFormattedTextField().setValue(null);
                    self.textFieldOCFormCUIT.setText("");
                    self.comboBoxOCProveedores.setSelectedIndex(0);

                    self.ordenes = DocumentoController.getInstance().listarOrdenes();
                    self.populateOCTable();
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

    //endregion

    //region Facturas
    void actionNuevaFactura() {
        Documentos self = this;
        this.btnNuevaFactura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    self.frmFactura = new app.Documentos.Factura.Formulario(self);
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
