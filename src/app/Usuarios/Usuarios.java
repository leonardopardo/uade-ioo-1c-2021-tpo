package app.Usuarios;

import app.Main.Main;
import com.sun.org.apache.xerces.internal.impl.xs.util.ObjectListImpl;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import controllers.UsuariosController;
import dto.UsuarioDTO;
import modelos.Usuario;
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

    public Usuarios(String title) throws SQLException {
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
        this.cambiarColor();
        //endregion
    }

    void populateInputs(String selectedRow){
        Usuario u = this.controller.obtener(selectedRow);
        this.textFieldNombre.setText(u.getNombre());
        this.textFieldApellido.setText(u.getApellido());
        this.textFieldUsername.setText(u.getUsername());
        this.textFieldUsername.setEditable(false);
        this.textFieldEdad.setText(u.getEdad().toString());
        this.comboBoxRole.setSelectedItem(u.getRole());
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

    void closeModule() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Main m = new Main("Main");
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

    void cambiarColor(){

        Usuarios self = this;

        this.checkDark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.update(self.getGraphics());
            }
        });
    }
}
