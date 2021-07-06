package app.Documentos.OrdenCompra;


import controllers.DocumentoController;
import controllers.PrecioController;
import controllers.ProveedorController;
import dto.DetalleDTO;
import dto.ItemDTO;
import dto.OrdenCompraDTO;
import dto.ProveedorDTO;
import helpers.Helpers;
import modelos.enums.Unidad;
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
    private JButton btnEliminarDetalle;
    private JPanel pnlTable;
    private JPanel pnlFormDetalle;
    private JPanel pnlFormCabecera;
    private JPanel pnlActions;
    private JSpinner spinnerCantidad;
    private JComboBox comboBoxItemTitulo;
    private JTextField textFieldCodigoItem;
    private List<DetalleDTO> detalle;
    private JDatePickerImpl datePickerfecha;

    private Boolean guardarOrdenFlag;

    public Formulario(JFrame parent) {
        super(parent);
        this.detalle = new ArrayList<>();
        this.textFieldCuit.setEnabled(false);
        this.guardarOrdenFlag = false;
        this.spinnerCantidad.setModel(new SpinnerNumberModel(1, 1, 1000, 1));

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
        this.actionAgregarDetalle();
        this.actionEliminarDetalle();
        this.actionGuardarOrden();
        this.actionClose();
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
    JDatePickerImpl nuevoDatePicker() {
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        datePicker.setLayout(new GridLayout());
        datePicker.setPreferredSize(new Dimension(100, 30));
        datePicker.setMinimumSize(new Dimension(100, 30));

        return datePicker;
    }

    void appendDatePicker(JPanel panel, JDatePickerImpl picker) {
        panel.setLayout(new GridLayout());
        panel.add(picker);
    }
    //endregion

    //region Populate
    void populateComboBoxProveedores() {
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

    void populateComboBoxItemTitulo() {
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

    void populateTablaDetalle() {
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
    void actionClose() {
        Formulario self = this;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (self.guardarOrdenFlag) {
                    int confirmResult = JOptionPane.showConfirmDialog(
                            pnlMain,
                            "Al cerrar esta ventana sin guardar se perderan los cambios realizados. ¿Desea continuar?",
                            "Cerrar",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirmResult == JOptionPane.YES_OPTION)
                        self.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    else
                        self.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    void actionSelectedComboBoxProveedor() {
        Formulario self = this;
        this.comboBoxProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String razonSocial = self.comboBoxProveedor.getSelectedItem().toString();

                    ProveedorDTO proveedor = ProveedorController.getInstance().obtenerPorRazonSocial(razonSocial);

                    if (proveedor == null)
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

    void actionSelectedComboBoxItem() {
        Formulario self = this;
        this.comboBoxItemTitulo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String selectItemCod = self.comboBoxItemTitulo.getSelectedItem().toString();

                    ItemDTO item = PrecioController.getInstance().obtenerItemPorTitulo(selectItemCod);

                    if (item != null)
                        self.textFieldCodigoItem.setText(item.codigo);
                    else
                        self.textFieldCodigoItem.setText("");

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

    void actionAgregarDetalle() {
        Formulario self = this;
        this.btnAgregarDetalle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String itemCodigo = self.textFieldCodigoItem.getText();

                    if (itemCodigo.equals(""))
                        throw new Exception("Debe seleccionar un producto o servicio");

                    ItemDTO item = PrecioController.getInstance().obtenerItemPorCodigo(itemCodigo);

                    Double cantidad = Double.parseDouble(self.spinnerCantidad.getValue().toString());

                    Integer element = itemAgregado(itemCodigo);

                    if (element >= 0) {
                        DetalleDTO d = self.detalle.get(element);
                        d.cantItem = cantidad;
                        self.detalle.set(element, d);
                    } else {
                        DetalleDTO d = new DetalleDTO();
                        d.codItem = itemCodigo;
                        d.cantItem = cantidad;
                        d.descripcion = item.titulo;

                        self.detalle.add(d);
                    }

                    self.setTableSchemma();

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

    void actionEliminarDetalle() {
        Formulario self = this;
        this.btnEliminarDetalle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    int row = self.tblDetalle.getSelectedRow();

                    if (row < 0)
                        throw new Exception("Debe seleccionar una fila de la tabla");

                    String value = self.tblDetalle.getModel().getValueAt(row, 0).toString();

                    int itemIndex = self.itemAgregado(value);

                    if (itemIndex >= 0) {

                        int confirmResult = JOptionPane.showConfirmDialog(
                                pnlMain,
                                "¿Está seguro que desea eliminar la fila?",
                                "Eliminar Fila",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirmResult == JOptionPane.YES_OPTION) {
                            self.detalle.remove(itemIndex);
                            self.setTableSchemma();
                        }
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

    void actionGuardarOrden() {
        Formulario self = this;
        this.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String proveedorCuit = self.textFieldCuit.getText();

                    LocalDate date = Helpers.datePickerFormatter(self.datePickerfecha);

                    if (proveedorCuit.equals(""))
                        throw new Exception("Debe seleccionar un proveedor.");

                    if (date.isBefore(LocalDate.now()))
                        throw new Exception("La fecha no puede ser anterior al día de hoy.");

                    if (self.detalle.size() == 0)
                        throw new Exception("La orden de compra debe tener al menos un detalle.");

                    self.guardarOrdenFlag = true;

                    DocumentoController.getInstance().agregarOrden(self.valuesToDto());

                    self.dispose();

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
    //endregion

    //region Settings
    void setTableSchemma() {
        try {
            String[] columns = new String[]{
                    "CÓDIGO",
                    "DESCRIPCIÓN",
                    "CANTIDAD",
                    "UNIDAD"
            };

            DefaultTableModel tblModel = new DefaultTableModel(columns, 0);

            this.detalle.forEach(d -> {
                Object[] o = {
                        d.codItem,
                        d.descripcion,
                        d.cantItem,
                        Unidad.UNIDAD
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

    void positionScreen() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2
        );
    }
    //endregion

    //region Helpers
    Integer itemAgregado(String itemCodigo) {
        for (DetalleDTO d : this.detalle) {
            if (d.codItem.equals(itemCodigo))
                return this.detalle.indexOf(d);
        }
        return -1;
    }
    //endregion

    OrdenCompraDTO valuesToDto() {
        OrdenCompraDTO dto = new OrdenCompraDTO();
        dto.cuit = this.textFieldCuit.getText();
        dto.razonSocial = this.comboBoxProveedor.getSelectedItem().toString();
        dto.fecha = Helpers.datePickerFormatter(this.datePickerfecha);
        dto.detalles = this.detalle;

        return dto;
    }
}
