package app.Proveedores.Main;

import app.Main.Main;
import controllers.ProveedorController;
import dto.ProveedorDTO;
import modelos.enums.Rubro;
import modelos.enums.TipoIVA;
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
    //endregion

    private ArrayList<Rubro> rubros;

    public Proveedores(String title) throws Exception{
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
        //endregion

        //region Load Elements
        this.loadDatePicker();
        //endregion

        //region Initialize Properties
        this.rubros = new ArrayList<>();
        //endregion
    }

    //region Populate Methods
    void populateRubros(){
        for (Rubro r: Rubro.values()) {
            this.comboBoxRubros.addItem(r);
        }
    }

    void populateTipoIVA(){
        for (TipoIVA t: TipoIVA.values()) {
            this.comboBoxTipoIVA.addItem(t);
        }
    }

    void populateTableProveedores() throws Exception{

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

    void actionAgregarRubro(){
        Proveedores self = this;

        this.btnAgregarRubro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!self.rubros.contains(self.comboBoxRubros.getSelectedItem())){
                    self.rubros.add(Rubro.valueOf(self.comboBoxRubros.getSelectedItem().toString()));

                    DefaultListModel model = new DefaultListModel();
                    model.addAll(self.rubros);

                    self.listRubros.setModel(model);
                }
            }
        });
    }

    void actionElimarRubro(){
        Proveedores self = this;

        this.btnElimnarRubro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                var value = self.listRubros.getSelectedValue();

                try{
                    if(value == null)
                        throw new Exception("Debe seleccionar un rubro de la lista.");

                    self.rubros.remove(Rubro.valueOf(value.toString()));

                    DefaultListModel model = new DefaultListModel();
                    model.addAll(self.rubros);

                    self.listRubros.setModel(model);


                } catch(Exception ex) {
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

    void actionGuardarProveedor(){
        this.guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        pnlMain,
                        "Click en Guardar",
                        "",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }

    void actionCancelarProveedor(){
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

    void actionEliminarProveedor(){
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
    void loadDatePicker(){
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        this.pnlDatePicker.setLayout(new GridLayout());
        this.pnlDatePicker.add(datePicker);
    }
    //endregion
}
