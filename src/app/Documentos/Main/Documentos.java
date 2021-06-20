package app.Documentos.Main;

import app.Catalogo.Item.Items;
import app.Main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Documentos extends JFrame{
    private JPanel pnlMain;
    private JPanel pnlHeader;
    private JPanel pnlBody;
    private JTabbedPane tabbedPane1;
    private JLabel lblIcono;

    public static void main(String[] args) throws Exception {
        Documentos self = new Documentos("Factura 2000");
    }

    public Documentos(String title) throws Exception{
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

    void loadModules() throws Exception{
        /*this.pnlItems.setLayout(new BorderLayout());
        this.pnlItems.add(new Items(), BorderLayout.NORTH);*/
    }

    void closeModule() {
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