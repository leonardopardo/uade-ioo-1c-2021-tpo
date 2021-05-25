package app.Main;

import app.Acceso.Login;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private JPanel pnlMain;
    private JButton btnProveedores;
    private JButton btnDocumentos;
    private JButton btnOrdenes;
    private JButton btnInformes;
    private JButton btnUsuarios;
    private JPanel pnlHeader;

    public Main(String title) {
        super(title);
        this.setResizable(false);
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(pnlMain.getPreferredSize());
        this.setBackground(Color.WHITE);
    }

    public static void main(String[] args) throws Exception {
        Main loginFrame = new Main("Factura 2000");
    }
}
