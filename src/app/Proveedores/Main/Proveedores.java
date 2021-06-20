package app.Proveedores.Main;

import app.Main.Main;
import app.Usuarios.Usuarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Proveedores extends JFrame {
    private JPanel pnlMain;
    private JPanel pnlHeader;
    private JPanel pnlBody;
    private JTabbedPane tabbedPaneMain;

    public Proveedores(String title) throws Exception{
        super(title);

        this.setResizable(false);
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(pnlMain.getPreferredSize());
        this.setBackground(Color.WHITE);

        //region Register Actions
        this.closeModule();
        //endregion
    }

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
}
