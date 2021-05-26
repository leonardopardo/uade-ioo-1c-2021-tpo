package app.Usuarios;

import app.Main.Main;
import com.sun.org.apache.xerces.internal.impl.xs.util.ObjectListImpl;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
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
import java.util.stream.Collectors;

public class Usuarios extends JFrame {
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

    public Usuarios(String title) throws SQLException {
        super(title);
        this.setResizable(false);
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(pnlMain.getPreferredSize());
        this.setBackground(Color.WHITE);

        this.populateUsersTable();
        this.populateRoleComboBox();
        this.closeModule();
        this.cancelAction();
    }

    void populateUsersTable() throws SQLException {

        try {

            UsuarioService usuarioService = new UsuarioService();

            Usuario[] usuarios = usuarioService.list().toArray(new Usuario[0]);

            String[] columns = new String[] {"nombre", "apellido", "username", "edad", "role"};

            DefaultTableModel tblModel = new DefaultTableModel(columns, 0);

            usuarioService.list().stream().forEach( x -> {
                Object[] o = { x.getNombre().toUpperCase(), x.getApellido().toUpperCase(), x.getUsername(), x.getEdad(), x.getRole() };
                tblModel.addRow(o);
            });

            this.tableUsuarios.setModel(tblModel);

        } catch (SQLException e) {

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
                self.dispatchEvent(new WindowEvent(self, WindowEvent.WINDOW_CLOSING));
            }
        });
    }
}
