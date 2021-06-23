package app.Proveedores.Main;

import app.Main.Main;
import controllers.ProveedorController;
import dto.CertificadoDTO;
import dto.ProveedorDTO;
import dto.ProveedorUIDTO;
import modelos.CertificadoExcencion;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    public Proveedores(String title) throws Exception {
        super(title);

        //region Setting Form
        this.setResizable(false);
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(pnlMain.getPreferredSize());
        this.setBackground(Color.WHITE);
        //endregion

        //region Register Actions
        this.closeModule();
        this.actionAgregarRubro();
        this.actionElimarRubro();
        this.actionEliminarProveedor();
        this.actionCancelarProveedor();
        this.actionGuardarProveedor();
        //endregion

        //region Populate Elements
        this.populateRubros();
        this.populateTipoIVA();
        this.populateTableProveedores();
        this.populateTipoRetencion();
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
        this.proveedorController = ProveedorController.getInstance();

        //endregion
    }

    //region Populate Methods
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

    void populateTableProveedores() throws Exception {

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
    }

    void populateTipoRetencion() {
        for (TipoRetencion t : TipoRetencion.values()) {
            this.comboBoxCertTipoRetencion.addItem(t);
        }
    }
    //endregion

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

                    ProveedorDTO pDto = new ProveedorDTO();
                    pDto.cuit = self.textFieldCuit.getText();
                    pDto.razonSocial = self.textFieldRazonSocial.getText();
                    pDto.nombreFantasia = self.textFieldNombreFantasia.getText();
                    pDto.email = self.textFieldEmail.getText();
                    pDto.telefono = self.textFieldTelefono.getText();
                    pDto.ingresosBrutos = self.textFieldIibb.getText();
                    pDto.tipoIVA = TipoIVA.valueOf(self.comboBoxTipoIVA.getSelectedItem().toString());
                    pDto.limiteCtaCte = Double.parseDouble(self.textFieldCtaCte.getText());
                    pDto.inicioActividad = LocalDate.of(self.inicioAct.getModel().getYear(), self.inicioAct.getModel().getMonth(), self.inicioAct.getModel().getDay());

                    for (int i = 0; i < self.listRubros.getModel().getSize(); i++) {
                        Rubro r = Rubro.valueOf(self.listRubros.getModel().getElementAt(i).toString());
                        pDto.rubros.add(r);
                    }

                    self.proveedorController.agregar(pDto);


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

    void actionCancelarProveedor() {
        this.cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        pnlMain,
                        "Click en Cancelar",
                        "",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }

    void actionEliminarProveedor() {
        this.eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        pnlMain,
                        "Click en Eliminar",
                        "",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }
    //endregion

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
    //endregion
}
