package app.Pagos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NuevoPago extends JDialog implements ActionListener {
    private JComboBox comboBox1;
    private JTextField textField1;
    private JPanel pnlMain;
    private JButton guardarButton;
    private JButton cancelarButton;

    public NuevoPago(JFrame parent){
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
