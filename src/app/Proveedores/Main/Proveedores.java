package app.Proveedores.Main;

import app.Main.Main;
import controllers.ProveedorController;
import dto.CertificadoDTO;
import dto.ProveedorDTO;
import dto.ProveedorUIDTO;
import modelos.enums.Rubro;
import modelos.enums.TipoIVA;
import modelos.enums.TipoRetencion;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
    private ProveedorController proveedorController;
    //endregion
    private JDatePickerImpl inicioAct;
    private ArrayList<Rubro> rubros;
    protected static final String PROVEEDOR_EXISTENTE_EXCEPTION = "El proveedor que intenta agregar ya existe.";

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
        this.actionSelectedProveedor();
        this.selectedRow();
        //endregion

        //region Populate Elements
        this.populateRubros();
        this.populateTipoIVA();
        this.populateTableProveedores();
        this.populateTipoRetencion();
        //this.populateComboBoxProveedoresCert();
        //endregion

        //region Load Elements
        this.inicioAct = this.nuevoDatePicker();
        this.pnlDatePicker.setLayout(new GridLayout());
        this.pnlDatePicker.add(this.inicioAct);

        this.loadTableCert();
        // this.loadDatePicker(this.pnlDatePicker);
        // this.loadDatePicker(this.pnlCertFechaInicio);
        // this.loadDatePicker(this.pnlCertFechaFin);
        //endregion

        //region Initialize Properties
        this.rubros = new ArrayList<>();
        this.textFieldCertCuit.setEnabled(false);
        this.proveedorController = ProveedorController.getInstance();
        //endregion
    }


    //region Populate Methods
    void populateInputs(String selectedRow) {
        ProveedorDTO p = this.proveedorController.obtener(selectedRow);
        this.textFieldRazonSocial.setText(p.razonSocial);
        this.textFieldNombreFantasia.setText(p.nombreFantasia);
        this.textFieldCuit.setText(p.cuit);
        this.textFieldIibb.setText(p.ingresosBrutos);
        this.textFieldEmail.setText(p.email);
        this.textFieldTelefono.setText(p.telefono);
        this.textFieldCtaCte.setText(Double.toString(p.limiteCtaCte));
        this.comboBoxTipoIVA.setSelectedItem(p.tipoIVA);
        this.inicioAct.getJFormattedTextField().setText(p.inicioActividad.toString());
        this.populateListRubros((ArrayList) p.rubros);
    }

    void selectedRow() {
        this.tableProveedores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable target = (JTable) e.getSource();
                populateInputs(tableProveedores.getValueAt(target.getSelectedRow(), 1).toString());
            }
        });
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
            List<ProveedorUIDTO> proveedores = ProveedorController.getInstance().listar();

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


    void populateTableProveedoresCert() {
        try {
            List<ProveedorUIDTO> proveedores = ProveedorController.getInstance().listar();

            proveedores.stream().forEach(x -> {
                this.comboBoxCertProveedor.addItem(x.razonSocial);
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


    void populateTipoRetencion() {
        for (TipoRetencion t : TipoRetencion.values()) {
            this.comboBoxCertTipoRetencion.addItem(t);
        }
    }


    void populateListRubros(ArrayList r) {
        DefaultListModel model = new DefaultListModel();
        model.addAll(r);
        this.listRubros.setModel(model);
    }
    //end populate region


    //region Action Methods
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
                    ProveedorDTO check = self.proveedorController.obtener(self.textFieldCuit.getText());

                    if (check != null) {
                        String message = "Usted está a punto de editar el proveedor " + check.razonSocial + " ¿está seguro?";

                        int confirmResult = JOptionPane.showConfirmDialog(pnlMain, message,
                                "Actualizar proveedor", JOptionPane.YES_NO_OPTION
                        );
                        if (confirmResult == JOptionPane.YES_OPTION) {
                            self.proveedorController.actualizar(self.crearActualizarProveedor());
                        }
                    } else {
                        ProveedorDTO pDto = self.crearActualizarProveedor();
                        self.proveedorController.agregar(pDto);
                    }
                    self.populateTableProveedores();
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
        pDto.inicioActividad = LocalDate.of(this.inicioAct.getModel().getYear(), this.inicioAct.getModel().getMonth(), this.inicioAct.getModel().getDay());

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
                    String razonSocial = self.textFieldRazonSocial.getText();
                    String cuit = self.textFieldCuit.getText();

                    int confirmResult = JOptionPane.showConfirmDialog(pnlMain, "¿Está seguro que desea eliminar el proveedor " + razonSocial + "?",
                            "Eliminar proveedor", JOptionPane.YES_NO_OPTION
                    );
                    if (confirmResult == JOptionPane.YES_OPTION) {
                        self.proveedorController.eliminar(cuit);
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


    void actionGuardarCertificado() {
        this.guardarCertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(
                            pnlMain,
                            "Click en Guardar Certificado",
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


    void actionSelectedProveedor() {
        Proveedores self = this;
        this.comboBoxCertProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String razonSocial = self.comboBoxCertProveedor.getSelectedItem().toString();
                    ProveedorDTO dto = ProveedorController.getInstance().obtenerPorRazonSocial(razonSocial);
                    self.textFieldCertCuit.setText(dto.cuit);
                } catch (Exception ex) {

                }
            }
        });
    }
    //end action region

    //region Load Methods
    JDatePickerImpl nuevoDatePicker() {
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

        return datePicker;
    }


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

    //endregion
}