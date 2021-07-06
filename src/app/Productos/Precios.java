package app.Productos;

import javax.swing.*;
import java.awt.*;

public class Precios extends JDialog{
    private JPanel pnlMain;
    private JButton GUARDARButton;
    private JButton CANCELARButton;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JTextField textField1;
    private JComboBox comboBox4;
    private JTextField textField2;
    private JTextField textField3;

    public Precios(JFrame parent){
        super(parent);
        this.setContentPane(this.pnlMain);
        this.setResizable(false);
        this.setModal(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(this.pnlMain.getPreferredSize());
        this.pack();
        this.positionScreen();
        this.setVisible(true);
    }

    void positionScreen() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2
        );
    }
}
