package app.Documentos.OrdenCompra;

import javax.swing.*;
import java.awt.*;

public class Formulario extends JFrame {
    private JPanel pnlMain;

    public Formulario(){
        this.setResizable(false);
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(pnlMain.getPreferredSize());
        this.setBackground(Color.WHITE);
    }
}
