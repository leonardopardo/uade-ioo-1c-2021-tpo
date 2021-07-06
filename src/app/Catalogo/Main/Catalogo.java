package app.Catalogo.Main;

import app.Catalogo.Item.Items;
import app.Catalogo.Precio.Precio;
import app.Main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Catalogo extends JFrame {
    private JPanel pnlMain;
    private JPanel pnlHeader;
    private JPanel pnlBody;
    private JTabbedPane tabbedPane1;
    private JLabel lblIcon;
    private JLabel lblTitle;
    private JPanel pnlIcon;
    private JPanel pnlTitle;
    private JPanel pnlItems;
    private JPanel pnlPrecio;

    public Catalogo(String title) throws Exception {
        super(title);

        //region Settings
        this.setResizable(false);
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(pnlMain.getPreferredSize());
        this.setBackground(Color.WHITE);
        //endregion

        int w = this.pnlMain.getPreferredSize().width;
        int h = this.pnlMain.getPreferredSize().height;

        JPanel itemsPanel = new Items();
        itemsPanel.setSize(new Dimension(w, h));
        this.pnlItems.setLayout(new GridLayout());
        this.pnlItems.add(itemsPanel, BorderLayout.CENTER);

        JPanel precioPanel = new Precio();
        precioPanel.setSize(new Dimension(w, h));
        this.pnlPrecio.setLayout(new GridLayout());
        this.pnlPrecio.add(precioPanel, BorderLayout.CENTER);


        //region Register Actions
        this.closeModule();
        this.positionScreen();
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

    void positionScreen(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width/2-this.getSize().width/2,
                dim.height/2-this.getSize().height/2
        );
    }
}
