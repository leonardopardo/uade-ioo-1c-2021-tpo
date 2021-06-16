package app.Usuarios;

import app.Main.Main;
import com.sun.org.apache.xerces.internal.impl.xs.util.ObjectListImpl;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import controllers.UsuariosController;
import dto.UsuarioDTO;
import modelos.enums.Role;
import servicios.UsuarioService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Array;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Usuarios extends JFrame {

    //region Properties
    private JPanel pnlMain;
    private JPanel pnlHeader;
    private JPanel pnlForm;
    private JPanel pnlTable;
    private JPanel pnlActions;
    private JPanel pnlModule;
    private JPanel pnlInputs;
    private JTextField textFieldUsername;
    private JTextField textFieldNombre;
    private JComboBox comboBoxRole;
    private JTextField textFieldPassword;
    private JTextField textFieldApellido;
    private JTextField textFieldEdad;
    private JButton buttonGuardar;
    private JButton buttonCancelar;
    private JButton buttonBuscar;
    private JLabel lblIconModule;
    private JLabel lblTitleModule;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JTable tableUsuarios;
    private JCheckBox checkDark;
    //endregion

    private UsuariosController controller;

    public Usuarios(String title) throws Exception {
        super(title);

        //region Service Controller
        this.controller = UsuariosController.getInstance();
        //endregion

        //region Form Setters
        this.setResizable(false);
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(pnlMain.getPreferredSize());
        this.setBackground(Color.WHITE);
        //endregion

        //region Populate Elements
        this.populateUsersTable();
        this.populateRoleComboBox();
        //endregion

        //region Register Actions
        this.closeModule();
        this.cancelAction();
        this.selectedRow();
        this.guardarAction();
        //endregion
    }

    void populateInputs(String selectedRow){
        UsuarioDTO u = this.controller.obtener(selectedRow);
        this.textFieldNombre.setText(u.nombre);
        this.textFieldApellido.setText(u.apellido);
        this.textFieldUsername.setText(u.username);
        this.textFieldUsername.setEditable(false);
        this.textFieldEdad.setText(u.edad.toString());
        this.comboBoxRole.setSelectedItem(u.role);
    }

    void clearInputs(){
        this.textFieldNombre.setText("");
        this.textFieldApellido.setText("");
        this.textFieldUsername.setText("");
        this.textFieldUsername.setEditable(true);
        this.textFieldEdad.setText("");
    }

    void populateUsersTable() {

        try {

            List<UsuarioDTO> usuarios = UsuariosController.getInstance().listar();

            String[] columns = new String[] {
                    "nombre".toUpperCase(),
                    "apellido".toUpperCase(),
                    "username".toUpperCase(),
                    "edad".toUpperCase(),
                    "role".toUpperCase()
            };

            DefaultTableModel tblModel = new DefaultTableModel(columns, 0);

            usuarios.stream().forEach( x -> {
                Object[] o = {
                        x.nombre.toUpperCase(),
                        x.apellido.toUpperCase(),
                        x.username,
                        x.edad,
                        x.role
                };

                tblModel.addRow(o);
            });

            this.tableUsuarios.setModel(tblModel);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    pnlMain,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    void populateRoleComboBox(){

        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<String>();
        comboBoxModel.addElement(Role.ADMINISTRADOR);
        comboBoxModel.addElement(Role.OPERADOR);

        this.comboBoxRole.setModel(comboBoxModel);
    }

    void closeModule(){
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Main m = null;
                try {
                    m = new Main("Main");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                m.setVisible(true);
            }
        });
    }

    void cancelAction() {
        Usuarios self = this;
        this.buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.clearInputs();
            }
        });
    }

    void selectedRow() {
        this.tableUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JTable target = (JTable) e.getSource();
                populateInputs(tableUsuarios.getValueAt(target.getSelectedRow(), 2).toString());
            }
        });
    }

    void guardarAction(){

        Usuarios self = this;

        this.buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = self.textFieldUsername.getText();

                if(self.controller.obtener(username) != null){

                    // Actualizar...
                    UsuarioDTO u = new UsuarioDTO();
                    u.nombre = self.textFieldNombre.getText();
                    u.apellido = self.textFieldApellido.getText();
                    u.edad = LocalDate.parse(self.textFieldEdad.getText());
                    u.role = Role.valueOf(self.comboBoxRole.getSelectedItem().toString());
                    u.password = self.textFieldPassword.getText();

                    try {
                        self.controller.actualizar(u);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                pnlMain,
                                "No se pudo actualizar el usuario. \n" + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }

                } else {

                    try {

                        // Guardar...
                        if(self.validationRules())
                            throw new Exception("Todos los campos deben estar completos.");


                        UsuarioDTO u = new UsuarioDTO();
                        u.nombre = self.textFieldNombre.getText();
                        u.apellido = self.textFieldApellido.getText();
                        u.edad = LocalDate.parse(self.textFieldEdad.getText());
                        u.role = Role.valueOf(self.comboBoxRole.getSelectedItem().toString());
                        u.password = self.textFieldPassword.getText();
                        u.username = self.textFieldUsername.getText();


                        self.controller.actualizar(u);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                pnlMain,
                                "No se pudo guardar el usuario. \n" + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });
    }

    private boolean validationRules() {

        if(this.textFieldUsername.getText().equals("")
                || this.textFieldPassword.getText().equals("")
                || this.textFieldNombre.getText().equals("")
                || this.textFieldApellido.getText().equals("")
                || this.textFieldEdad.getText().equals("")){

            return false;
        }

        return true;
    }

}
