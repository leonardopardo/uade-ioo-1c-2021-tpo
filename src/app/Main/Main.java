package app.Main;

import app.Usuarios.Usuarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class Main extends JFrame {
    private JPanel pnlMain;
    private JButton btnProveedores;
    private JButton btnDocumentos;
    private JButton btnOrdenes;
    private JButton btnInformes;
    private JButton btnUsuarios;
    private JPanel pnlHeader;

    public Main(String title) throws Exception {
        super(title);

        this.setResizable(false);
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(pnlMain.getPreferredSize());
        this.setBackground(Color.WHITE);

        this.showUsuariosModule();
        this.closeModule();
    }

    public static void main(String[] args) throws Exception {
        Main self = new Main("Factura 2000");
    }

    void showUsuariosModule() {

        Main self = this;

        btnUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.setVisible(false);
                try {
                    Usuarios u = new Usuarios("Usuarios");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

    }

    void closeModule() {

        Main self = this;

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                self.dispose();
            }
        });
    }
}
