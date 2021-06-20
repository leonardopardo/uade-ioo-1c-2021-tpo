package app.Main;

import app.Catalogo.Main.Catalogo;
import app.Documentos.Main.Documentos;
import app.Pagos.Ordenes;
import app.Proveedores.Main.Proveedores;
import app.Usuarios.Usuarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame {
    private JPanel pnlMain;
    private JButton btnProveedores;
    private JButton btnDocumentos;
    private JButton btnOrdenes;
    private JButton btnInformes;
    private JButton btnUsuarios;
    private JPanel pnlHeader;
    private JButton btnCatalogo;

    public Main(String title) throws Exception {
        super(title);

        this.setResizable(false);
        this.setContentPane(this.pnlMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(pnlMain.getPreferredSize());
        this.setBackground(Color.WHITE);

        //region Register Modules
        this.showUsuariosModule();
        this.showProveedoresModule();
        this.showDocumentosModule();
        this.showCatalogoModule();
        this.showOrdenPagoModule();
        //endregion

        this.closeModule();
    }

    public static void main(String[] args) throws Exception {
        Main self = new Main("Factura 2000");
    }

    //region Display Modules
    void showUsuariosModule() {

        Main self = this;

        btnUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.setVisible(false);
                try {
                    Usuarios u = new Usuarios("Usuarios");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    void showProveedoresModule() {

        Main self = this;

        btnProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.setVisible(false);
                try {
                    Proveedores u = new Proveedores("Proveedores");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    void showDocumentosModule() {
        Main self = this;

        this.btnDocumentos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.setVisible(false);
                try {
                    Documentos d = new Documentos("Documentos");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    void showCatalogoModule(){
        Main self = this;

        this.btnCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.setVisible(false);
                try {
                    Catalogo d = new Catalogo("Documentos");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    void showOrdenPagoModule(){
        Main self = this;

        this.btnOrdenes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.setVisible(false);
                try {
                    Ordenes d = new Ordenes("Ordenes");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    //endregion

    void closeModule() {

        Main self = this;

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                self.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
    }
}
