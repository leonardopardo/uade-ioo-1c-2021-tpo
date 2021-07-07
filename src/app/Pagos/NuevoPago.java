package app.Pagos;

import controllers.DocumentoController;
import controllers.OrdenPagoController;
import controllers.ProveedorController;
import dto.*;
import helpers.Helpers;
import modelos.CertificadoExcencion;
import modelos.enums.EstadoPago;
import modelos.enums.TipoPago;
import modelos.enums.TipoRetencion;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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
    private JTextField textFieldIVA;
    private JTextField textFieldIIBB;
    private JTextField textFieldGAN;
    private JTextField textFieldRetenciones;
    private JTextField textFieldSubtotal;
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

                    self.calcularRetenciones();

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

                    self.calcularRetenciones();

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

                    calcularRetenciones();

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
                    self.calcularRetenciones();


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
                    // 1. Validar campos  >>>>> OK! <<<<<

                    String cuitProveedor = self.textFieldCUIT.getText();

                    if (cuitProveedor.equals(""))
                        throw new Exception("Debe seleccionar un proveedor");

                    if (self.facturasPagar.size() == 0)
                        throw new Exception("Debe seleccionar al menos una factura a pagar.");

                    if (self.comboBoxFormaPago.getSelectedIndex() == 0)
                        throw new Exception("Debe seleccionar una forma de pago.");

                    TipoPago formaDePago = TipoPago.valueOf(self.comboBoxFormaPago.getSelectedItem().toString());

                    self.calcularRetenciones();

                    OrdenPagoDTO op = new OrdenPagoDTO();
                    op.fecha = Helpers.datePickerFormatter(self.datePickerFecha);
                    op.nombreProveedor = self.comboBoxProveedores.getSelectedItem().toString();
                    op.cuitProveedor = self.textFieldCUIT.getText();

                    if (formaDePago.equals(TipoPago.CHEQUE_TERCERO) || formaDePago.equals(TipoPago.CHEQUE_PROPIO)) {

                        String chequeTitular = self.textFieldTitularCheque.getText();
                        String chequeBanco = self.textFieldBancoCheque.getText();
                        String chequeNumero = self.textFieldChequeNumero.getText();

                        LocalDate chequeFechaEmision = null;
                        LocalDate chequeFechaVencimiento = null;

                        if (self.datePickerFechaEmisionCheque.getJFormattedTextField().getValue() != null)
                            chequeFechaEmision = Helpers.datePickerFormatter(self.datePickerFechaEmisionCheque);

                        if (self.datePickerFechaVencimientoCheque.getJFormattedTextField().getValue() != null)
                            chequeFechaVencimiento = Helpers.datePickerFormatter(self.datePickerFechaVencimientoCheque);

                        if (chequeTitular.equals(""))
                            throw new Exception("El campo titular es obligatorio");

                        if (chequeBanco.equals(""))
                            throw new Exception("El campo banco es obligatorio");

                        if (chequeNumero.equals(""))
                            throw new Exception("El campo número es obligatorio");

                        if (chequeFechaEmision == null)
                            throw new Exception("El campo fecha de emisión es obligatorio");

                        if (chequeFechaVencimiento == null)
                            throw new Exception("El campo fecha de vencimiento es obligatorio");

                        PagoDTO pDTO = new PagoDTO();
                        pDTO.tipo = formaDePago;
                        pDTO.orden = op;
                        pDTO.fecha = Helpers.datePickerFormatter(self.datePickerFecha);
                        pDTO.monto = Double.parseDouble(self.textFieldSubtotal.getText());

                        ChequeDTO cheque = new ChequeDTO();
                        cheque.banco = chequeBanco;
                        cheque.titular = chequeTitular;
                        cheque.fechaEmision = chequeFechaEmision;
                        cheque.fechaVencimiento = chequeFechaVencimiento;
                        cheque.numero = chequeNumero;

                        pDTO.cheque = cheque;
                    }

                    op.estado = EstadoPago.CANCELADO;
                    op.facturas = self.facturasPagar;
                    op.retencionesIVA = Double.parseDouble(self.textFieldIVA.getText());
                    op.retencionesIIBB = Double.parseDouble(self.textFieldIIBB.getText());
                    op.retencionesGAN = Double.parseDouble(self.textFieldGAN.getText());
                    op.importeTotal = Double.parseDouble(self.textFieldTotal.getText());
                    op.retencionesTotal = Double.parseDouble(self.textFieldRetenciones.getText());

                    OrdenPagoController.getInstance().agregar(op);

                    Ordenes parent = (Ordenes) self.getParent();
                    parent.populateTable();

                    self.dispose();

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

    void calcularMoto() {

        this.montoPagar = 0.0;

        this.facturasPagar.forEach(f -> {
            this.montoPagar += f.monto;
        });

        this.textFieldTotal.setText(this.montoPagar.toString());
    }

    void calcularRetenciones() throws Exception {

        Double iva = 0.0;
        Double iibb = 0.0;
        Double ganancias = 0.0;

        String cuit = this.textFieldCUIT.getText();

        if (!cuit.equals("")) {
            List<CertificadoDTO> certificados = ProveedorController.getInstance().obtenerCertificadosProveedor(cuit);

            for (CertificadoDTO cert : certificados) {
                TipoRetencion i = this.certificadoVigente(cert);
                if (i != null) {
                    if (i.equals(TipoRetencion.IVA)) {
                        iva = this.calcularRetencionIVA();
                    } else if (i.equals(TipoRetencion.IIBB)) {
                        iibb = this.calcularRetencionIIBB();
                    } else if (i.equals(TipoRetencion.GANANCIAS)) {
                        ganancias = this.calcularRetencionGAN();
                    }
                }
            }
        }

        Double retenciones = iva + iibb + ganancias;

        Double subtotal = this.montoPagar - retenciones;

        this.textFieldIVA.setText(Helpers.doubleTwoDecimal(iva).toString());
        this.textFieldIIBB.setText(Helpers.doubleTwoDecimal(iibb).toString());
        this.textFieldGAN.setText(Helpers.doubleTwoDecimal(ganancias).toString());
        this.textFieldSubtotal.setText(Helpers.doubleTwoDecimal(subtotal).toString());
        this.textFieldRetenciones.setText(Helpers.doubleTwoDecimal(retenciones).toString());

    }

    TipoRetencion certificadoVigente(CertificadoDTO cert) {
        if (LocalDate.now().isAfter(cert.fechaInicio) && LocalDate.now().isBefore(cert.fechaFin))
            return cert.tipo;

        return null;
    }

    Double calcularRetencionIVA() {
        return Helpers.doubleTwoDecimal(this.montoPagar - (this.montoPagar / 1.21));
    }

    Double calcularRetencionIIBB() {
        return Helpers.doubleTwoDecimal(this.montoPagar - (this.montoPagar / 1.03));
    }

    Double calcularRetencionGAN() {
        Double iva = this.calcularRetencionIVA();
        Double iibb = this.calcularRetencionIIBB();
        Double ganancias = 1.03;
        Double neto = this.montoPagar - iva - iibb;

        if (this.montoPagar >= 7870) {
            ganancias = 1.06;
        } else if (this.montoPagar >= 67170) {
            ganancias = 1.02;
        }

        return Helpers.doubleTwoDecimal(neto - (neto / ganancias));
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
