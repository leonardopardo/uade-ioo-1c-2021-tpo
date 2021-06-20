package app.Pagos;

import app.Main.Main;
import app.Proveedores.Main.Proveedores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Ordenes extends JFrame{
    private JPanel pnlMain;
    private JPanel pnlHeader;
    private JPanel pnlBody;
    private JPanel pnlIcon;
    private JLabel lblIcon;
    private JPanel pnlTitle;
    private JLabel lblTitle;
    private JPanel pnlForm;
    private JPanel pnlTable;
    private JTable tablePagos;
    private JPanel pnlActions;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    public Ordenes(String title){
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
        Ordenes self = this;
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
