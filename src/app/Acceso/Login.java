package app.Acceso;

import modelos.Usuario;
import seeders.UsuarioSeeder;
import servicios.UsuariosService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.util.List;

public class Login extends JFrame {
    private JPanel pnlMain;
    private JPanel pnlHeader;
    private JPanel pnlInputs;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JLabel lblPassword;
    private JPasswordField txtPassword;
    private JPanel pnlButton;
    private JButton ingresarButton;
    private JPanel pnlSeparate;
    private JLabel lblBrand;
    private JLabel lblAccess;

    public Login(String titulo) throws Exception {
        super(titulo);
        this.setResizable(false);
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setSize(pnlMain.getPreferredSize());
        this.setBackground(Color.WHITE);

        UsuarioSeeder.run();
        this.logOn();
    }

    public void logOn() {
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean usrIsValid = UsuariosService.getInstance()
                        .validarCredenciales(txtUsername.getText(), txtPassword.getPassword());

                if(usrIsValid){
                    JOptionPane.showMessageDialog(
                            pnlMain,
                            "Credenciales Válidas",
                            "Excelente",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    String message = "Credenciales Inválidas";

                    if(txtPassword.getPassword().length == 0)
                        message = "El campo Contraseña no puede estar vacío.";

                    if(txtUsername.getText().isEmpty())
                        message = "El campo Username no puede estar vacío.";

                    JOptionPane.showMessageDialog(
                            pnlMain,
                            message,
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    public static void main(String[] args) throws Exception {
        Login loginFrame = new Login("Factura 2000");
    }
}
