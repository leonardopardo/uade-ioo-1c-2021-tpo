package app.Catalogo.Main;

import app.Main.Main;
import app.Proveedores.Main.Proveedores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Catalogo extends JFrame{
    private JPanel pnlMain;
    private JPanel pnlHeader;
    private JPanel pnlBody;
    private JTabbedPane tabbedPane1;
    private JLabel lblIcon;
    private JLabel lblTitle;
    private JPanel pnlIcon;
    private JPanel pnlTitle;

    public Catalogo(String title){
        super(title);

        //region Settings
        this.setResizable(false);
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(pnlMain.getPreferredSize());
        this.setBackground(Color.WHITE);
        //endregion

        //region Register Actions
        this.closeModule();
        //endregion
    }

    void closeModule() {

        Catalogo self = this;

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
