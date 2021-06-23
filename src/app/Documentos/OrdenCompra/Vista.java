package app.Documentos.OrdenCompra;

import javax.swing.*;

public class Vista extends JPanel {
    private JPanel pnlMain;
    private JTable tableOrdenesCompra;
    private JComboBox comboBoxProveedores;
    private JButton buscarButton;
    private JButton nuevaOrdenButton;
    private JButton cancelarButton;

    public Vista(){
        this.add(this.pnlMain);
    }
}
