package app.Pagos;

import app.Documentos.OrdenCompra.Formulario;
import app.Main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton btnNuevaOrden;
    private JButton btnEliminar;
    private JButton btnFiltrar;
    private JComboBox comboBoxProveedores;
    private JTextField textField1;
    private JButton btnLimpiarFiltro;
    private JPanel pnlFrmProveedor;
    private JPanel pnlFrmCUIT;
    private JPanel pnlFRMFechaDesde;
    private JPanel pnlFRMFechaHasta;
    private JLabel lblFechaDesde;
    private JPanel pnlContainerFechaDesde;
    private JLabel lblFechaHasta;
    private JPanel pnlContainerFechaHasta;
    private JLabel lblProveedores;

    private Ordenes self;

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
        this.positionScreen();
        this.actionNuevaOrden();
        //endregion

        this.self = this;
    }

    void actionNuevaOrden(){
        Ordenes self = this;
        this.btnNuevaOrden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    NuevoPago frmNuevoPago = new NuevoPago(self);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            pnlMain,
                            ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
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

    void positionScreen(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width/2-this.getSize().width/2,
                dim.height/2-this.getSize().height/2
        );
    }
}
