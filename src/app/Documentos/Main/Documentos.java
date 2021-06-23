package app.Documentos.Main;

import app.Documentos.OrdenCompra.Vista;
import app.Main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Documentos extends JFrame{
    private JPanel pnlMain;
    private JPanel pnlHeader;
    private JPanel pnlBody;
    private JTabbedPane tabbedPaneMain;
    private JLabel lblIcono;
    private JPanel pnlOrdenCompra;

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

        this.loadOrdenCompraPanel();
    }

    //region Loaders
    void loadOrdenCompraPanel() {
        try {
            this.pnlOrdenCompra.setLayout(new GridLayout());
            this.pnlOrdenCompra.add(new Vista(), BorderLayout.CENTER);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    //endregion

    //region Actions
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
    //endregion
}
