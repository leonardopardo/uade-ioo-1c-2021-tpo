package app.Productos;

import app.Main.Main;
import controllers.PrecioController;
import controllers.ProveedorController;
import dto.ItemDTO;
import dto.PrecioDTO;
import dto.ProveedorDTO;
import modelos.enums.Rubro;
import modelos.enums.TipoItem;
import modelos.enums.Unidad;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class Productos extends JFrame {
    private JPanel pnlMain;
    private JPanel pnlHeader;
    private JPanel pnlBody;
    private JTabbedPane tabbedPane;
    private JTable tableItems;
    private JButton btnNuevoItem;
    private JButton btnModificarItem;
    private JButton btnEliminarItem;
    private JButton btnFiltrarItems;
    private JButton btnLimpiarFiltroItems;
    private JComboBox comboBoxTipoItem;
    private JComboBox comboBoxRubroItem;
    private JComboBox comboBoxUnidadItem;
    private JTable tablePrecios;
    private JComboBox comboBoxRubroPrecio;
    private JComboBox comboBoxItemPrecio;
    private JTextField textFieldItemCodigoPrecio;
    private JComboBox comboBoxProveedorPrecio;
    private JTextField textFieldProveedorCuitPrecio;
    private JPanel pnlTabItems;
    private JPanel pnlTabPrecios;
    private JButton btnNuevoPrecio;
    private JButton btnModificarPrecio;
    private JButton btnEliminarPrecio;
    private JButton btnFiltrarPrecios;
    private JButton btnLimpiarFiltroPrecios;
    private JLabel lblTipoItem;
    private JLabel lblRubroItem;
    private JLabel lblUnidadItem;
    private JPanel pnlItemsFilters;
    private JPanel pnlItemsTable;
    private JPanel pnlItemsActions;
    private JPanel pnlPrecioFilters;
    private JPanel pnlPrecioTable;
    private JPanel pnlPrecioActions;
    private JLabel lblRubroPrecio;
    private JLabel lblItemPrecio;
    private JLabel lblItemCodigoPrecio;
    private JLabel lblProveedorPrecio;
    private JLabel lblProveedorCuitPrecio;
    private JDialog nuevoItem;
    private JDialog nuevoPrecio;
    private List<ItemDTO> items;
    private List<PrecioDTO> precios;

    public Productos(String title) {
        super(title);

        //region Loaders
        this.loadItems();
        this.loadPrecios();
        this.loadTableItems();
        this.loadTablePrecios();
        this.loadComboBoxItems();
        this.loadComboBoxProveedores();
        this.loadComboBoxRubros();
        this.loadComboBoxTipo();
        this.loadComboBoxUnidad();
        //endregion

        //region Settings
        this.setContentPane(this.pnlMain);
        this.setSize(pnlMain.getPreferredSize());
        this.setResizable(false);
        this.positionScreen();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        //endregion

        //region Actions
        this.closeModule();
        this.actionOnClickNuevoItem();
        this.actionOnClickNuevoPrecio();
        this.actionOnClickModificarPrecio();
        this.actionOnClickEliminarPrecio();
        this.actionOnClickFiltrarPrecios();
        this.actionOnClickLimpiarFiltroPrecios();
        this.actionOnChangeComboBoxProveedores();
        this.actionOnChangeComboBoxItems();
        this.actionOnClickModificarItem();
        this.actionOnClickEliminarItem();
        this.actionOnClickFiltrarItems();
        this.actionOnClickLimpiarFiltroItems();
        //endregion
    }

    //region Loaders
    void loadComboBoxProveedores() {
        try {
            List<ProveedorDTO> proveedores = ProveedorController.getInstance().listar();

            this.comboBoxProveedorPrecio.addItem("-- Seleccione --");

            proveedores.stream().forEach(x -> {
                this.comboBoxProveedorPrecio.addItem(x.razonSocial);
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

    void loadComboBoxRubros() {
        this.comboBoxRubroItem.addItem("-- Seleccione --");
        this.comboBoxRubroPrecio.addItem("-- Seleccione --");
        for (Rubro r : Rubro.values()) {
            this.comboBoxRubroItem.addItem(r);
            this.comboBoxRubroPrecio.addItem(r);
        }
    }

    void loadComboBoxTipo() {
        this.comboBoxTipoItem.addItem("-- Seleccione --");
        for (TipoItem t : TipoItem.values()) {
            this.comboBoxTipoItem.addItem(t);
        }
    }

    void loadComboBoxItems() {
        this.comboBoxItemPrecio.addItem("-- Seleccione --");
        for (ItemDTO item : this.items) {
            this.comboBoxItemPrecio.addItem(item.titulo);
        }
    }

    void loadComboBoxUnidad() {
        this.comboBoxUnidadItem.addItem("-- Seleccione --");
        ;
        for (Unidad u : Unidad.values()) {
            this.comboBoxUnidadItem.addItem(u);
        }
    }

    void loadTableItems() {
        try {

            String[] columns = new String[]{
                    "C??DIGO",
                    "T??TULO",
                    "RUBRO",
                    "UNIDAD"
            };

            DefaultTableModel tblModel = new DefaultTableModel(columns, 0);

            this.items.stream().forEach(x -> {
                Object[] o = {
                        x.codigo,
                        x.titulo,
                        x.rubro,
                        x.unidad,
                };
                tblModel.addRow(o);
            });

            this.tableItems.setModel(tblModel);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    pnlMain,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    void loadTablePrecios() {
        try {

            String[] columns = new String[]{
                    "RUBRO",
                    "C??DIGO",
                    "DESCRIPCI??N",
                    "PROVEEDOR",
                    "CUIT",
                    "PRECIO"
            };

            DefaultTableModel tblModel = new DefaultTableModel(columns, 0);

            this.precios.stream().forEach(x -> {
                Object[] o = {
                        x.rubro,
                        x.itemCodigo,
                        x.itemTitulo,
                        x.razonSocial,
                        x.cuit,
                        x.precio
                };
                tblModel.addRow(o);
            });

            this.tablePrecios.setModel(tblModel);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    pnlMain,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    void loadItems() {
        try {
            this.items = PrecioController.getInstance().listarItems();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    pnlMain,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    void loadPrecios() {
        try {
            this.precios = PrecioController.getInstance().listarPrecios();
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
    void actionOnClickNuevoItem() {
        Productos self = this;
        this.btnNuevoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.nuevoItem = new Items(self);
            }
        });
    }

    void actionOnClickModificarItem() {
        Productos self = this;
        this.btnModificarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    int row = self.tableItems.getSelectedRow();

                    if(row < 0)
                        throw new Exception("Debe seleccionar una fila de la tabla.");

                    ItemDTO item = self.items.get(row);

                    self.nuevoItem = new Items(self, item);

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

    void actionOnClickEliminarItem() {
        Productos self = this;
        this.btnEliminarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    int row = self.tableItems.getSelectedRow();

                    String codigoItem = self.tableItems.getValueAt(row, 0).toString();

                    int confirmResult = JOptionPane.showConfirmDialog(
                            pnlMain,
                            "??Est?? seguro que desea eliminar el registro?",
                            "Cerrar",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirmResult == JOptionPane.YES_OPTION) {
                        PrecioController.getInstance().eliminar(codigoItem);
                        self.items.remove(row);
                        self.loadTableItems();
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

    void actionOnClickFiltrarItems() {
        Productos self = this;
        this.btnFiltrarItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PrecioController controller = PrecioController.getInstance();

                    Rubro rubro = null;

                    TipoItem tipo = null;

                    Unidad unidad = null;

                    if (self.comboBoxRubroItem.getSelectedIndex() > 0)
                        rubro = Rubro.valueOf(self.comboBoxRubroItem.getSelectedItem().toString());

                    if (self.comboBoxTipoItem.getSelectedIndex() > 0)
                        tipo = TipoItem.valueOf(self.comboBoxTipoItem.getSelectedItem().toString());

                    if (self.comboBoxUnidadItem.getSelectedIndex() > 0)
                        unidad = Unidad.valueOf(self.comboBoxUnidadItem.getSelectedItem().toString());

                    self.items = null;

                    if (rubro != null && tipo == null && unidad == null) {
                        self.items = controller.listarItems(rubro);
                    } else if (rubro != null && tipo != null && unidad == null) {
                        self.items = controller.listarItemsPorTipo(rubro, tipo);
                    } else if (rubro != null && tipo == null && unidad != null) {
                        self.items = controller.listarItemsPorUnidad(rubro, unidad);
                    } else if (rubro != null && tipo != null && unidad != null) {
                        self.items = controller.listarItems(rubro, tipo, unidad);
                    } else if (rubro == null && tipo != null && unidad == null) {
                        self.items = controller.listarItemsPorTipo(tipo);
                    } else if (rubro == null && tipo != null && unidad != null) {
                        self.items = controller.listarItems(tipo, unidad);
                    } else if (rubro == null && tipo == null && unidad != null) {
                        self.items = controller.listarItemsPorUnidad(unidad);
                    } else {
                        self.items = controller.listarItems();
                    }

                    self.loadTableItems();

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

    void actionOnClickLimpiarFiltroItems() {
        Productos self = this;
        this.btnLimpiarFiltroItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    self.comboBoxRubroItem.setSelectedIndex(0);
                    self.comboBoxUnidadItem.setSelectedIndex(0);
                    self.comboBoxTipoItem.setSelectedIndex(0);
                    self.loadItems();
                    self.loadTableItems();
                } catch (Exception ex) {

                }
            }
        });
    }

    void actionOnClickNuevoPrecio() {
        Productos self = this;
        this.btnNuevoPrecio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    self.nuevoPrecio = new Precios(self);
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

    void actionOnClickModificarPrecio() {
        Productos self = this;
        this.btnModificarPrecio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    int row = self.tablePrecios.getSelectedRow();

                    if (row < 0)
                        throw new Exception("Debe seleccionar una fila de la tabla.");

                    PrecioDTO precio = self.precios.get(row);

                    self.nuevoPrecio = new Precios(self, precio);

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

    void actionOnClickEliminarPrecio() {
        Productos self = this;
        this.btnEliminarPrecio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    int row = self.tablePrecios.getSelectedRow();

                    String codigoItem = self.tablePrecios.getValueAt(row, 0).toString();

                    String cuitProveedor = self.tablePrecios.getValueAt(row, 2).toString();

                    int confirmResult = JOptionPane.showConfirmDialog(
                            pnlMain,
                            "??Est?? seguro que desea eliminar el registro?",
                            "Cerrar",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirmResult == JOptionPane.YES_OPTION) {
                        PrecioController.getInstance().eliminar(codigoItem, cuitProveedor);
                        self.precios.remove(row);
                        self.loadTablePrecios();
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

    void actionOnClickFiltrarPrecios() {
        Productos self = this;
        this.btnFiltrarPrecios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PrecioController controller = PrecioController.getInstance();

                    Rubro rubro = null;

                    String codigoItem = null;

                    String cuitProveedor = null;

                    if (self.comboBoxRubroPrecio.getSelectedIndex() > 0)
                        rubro = Rubro.valueOf(self.comboBoxRubroPrecio.getSelectedItem().toString());

                    if (!self.textFieldItemCodigoPrecio.getText().equals(""))
                        codigoItem = self.textFieldItemCodigoPrecio.getText();

                    if (!self.textFieldProveedorCuitPrecio.getText().equals(""))
                        cuitProveedor = self.textFieldProveedorCuitPrecio.getText();

                    self.precios = null;

                    if (rubro != null && codigoItem == null && cuitProveedor == null) {
                        self.precios = controller.listarPrecios(rubro);
                    } else if (rubro != null && codigoItem != null && cuitProveedor == null) {
                        self.precios = controller.listarPreciosPorItem(rubro, codigoItem);
                    } else if (rubro != null && codigoItem == null && cuitProveedor != null) {
                        self.precios = controller.listarPreciosPorProveedor(rubro, cuitProveedor);
                    } else if (rubro != null && codigoItem != null && cuitProveedor != null) {
                        self.precios = controller.listarPrecios(rubro, codigoItem, cuitProveedor);
                    } else if (rubro == null && codigoItem != null && cuitProveedor == null) {
                        self.precios = controller.listarPreciosPorItem(codigoItem);
                    } else if (rubro == null && codigoItem != null && cuitProveedor != null) {
                        self.precios = controller.listarPrecios(codigoItem, cuitProveedor);
                    } else if (rubro == null && codigoItem == null && cuitProveedor != null) {
                        self.precios = controller.listarPreciosPorProveedor(cuitProveedor);
                    } else {
                        self.precios = controller.listarPrecios();
                    }

                    self.loadTablePrecios();

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

    void actionOnClickLimpiarFiltroPrecios() {
        Productos self = this;
        this.btnLimpiarFiltroPrecios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    self.comboBoxRubroPrecio.setSelectedIndex(0);
                    self.comboBoxItemPrecio.setSelectedIndex(0);
                    self.comboBoxProveedorPrecio.setSelectedIndex(0);
                    self.textFieldProveedorCuitPrecio.setText("");
                    self.textFieldItemCodigoPrecio.setText("");
                    self.loadPrecios();
                    self.loadTablePrecios();
                } catch (Exception ex) {

                }
            }
        });
    }

    void actionOnChangeComboBoxProveedores() {
        Productos self = this;
        this.comboBoxProveedorPrecio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String razonSocial = self.comboBoxProveedorPrecio
                            .getSelectedItem()
                            .toString();

                    ProveedorDTO dto = ProveedorController
                            .getInstance()
                            .obtenerPorRazonSocial(razonSocial);

                    if (dto != null) {
                        self.textFieldProveedorCuitPrecio.setText(dto.cuit);
                    } else {
                        self.textFieldProveedorCuitPrecio.setText("");
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
        Productos self = this;
        this.comboBoxItemPrecio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String titulo = self.comboBoxItemPrecio
                            .getSelectedItem()
                            .toString();

                    ItemDTO dto = PrecioController
                            .getInstance()
                            .obtenerItemPorTitulo(titulo);

                    if (dto != null) {
                        self.textFieldItemCodigoPrecio.setText(dto.codigo);
                    } else {
                        self.textFieldItemCodigoPrecio.setText("");
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

    //region Settings
    void positionScreen() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2
        );
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
    //endregion
}
