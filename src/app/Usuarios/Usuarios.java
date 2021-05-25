package app.Usuarios;

import app.Main.Main;
import modelos.Usuario;

import javax.swing.*;
import java.awt.*;

public class Usuarios extends JFrame {
    private JPanel pnlMain;
    private JTable tblUsuarios;
    private JPanel pnlHeader;
    private JPanel pnlTable;
    private JPanel pnlActions;
    private JPanel pnlTitle;

    public Usuarios(String title) {
        super(title);
        this.setResizable(false);
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(pnlMain.getPreferredSize());
        this.setBackground(Color.WHITE);
    }

    public static void main(String[] args) throws Exception {
        Usuarios loginFrame = new Usuarios("Factura 2000");
    }
}
