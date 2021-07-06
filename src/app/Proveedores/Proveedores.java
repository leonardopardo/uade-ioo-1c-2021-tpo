package app.Proveedores;

import app.Main.Main;
import controllers.ProveedorController;
import dto.CertificadoDTO;
import dto.ProveedorDTO;
import helpers.Helpers;
import modelos.enums.Rubro;
import modelos.enums.TipoIVA;
import modelos.enums.TipoRetencion;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Proveedores extends JFrame {

    //region UI Elements
    private JPanel pnlMain;
    private JPanel pnlHeader;
    private JPanel pnlBody;
    private JTabbedPane tabbedPaneMain;
    private JPanel tabProveedores;
    private JPanel tabCertificados;
    private JPanel pnlForm;
    private JPanel pnlTable;
    private JPanel pnlActions;
    private JTable tableProveedores;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JButton eliminarButton;
    private JPanel pnlInputs;
    private JTextField textFieldRazonSocial;
    private JPanel pnlList;
    private JList listRubros;
    private JTextField textFieldNombreFantasia;
    private JPanel pnlRazonSocial;
    private JPanel pnlNombreFantasia;
    private JPanel pnlFiscal;
    private JTextField textFieldCuit;
    private JTextField textFieldIibb;
    private JComboBox comboBoxTipoIVA;
    private JLabel lblCuit;
    private JLabel lblIibb;
    private JLabel lblInicioAct;
    private JLabel lblTipoIVA;
    private JPanel pnlContacto;
    private JTextField textFieldEmail;
    private JTextField textFieldTelefono;
    private JLabel lblEmail;
    private JLabel lblTelefono;
    private JPanel pnlMontos;
    private JTextField textFieldCtaCte;
    private JComboBox comboBoxRubros;
    private JButton btnAgregarRubro;
    private JLabel lblCtaCte;
    private JLabel lblRubros;
    private JLabel lblRazonSocial;
    private JLabel lblBalance;
    private JLabel lblBalanceMonto;
    private JLabel lblNombreFantasia;
    private JButton btnElimnarRubro;
    private JPanel pnlDatePicker;
    private JPanel pnlCertTable;
    private JPanel pnlCertForm;
    private JPanel pnlCertActions;
    private JComboBox comboBoxCertProveedor;
    private JTextField textFieldCertCuit;
    private JComboBox comboBoxCertTipoRetencion;
    private JTable tableCert;
    private JButton guardarCertButton;
    private JButton cancelarCertButton;
    private JButton eliminarCertButton;
    private JPanel pnlCertCuit;
    private JPanel pnlCertProveedor;
    private JLabel lblCertProveedor;
    private JLabel lblCertCuit;
    private JLabel lblCertTipoRetencion;
    private JPanel pnlCertFechaInicio;
    private JLabel lblCertFechaInicio;
    private JLabel lblCertFechaFin;
    private JPanel pnlCertFechaFin;
    private JDatePickerImpl inicioAct;
    private JDatePickerImpl certifInicio;
    private JDatePickerImpl certifFin;
    private List<Rubro> rubros;
    protected static final String PROVEEDOR_EXISTENTE_EXCEPTION = "El proveedor que intenta agregar ya existe.";
    protected static final String FECHA_VENCIMIENTO_POSTERIOR_INICIO = "La fecha de vencimiento del certificado debe ser posterior a la fecha de emisión.";
    protected static final String CERTIFICAD_EXISTENTE = "El certificado que está intentando agregar ya existe.";
    //endregion

    public Proveedores(String title) throws Exception {
        super(title);

        //region Setting
        this.setResizable(false);
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(pnlMain.getPreferredSize());
        this.setBackground(Color.WHITE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        //endregion

        //region Register Actions
        this.closeModule();
        this.actionAgregarRubro();
        this.actionElimarRubro();
        this.actionEliminarProveedor();
        this.actionCancelarProveedor();
        this.actionGuardarProveedor();
        this.actionGuardarCertificado();
        this.actionCancelarCertificado();
        this.actionEliminarCertificado();
        this.actionSelectedRowProveedores();
        this.actionShowCertificados();
        //endregion

        //region Populate Elements
        this.populateRubros();
        this.populateTipoIVA();
        this.populateTableProveedores();
        this.populateTipoRetencion();
        this.populateComboBoxProveedoresCert();
        //endregion

        //region Load Elements
        this.inicioAct = Helpers.nuevoDatePicker();
        this.certifInicio = Helpers.nuevoDatePicker();
        this.certifFin = Helpers.nuevoDatePicker();
        this.pnlDatePicker.setLayout(new GridLayout());
        this.pnlCertFechaInicio.setLayout(new GridLayout());
        this.pnlCertFechaFin.setLayout(new GridLayout());
        this.pnlCertFechaInicio.add(this.certifInicio);
        this.pnlCertFechaFin.add(this.certifFin);
        this.pnlDatePicker.add(this.inicioAct);
        this.loadTableCert();
        //endregion

        //region Initialize Properties
        this.rubros = new ArrayList<>();
        this.textFieldCertCuit.setEnabled(false);
        //endregion
    }

    //region Populate Methods
    void populateInputs(String selectedRow) throws Exception {
        ProveedorDTO proveedor = ProveedorController.getInstance().obtener(selectedRow);
        this.textFieldRazonSocial.setText(proveedor.razonSocial);
        this.textFieldNombreFantasia.setText(proveedor.nombreFantasia);
        this.textFieldCuit.setText(proveedor.cuit);
        this.textFieldIibb.setText(proveedor.ingresosBrutos);
        this.textFieldEmail.setText(proveedor.email);
        this.textFieldTelefono.setText(proveedor.telefono);
        this.textFieldCtaCte.setText(Double.toString(proveedor.limiteCtaCte));
        this.comboBoxTipoIVA.setSelectedItem(proveedor.tipoIVA);
        this.inicioAct.getJFormattedTextField().setText(proveedor.inicioActividad.toString());
        this.rubros = proveedor.rubros;
        this.populateListRubros((ArrayList) rubros);
    }

    void populateRubros() {
        for (Rubro r : Rubro.values()) {
            this.comboBoxRubros.addItem(r);
        }
    }

    void populateTipoIVA() {
        for (TipoIVA t : TipoIVA.values()) {
            this.comboBoxTipoIVA.addItem(t);
        }
    }

    void populateTableProveedores() {
        try {
            List<ProveedorDTO> proveedores = ProveedorController.getInstance().listar();

            String[] columns = new String[]{
                    "Razon Social".toUpperCase(),
                    "CUIT".toUpperCase(),
            };

            DefaultTableModel tblModel = new DefaultTableModel(columns, 0);

            proveedores.stream().forEach(x -> {
                Object[] o = {
                        x.razonSocial,
                        x.cuit,
                };

                tblModel.addRow(o);
            });

            this.tableProveedores.setModel(tblModel);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    pnlMain,
                    ex.getMessage(),
                    "",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    void populateTipoRetencion() {
        for (TipoRetencion t : TipoRetencion.values()) {
            this.comboBoxCertTipoRetencion.addItem(t);
        }
    }

    void populateListRubros(ArrayList r) {
        DefaultListModel model = new DefaultListModel();
        //model.addAll(r);
        this.listRubros.setModel(model);
    }
    //endregion

    //region Action
    void closeModule() {
        Proveedores self = this;
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

    void actionSelectedRowProveedores() {
        this.tableProveedores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable target = (JTable) e.getSource();
                try {
                    populateInputs(tableProveedores.getValueAt(target.getSelectedRow(), 1).toString());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    void actionAgregarRubro() {
        Proveedores self = this;
        this.btnAgregarRubro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!self.rubros.contains(self.comboBoxRubros.getSelectedItem())) {
                    self.rubros.add(Rubro.valueOf(self.comboBoxRubros.getSelectedItem().toString()));

                    DefaultListModel model = new DefaultListModel();
                    model.addAll(self.rubros);
                    self.listRubros.setModel(model);
                }
            }
        });
    }

    void actionElimarRubro() {
        Proveedores self = this;
        this.btnElimnarRubro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var value = self.listRubros.getSelectedValue();

                try {
                    if (value == null)
                        throw new Exception("Debe seleccionar un rubro de la lista.");

                    self.rubros.remove(Rubro.valueOf(value.toString()));
                    DefaultListModel model = new DefaultListModel();
                    model.addAll(self.rubros);
                    self.listRubros.setModel(model);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            pnlMain,
                            ex.getMessage(),
                            "",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    void actionGuardarProveedor() {
        Proveedores self = this;
        this.guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ProveedorController pController = ProveedorController.getInstance();
                    ProveedorDTO check = pController.obtener(self.textFieldCuit.getText());

                    if (check != null) {
                        String message = "Usted está a punto de editar el proveedor " + check.razonSocial + " ¿está seguro?";

                        int confirmResult = JOptionPane.showConfirmDialog(pnlMain, message,
                                "Actualizar proveedor", JOptionPane.YES_NO_OPTION
                        );
                        if (confirmResult == JOptionPane.YES_OPTION) {
                            pController.actualizar(self.crearActualizarProveedor());
                        }
                    } else {
                        ProveedorDTO pDto = self.crearActualizarProveedor();
                        pController.agregar(pDto);
                    }
                    self.populateTableProveedores();
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

    ProveedorDTO crearActualizarProveedor() {
        ProveedorDTO pDto = new ProveedorDTO();
        pDto.cuit = this.textFieldCuit.getText();
        pDto.razonSocial = this.textFieldRazonSocial.getText();
        pDto.nombreFantasia = this.textFieldNombreFantasia.getText();
        pDto.email = this.textFieldEmail.getText();
        pDto.telefono = this.textFieldTelefono.getText();
        pDto.ingresosBrutos = this.textFieldIibb.getText();
        pDto.tipoIVA = TipoIVA.valueOf(this.comboBoxTipoIVA.getSelectedItem().toString());
        pDto.limiteCtaCte = Double.parseDouble(this.textFieldCtaCte.getText());
        pDto.inicioActividad = Helpers.datePickerFormatter(this.inicioAct);

        for (int i = 0; i < this.listRubros.getModel().getSize(); i++) {
            Rubro r = Rubro.valueOf(this.listRubros.getModel().getElementAt(i).toString());
            pDto.rubros.add(r);
        }
        return pDto;
    }

    void actionCancelarProveedor() {
        Proveedores self = this;
        this.cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    self.clearInputs();
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

    void actionEliminarProveedor() {
        Proveedores self = this;
        this.eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ProveedorController pController = ProveedorController.getInstance();
                    String razonSocial = self.textFieldRazonSocial.getText();
                    String cuit = self.textFieldCuit.getText();

                    int confirmResult = JOptionPane.showConfirmDialog(pnlMain, "¿Está seguro que desea eliminar el proveedor " + razonSocial + "?",
                            "Eliminar proveedor", JOptionPane.YES_NO_OPTION
                    );
                    if (confirmResult == JOptionPane.YES_OPTION) {
                        pController.eliminar(cuit);
                        self.populateTableProveedores();
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

    //region Load Methods
    void loadTableCert() throws Exception {
        String cuit = this.textFieldCertCuit.getText();

        String[] columns = new String[]{
                "Tipo",
                "Fecha Inicio",
                "Fecha Fin"
        };

        DefaultTableModel tblModel = new DefaultTableModel(columns, 0);

        if (cuit != null) {
            List<CertificadoDTO> certificados = ProveedorController.getInstance().listarCertificadosPorProveedor(cuit);

            certificados.stream().forEach(x -> {
                Object[] o = {
                        x.tipo,
                        x.fechaInicio,
                        x.fechaFin
                };

                tblModel.addRow(o);
            });
        }
        this.tableCert.setModel(tblModel);
    }

    // clear inputs
    void clearInputs() {
        this.textFieldRazonSocial.setText("");
        this.textFieldCuit.setText("");
        this.textFieldCtaCte.setText("");
        this.textFieldTelefono.setText("");
        this.textFieldIibb.setText("");
        this.textFieldEmail.setText("");
        this.textFieldNombreFantasia.setText("");
        this.inicioAct.getJFormattedTextField().setText("");
        DefaultListModel listRubrosModel = (DefaultListModel) this.listRubros.getModel();
        listRubrosModel.removeAllElements();
    }
    //endregion proveedores

    //region Certificados
    //region Certificados Populate
    void populateComboBoxProveedoresCert() throws Exception {
        this.comboBoxCertProveedor.addItem("-- Seleccione un proveedor --");
        for (ProveedorDTO prov : ProveedorController.getInstance().listar()
        ) {
            this.comboBoxCertProveedor.addItem(prov.razonSocial);
        }
    }

    void populateTableProveedoresCert() {
        try {
            List<CertificadoDTO> certificados = ProveedorController.getInstance().obtenerCertificadosProveedor(this.textFieldCertCuit.getText());
            String[] columns = new String[]{
                    "Tipo",
                    "Fecha Inicio",
                    "Fecha Fin",
            };

            DefaultTableModel tblModel = new DefaultTableModel(columns, 0);
            if (certificados != null) {
                certificados.stream().forEach(x -> {
                    Object[] o = {
                            x.tipo,
                            x.fechaInicio,
                            x.fechaFin,
                    };

                    tblModel.addRow(o);
                });
                this.tableCert.setModel(tblModel);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    pnlMain,
                    ex.getStackTrace(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    //endregion


    //region Certificados Actions
    void actionShowCertificados() {
        Proveedores self = this;
        this.comboBoxCertProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String razonSocial = self.comboBoxCertProveedor.getSelectedItem().toString();
                    ProveedorDTO dto = ProveedorController.getInstance().obtenerPorRazonSocial(razonSocial);
                    if (dto != null) {
                        self.textFieldCertCuit.setText(dto.cuit);
                    }
                    populateTableProveedoresCert();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            pnlMain,
                            ex.getMessage(),
                            "",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    void actionGuardarCertificado() {
        Proveedores self = this;
        this.guardarCertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CertificadoDTO nuevoCertif = new CertificadoDTO();
                    LocalDate startDate = Helpers.datePickerFormatter(self.certifInicio);
                    nuevoCertif.fechaInicio = startDate;
                    LocalDate endDate = Helpers.datePickerFormatter(self.certifFin);

                    if (endDate.isBefore(startDate)) {
                        throw new Exception(FECHA_VENCIMIENTO_POSTERIOR_INICIO);
                    }

                    String provCuit = self.textFieldCertCuit.getText();
                    nuevoCertif.tipo = TipoRetencion.valueOf(self.comboBoxCertTipoRetencion.getSelectedItem().toString());

                    if (ProveedorController.getInstance().existeCertificado(provCuit, nuevoCertif)) {
                        throw new Exception(CERTIFICAD_EXISTENTE);
                    }

                    nuevoCertif.fechaFin = endDate;
                    ProveedorController.getInstance().agregarCertificado(nuevoCertif, provCuit);
                    populateTableProveedoresCert();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            pnlMain,
                            ex.getMessage(),
                            "",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    void actionCancelarCertificado() {
        this.cancelarCertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(
                            pnlMain,
                            "Click en Cancelar Certificado",
                            "",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            pnlMain,
                            ex.getMessage(),
                            "",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    void actionEliminarCertificado() {
        this.eliminarCertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(
                            pnlMain,
                            "Click en Eliminar Certificado",
                            "",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            pnlMain,
                            ex.getMessage(),
                            "",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }
    //endregion
    //endregion
}
