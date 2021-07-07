package app.Pagos;

import app.Main.Main;
import controllers.DocumentoController;
import controllers.OrdenPagoController;
import controllers.ProveedorController;
import dto.OrdenPagoDTO;
import dto.ProveedorDTO;
import helpers.Helpers;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Ordenes extends JFrame{
    private JPanel pnlMain;
    private JPanel pnlHeader;
    private JPanel pnlBody;
    private JPanel pnlIcon;
    private JLabel lblIcon;
    private JPanel pnlTitle;
    private JLabel lblTitle;
    private JPanel pnlForm;
    private JPanel pnlTable;
    private JTable tablePagos;
    private JPanel pnlActions;
    private JButton btnNuevaOrden;
    private JButton btnEliminar;
    private JButton btnFiltrar;
    private JComboBox comboBoxProveedores;
    private JTextField textFieldCUIT;
    private JButton btnLimpiarFiltro;
    private JPanel pnlFrmProveedor;
    private JPanel pnlFrmCUIT;
    private JPanel pnlFRMFechaDesde;
    private JPanel pnlFRMFechaHasta;
    private JLabel lblFechaDesde;
    private JPanel pnlContainerFechaDesde;
    private JLabel lblFechaHasta;
    private JPanel pnlContainerFechaHasta;
    private JLabel lblProveedores;
    private JLabel lblCUIT;
    private JButton btnActualizarOrden;
    private JDatePickerImpl datePickerFechaDesde;
    private JDatePickerImpl datePickerFechaHasta;
    private List<OrdenPagoDTO> ordenes;

    public Ordenes(String title) throws Exception {
        super(title);

        //region Settings
        this.setResizable(false);
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(pnlMain.getPreferredSize());
        this.setBackground(Color.WHITE);
        this.ordenes = new ArrayList<>();
        //endregion

        //region Register Actions
        this.closeModule();
        this.positionScreen();
        this.actionNuevaOrden();
        this.actionEliminarOrden();
        this.actionActualizarOrden();
        this.actionFiltrar();
        this.actionLimpiarFiltros();
        this.onChangeComboBoxProveedor();
        //endregion

        //region Loaders
        this.loadTable();
        //endregion

        //region Populate
        this.populateComboBoxProveedores();
        this.populateTable();
        //endregion

        //region Default Values
        textFieldCUIT.setEditable(false);

        this.datePickerFechaDesde = Helpers.nuevoDatePicker();
        Helpers.appendDatePicker(this.pnlContainerFechaDesde, this.datePickerFechaDesde);

        this.datePickerFechaHasta = Helpers.nuevoDatePicker();
        Helpers.appendDatePicker(this.pnlContainerFechaHasta, this.datePickerFechaHasta);
        //endregion
    }

    void loadTable() throws Exception {
        this.ordenes = OrdenPagoController.getInstance().ordenesPagoEmitidas();
    }

    //region Populate
    void populateComboBoxProveedores(){
        try {
            List<ProveedorDTO> proveedores = ProveedorController.getInstance().listar();

            this.comboBoxProveedores.addItem("-- Seleccione --");
            proveedores.stream().forEach(x -> {
                this.comboBoxProveedores.addItem(x.razonSocial);
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

    void populateTable(){
        try {
            String[] columns = new String[]{
                    "NUMERO",
                    "FECHA",
                    "RAZON SOCIAL",
                    "CUIT",
                    "MONTO",
                    "ESTADO"
            };

            DefaultTableModel tblModel = new DefaultTableModel(columns, 0);

            this.ordenes.stream().forEach(x -> {
                Object[] o = {
                        x.numero,
                        x.fecha,
                        x.nombreProveedor,
                        x.cuitProveedor,
                        x.importeTotal,
                        x.estado
                };

                tblModel.addRow(o);
            });

            this.tablePagos.setModel(tblModel);

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
        Ordenes self = this;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    Main m = null;
                    m = new Main("Main");
                    m.setVisible(true);
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

    void onChangeComboBoxProveedor(){
        Ordenes self = this;
        this.comboBoxProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String razonSocial = self.comboBoxProveedores.getSelectedItem().toString();

                    ProveedorDTO proveedor = ProveedorController.getInstance().obtenerPorRazonSocial(razonSocial);

                    if(proveedor != null)
                        self.textFieldCUIT.setText(proveedor.cuit);
                    else
                        self.textFieldCUIT.setText("");

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

    void actionFiltrar(){
        Ordenes self = this;
        this.btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    String cuit = self.textFieldCUIT.getText();

                    LocalDate fechaDesde = Helpers.datePickerFormatter(self.datePickerFechaDesde);

                    LocalDate fechaHasta = Helpers.datePickerFormatter(self.datePickerFechaHasta);

                    if(self.datePickerFechaDesde.getJFormattedTextField().getValue() != null)
                        fechaDesde = Helpers.datePickerFormatter(self.datePickerFechaDesde);

                    if(self.datePickerFechaHasta.getJFormattedTextField().getValue() != null)
                        fechaHasta = Helpers.datePickerFormatter(self.datePickerFechaHasta);

                    if(fechaDesde != null
                            && fechaHasta != null
                            && !(fechaDesde.isBefore(fechaHasta) || fechaDesde.equals(fechaHasta))){

                        throw new Exception("La fecha desde no puede ser mayor que la fecha hasta.");
                    }

                    OrdenPagoController controller = OrdenPagoController.getInstance();

                    self.ordenes = null;

                    if (!cuit.equals("") && fechaDesde == null && fechaHasta == null) { // si viene cuit y no vienen fechas
                        self.ordenes = controller.ordenesPagoEmitidas(cuit);
                    } else if (cuit.equals("") && fechaDesde != null && fechaHasta == null) { // si viene solo fecha desde
                        self.ordenes = controller.ordenesPagoEmitidas(fechaDesde, LocalDate.MAX);
                    } else if (cuit.equals("") && fechaDesde == null && fechaHasta != null) { // si viene solo fecha hasta
                        self.ordenes = controller.ordenesPagoEmitidas(LocalDate.MIN, fechaHasta);
                    } else if (!cuit.equals("") && fechaDesde == null && fechaHasta != null) { // si viene cuit y fecha hasta
                        self.ordenes = controller.ordenesPagoEmitidas(LocalDate.MIN, fechaHasta);
                    } else if(!cuit.equals("") && fechaDesde != null && fechaHasta == null) { // si viene cuit y fecha desde
                        self.ordenes = controller.ordenesPagoEmitidas(fechaDesde, LocalDate.MAX, cuit);
                    } else if (cuit.equals("") && fechaDesde != null && fechaHasta != null) { // si viene fecha desde y fecha hasta
                        self.ordenes = controller.ordenesPagoEmitidas(fechaDesde, fechaHasta);
                    } else if (!cuit.equals("") && fechaDesde != null && fechaHasta != null) { // si viene el cuit y las dos fechas
                        self.ordenes = controller.ordenesPagoEmitidas(fechaDesde, fechaHasta, cuit);
                    } else if (!cuit.equals("") && fechaDesde != null && fechaHasta != null && fechaDesde.equals(fechaHasta)) { // si viene las fechas son iguales
                        self.ordenes = controller.ordenesPagoEmitidas(cuit, fechaDesde);
                    } else if (cuit.equals("") && fechaDesde != null && fechaHasta != null && fechaDesde.equals(fechaHasta)) {
                        self.ordenes = controller.ordenesPagoEmitidas(fechaDesde);
                    } else {
                        self.ordenes = controller.ordenesPagoEmitidas(); // no viene nada
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

    void actionLimpiarFiltros(){
        Ordenes self = this;

        this.btnLimpiarFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    self.datePickerFechaDesde.getJFormattedTextField().setText(null);
                    self.datePickerFechaHasta.getJFormattedTextField().setText(null);
                    self.textFieldCUIT.setText("");
                    self.comboBoxProveedores.setSelectedIndex(0);

                    self.ordenes = OrdenPagoController.getInstance().ordenesPagoEmitidas();
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

    void actionNuevaOrden(){
        Ordenes self = this;
        this.btnNuevaOrden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    NuevoPago frmNuevoPago = new NuevoPago(self);
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

    void actionActualizarOrden(){
        Ordenes self = this;
        this.btnActualizarOrden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Integer row = self.tablePagos.getSelectedRow();

                    if(row < 0)
                        throw new Exception("ATENCIÓN! Debe seleccionar la Orden que desea actualizar.");

                    Integer ordenNumero = Integer.parseInt(self.tablePagos.getValueAt(self.tablePagos.getSelectedRow(), 0).toString());

                    NuevoPago frmNuevoPago = new NuevoPago(self, ordenNumero);

                } catch (Exception ex){
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

    void actionEliminarOrden(){
        Ordenes self = this;
        this.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if(self.tablePagos.getSelectedRow() < 0)
                        throw new Exception("ATENCIÓN! Debe seleccionar la Orden que desea eliminar.");

                    String op = self.tablePagos.getValueAt(self.tablePagos.getSelectedRow(), 0).toString();

                    int confirmResult = JOptionPane.showConfirmDialog(
                            pnlMain,
                            "ATENCIÓN. Esta acción no puede deshacerse. ¿Desea continuar?",
                            "Cerrar",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (confirmResult == JOptionPane.YES_OPTION){

                        boolean result = OrdenPagoController.getInstance().eliminarOrdenPago(Integer.parseInt(op));

                        if(result){
                            JOptionPane.showMessageDialog(
                                    pnlMain,
                                    "ATENCIÓN! El registro se eliminó correctamente.",
                                    "Registro Eliminado",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        } else {
                            throw new Exception("Ocurrió un error al tratar de eliminar al registro.");
                        }

                    }

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

    void positionScreen(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width/2-this.getSize().width/2,
                dim.height/2-this.getSize().height/2
        );
    }
    //endregion
}
