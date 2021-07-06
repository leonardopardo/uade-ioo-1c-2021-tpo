package app.Productos;

import controllers.PrecioController;
import controllers.ProveedorController;
import dto.ItemDTO;
import dto.PrecioDTO;
import dto.ProveedorDTO;
import modelos.enums.Rubro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class Precios extends JDialog {
    private JPanel pnlMain;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JComboBox comboBoxRubro;
    private JComboBox comboBoxItemTitle;
    private JTextField textFieldItemCodigo;
    private JComboBox comboBoxProveedor;
    private JTextField textFieldProveedorCuit;
    private JTextField textFieldPrecio;
    private JLabel lblRubro;
    private JLabel lblPrecio;
    private JLabel lblItem;
    private JLabel lblItemCodigo;
    private JLabel lblProveedor;
    private JLabel lblProveedorCuit;

    public Precios(JFrame parent) {
        super(parent);

        //region Loaders
        this.loadComboBoxProveedores();
        this.loadComboBoxItems();
        this.loadComboBoxRubros();
        //endregion

        //region Actions
        this.actionOnChangeComboBoxProveedor();
        this.actionOnChangeComboBoxItems();
        this.actionOnClickGuardar();
        this.actionOnClickCancelar();
        //endregion

        //region Settings
        this.setContentPane(this.pnlMain);
        this.setResizable(false);
        this.setModal(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(this.pnlMain.getPreferredSize());
        this.pack();
        this.positionScreen();
        this.setVisible(true);
        //endregion
    }

    //region Loaders
    void loadComboBoxRubros() {
        this.comboBoxRubro.addItem("-- Seleccione --");
        for (Rubro r : Rubro.values()) {
            this.comboBoxRubro.addItem(r);
        }
    }

    void loadComboBoxProveedores() {
        try {
            List<ProveedorDTO> proveedores = ProveedorController.getInstance().listar();

            this.comboBoxProveedor.addItem("-- Seleccione --");

            proveedores.stream().forEach(x -> {
                this.comboBoxProveedor.addItem(x.razonSocial);
            });

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    pnlMain,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    void loadComboBoxItems() {
        try {
            List<ItemDTO> items = PrecioController.getInstance().listarItems();
            this.comboBoxItemTitle.addItem("--Seleccione--");
            for (ItemDTO item : items) {
                this.comboBoxItemTitle.addItem(item.titulo);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    pnlMain,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    //endregion

    //region Actions
    void actionOnClickGuardar() {
        Precios self = this;
        this.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String txtPrecio = self.textFieldPrecio.getText();
                    String txtCuit = self.textFieldProveedorCuit.getText();
                    String txtItemCodigo = self.textFieldItemCodigo.getText();

                    if(txtPrecio.equals(""))
                        throw new Exception("El campo precio no puede estar vacío.");

                    if(txtCuit.equals(""))
                        throw new Exception("Debe seleccionar un proveedor.");

                    if(txtItemCodigo.equals(""))
                        throw new Exception("Debe seleccionar un item.");

                    PrecioDTO precio = new PrecioDTO();
                    precio.itemCodigo = txtItemCodigo;
                    precio.precio = Double.parseDouble(txtPrecio);
                    precio.razonSocial = self.comboBoxProveedor.getSelectedItem().toString();
                    precio.cuit = txtCuit;
                    precio.rubro = Rubro.valueOf(self.comboBoxRubro.getSelectedItem().toString());

                    PrecioController.getInstance().agregar(precio);

                    Productos parent = (Productos) self.getParent();
                    parent.loadPrecios();
                    parent.loadTablePrecios();

                    self.dispose();

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

    void actionOnClickCancelar() {
        /*Precios self = this;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                int confirmResult = JOptionPane.showConfirmDialog(
                        pnlMain,
                        "Al cerrar esta ventana sin guardar se perderan los cambios realizados. ¿Desea continuar?",
                        "Cerrar",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmResult == JOptionPane.YES_OPTION)
                    self.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                else
                    self.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            }
        });*/
    }

    void actionOnChangeComboBoxProveedor() {
        Precios self = this;
        this.comboBoxProveedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String razonSocial = self.comboBoxProveedor
                            .getSelectedItem()
                            .toString();

                    ProveedorDTO dto = ProveedorController
                            .getInstance()
                            .obtenerPorRazonSocial(razonSocial);

                    if (dto != null) {
                        self.textFieldProveedorCuit.setText(dto.cuit);
                    } else {
                        self.textFieldProveedorCuit.setText("");
                    }

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

    void actionOnChangeComboBoxItems() {
        Precios self = this;
        this.comboBoxItemTitle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String titulo = self.comboBoxItemTitle
                            .getSelectedItem()
                            .toString();

                    ItemDTO dto = PrecioController
                            .getInstance()
                            .obtenerItemPorTitulo(titulo);

                    if (dto != null) {
                        self.textFieldItemCodigo.setText(dto.codigo);
                    } else {
                        self.textFieldItemCodigo.setText("");
                    }
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
    //endregion

    void positionScreen() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2
        );
    }
}
