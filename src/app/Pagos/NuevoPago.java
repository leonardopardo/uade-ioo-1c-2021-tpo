package app.Pagos;

import controllers.DocumentoController;
import controllers.ProveedorController;
import dto.FacturaDTO;
import dto.ProveedorDTO;
import helpers.Helpers;
import modelos.enums.EstadoPago;
import modelos.enums.TipoPago;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NuevoPago extends JDialog {

    private JComboBox comboBoxProveedores;
    private JTextField textFieldCUIT;
    private JPanel pnlMain;
    private JPanel pnlHeader;
    private JLabel lblCuit;
    private JLabel lblProveedor;
    private JPanel pnlContainerFecha;
    private JLabel lblFechaPago;
    private JPanel pnlFormProveedor;
    private JPanel pnlFormCuit;
    private JPanel pnlFormFecha;
    private JLabel lblEstado;
    private JComboBox comboBoxEstadoPago;
    private JPanel pnlEstadoPago;
    private JButton btnGuardar;
    private JButton cancelarButton;
    private JButton btnAddAll;
    private JList listFacturasImpagas;
    private JList listFacturasAPagar;
    private JButton btnRemoveAll;
    private JButton btnAddOne;
    private JButton btnRemoveOne;
    private JComboBox comboBoxFormaPago;
    private JTextField textFieldTotal;
    private JLabel lblTotal;
    private JLabel lblFormaPago;
    private JPanel pnlActions;
    private JPanel pnlFacturas;
    private JPanel pnlFormaPago;
    private JPanel pnlCheque;
    private JTextField textFieldChequeNumero;
    private JTextField textFieldBancoCheque;
    private JTextField textFieldMontoCheque;
    private JTextField textFieldTitularCheque;
    private JLabel lblNumero;
    private JPanel pnlContainerFechaEmision;
    private JLabel lblFechaEmision;
    private JLabel lblFechaVencimiento;
    private JPanel pnlContainerFechaVencimiento;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JDatePickerImpl datePickerFecha;
    private JDatePickerImpl datePickerFechaEmisionCheque;
    private JDatePickerImpl datePickerFechaVencimientoCheque;
    private List<FacturaDTO> facturasImpagas;
    private List<FacturaDTO> facturasPagar;
    private Boolean inicial;
    private Double montoPagar;

    public NuevoPago(JFrame parent) {
        super(parent);

        this.facturasImpagas = new ArrayList<>();
        this.facturasPagar = new ArrayList<>();
        this.inicial = true;
        this.montoPagar = 0.0;


        //region Actions
        this.onChangeComboBoxProveedor();
        this.onChangeComboBoxTipoPago();
        this.actionOnClickAddOne();
        this.actionOnClickAddAll();
        this.actionOnClickRemoveOne();
        this.actionOnClickRemoveAll();
        this.actionOnClickGuardar();
        //endregion

        this.pnlCheque.setVisible(false);

        this.setDefaults();
    }

    public NuevoPago(JFrame parent, Integer orden) {
        super(parent);

        this.comboBoxProveedores.setEditable(false);

        this.setDefaults();
    }

    //region Loaders
    void loadFacturasImpagas() {
        try {

            String cuit = this.textFieldCUIT.getText();

            if (!cuit.equals("")) {
                if (this.inicial) {
                    this.facturasImpagas = DocumentoController.getInstance()
                            .listarFacturasPorEstado(cuit, EstadoPago.PENDIENTE);
                    inicial = false;
                }

                DefaultListModel model = new DefaultListModel();

                this.facturasImpagas.forEach(f -> {
                    String s = String.join(" | ", f.fecha.toString(), f.numero.toString(), f.monto.toString());
                    model.addElement(s);
                });

                this.listFacturasImpagas.setModel(model);
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

    void loadFacturasAPagar() {
        try {

            DefaultListModel model = new DefaultListModel();

            this.facturasPagar.forEach(f -> {
                String s = String.join(" | ", f.fecha.toString(), f.numero.toString(), f.monto.toString());
                model.addElement(s);
            });

            this.listFacturasAPagar.setModel(model);

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

    //region Populate
    void populateComboBoxEstadoPago() {
        for (EstadoPago estado : EstadoPago.values()) {
            this.comboBoxEstadoPago.addItem(estado);
        }
    }

    void populateComboBoxProveedores() {
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

    void populateComboBoxTipoPago() {
        this.comboBoxFormaPago.addItem("--Seleccione--");
        for (TipoPago tp : TipoPago.values()) {
            this.comboBoxFormaPago.addItem(tp);
        }
    }
    //endregion

    //region Actions
    void onChangeComboBoxProveedor() {
        NuevoPago self = this;
        this.comboBoxProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String razonSocial = self.comboBoxProveedores.getSelectedItem().toString();

                    ProveedorDTO proveedor = ProveedorController.getInstance().obtenerPorRazonSocial(razonSocial);

                    if (proveedor != null) {
                        self.textFieldCUIT.setText(proveedor.cuit);
                        self.textFieldTitularCheque.setText(proveedor.razonSocial);
                        self.loadFacturasImpagas();
                    } else {
                        self.textFieldCUIT.setText("");
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

    void onChangeComboBoxTipoPago() {
        NuevoPago self = this;
        this.comboBoxFormaPago.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if (self.comboBoxFormaPago.getSelectedIndex() > 0) {
                        TipoPago tipo = (TipoPago) self.comboBoxFormaPago.getSelectedItem();

                        if (tipo.equals(TipoPago.CHEQUE_PROPIO) || tipo.equals(TipoPago.CHEQUE_TERCERO)) {
                            self.pnlCheque.setVisible(true);

                            JTextField titular = self.textFieldTitularCheque;

                            if (tipo.equals(TipoPago.CHEQUE_PROPIO)) {
                                titular.setText(self.comboBoxProveedores.getSelectedItem().toString());
                                titular.setEditable(false);
                            } else {
                                titular.setText("");
                                titular.setEditable(true);
                            }

                        } else {
                            self.pnlCheque.setVisible(false);
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

    void actionOnClickAddOne() {
        NuevoPago self = this;
        this.btnAddOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Object element = self.listFacturasImpagas.getSelectedValue();

                    Integer index = self.listFacturasImpagas.getSelectedIndex();

                    if (element != null && element.toString().equals("") || index < 0)
                        throw new Exception("Debe seleccionar un elemento.");

                    FacturaDTO factura = self.obtenerFactura(element, false);

                    self.facturasPagar.add(factura);
                    self.loadFacturasAPagar();

                    self.facturasImpagas.remove(factura);
                    self.loadFacturasImpagas();

                    self.calcularMoto();

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

    void actionOnClickAddAll() {
        NuevoPago self = this;
        this.btnAddAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if (self.textFieldCUIT.getText().equals(""))
                        throw new Exception("Debe seleccionar un proveedor.");

                    self.facturasPagar.addAll(self.facturasImpagas);
                    self.loadFacturasAPagar();

                    self.facturasImpagas.removeAll(self.facturasImpagas);
                    self.loadFacturasImpagas();

                    self.calcularMoto();
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

    void actionOnClickRemoveOne() {
        NuevoPago self = this;
        this.btnRemoveOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Object element = self.listFacturasAPagar.getSelectedValue();

                    Integer index = self.listFacturasAPagar.getSelectedIndex();

                    if (element != null && element.toString().equals("") || index < 0)
                        throw new Exception("Debe seleccionar un elemento.");

                    FacturaDTO factura = self.obtenerFactura(element, true);

                    self.facturasImpagas.add(factura);
                    self.loadFacturasImpagas();

                    self.facturasPagar.remove(factura);
                    self.loadFacturasAPagar();

                    self.calcularMoto();
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

    void actionOnClickRemoveAll() {
        NuevoPago self = this;
        this.btnRemoveAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if (self.textFieldCUIT.getText().equals(""))
                        throw new Exception("Debe seleccionar un proveedor.");

                    self.facturasImpagas.addAll(self.facturasPagar);
                    self.loadFacturasImpagas();

                    self.facturasPagar.removeAll(self.facturasPagar);
                    self.loadFacturasAPagar();
                    self.calcularMoto();

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

    void actionOnClickGuardar() {
        NuevoPago self = this;
        this.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    // para guardar la orden de pago
                    // 1. Validar campos
                    // 2. Verificar que tiene certificados de excención.
                    // 2.1 Si tiene certificados de excención no se le retiene nada.
                    // 2.1 Si no tiene certificados de retención se le retiene iva, gancias e iibb según tabla.
                    // 3. Entonces el monto a pagar será el monto total - las retenciones.
                    // 4. Generar el pago.

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

    //region Methods
    FacturaDTO obtenerFactura(Object element, boolean pagas) {

        String[] o = element.toString().split("\\|");

        int numero = Integer.parseInt(o[1].trim());

        if (!pagas) {
            for (FacturaDTO f : this.facturasImpagas) {
                if (f.numero.equals(numero))
                    return f;
            }
        } else {
            for (FacturaDTO f : this.facturasPagar) {
                if (f.numero.equals(numero))
                    return f;
            }
        }

        return null;
    }

    void calcularMoto(){

        this.montoPagar = 0.0;

        this.facturasPagar.forEach(f -> {
            this.montoPagar += f.monto;
        });

        this.textFieldTotal.setText(this.montoPagar.toString());
    }
    //endregion

    //region Settings
    void setDefaults() {

        this.populateComboBoxProveedores();
        this.populateComboBoxEstadoPago();
        this.populateComboBoxTipoPago();
        this.textFieldCUIT.setEditable(false);

        this.datePickerFecha = Helpers.nuevoDatePicker();
        Helpers.appendDatePicker(this.pnlContainerFecha, this.datePickerFecha);

        this.datePickerFechaEmisionCheque = Helpers.nuevoDatePicker();
        Helpers.appendDatePicker(this.pnlContainerFechaEmision, this.datePickerFechaEmisionCheque);

        this.datePickerFechaVencimientoCheque = Helpers.nuevoDatePicker();
        Helpers.appendDatePicker(this.pnlContainerFechaVencimiento, this.datePickerFechaVencimientoCheque);

        this.setContentPane(this.pnlMain);
        this.setResizable(false);
        this.setModal(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(this.pnlMain.getPreferredSize());
        this.pack();
        this.positionScreen();
        this.setVisible(true);
    }

    void positionScreen() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2
        );
    }
    //endregion
}
