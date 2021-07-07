package app.Documentos.Factura;

import app.Documentos.Main.Documentos;
import controllers.DocumentoController;
import controllers.PrecioController;
import controllers.ProveedorController;
import dto.DetalleDTO;
import dto.FacturaDTO;
import dto.ItemDTO;
import dto.ProveedorDTO;
import helpers.Helpers;
import modelos.enums.AlicuotaIVA;
import modelos.enums.Rubro;
import modelos.enums.Unidad;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Formulario extends JDialog {
    private JPanel pnlFormCabecera;
    private JLabel lblProveedor;
    private JComboBox comboBoxProveedorFact;
    private JLabel lblCuit;
    private JTextField textFieldCuit;
    private JPanel pnlFormDetalle;
    private JPanel pnlItem;
    private JLabel lblItem;
    private JLabel lblCantidad;
    private JButton btnAgregarDetalle;
    private JButton eliminarButton;
    private JLabel lblDescription;
    private JPanel pnlTable;
    private JTable tblDetalle;
    private JPanel pnlActions;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JPanel pnlMain;
    private JComboBox comboBoxRubrosFact;
    private JTextField subtotalFact;
    private JTextField iva21Fact;
    private JTextField iva10_5Fact;
    private JTextField totalFact;
    private JComboBox comboBoxItems;
    private JTextField textFieldCodItem;
    private JSpinner spinnerCantFact;
    private JPanel pnlFechaFact;
    private JLabel lblFecha;
    private JDatePickerImpl datePickerfechaFact;
    private List<DetalleDTO> detalle;

    public Formulario(JFrame parent) {
        super(parent);
        // region Initialize
        this.detalle = new ArrayList<>();
        this.iva10_5Fact.setText("0.0");
        this.iva21Fact.setText("0.0");
        this.totalFact.setText("0.0");
        this.subtotalFact.setText("0.0");

        this.textFieldCuit.setEditable(false);
        this.spinnerCantFact.setModel(new SpinnerNumberModel(1, 1, 1000, 1));

        //region Register Actions
        this.actionSelectedProveedor();
        this.actionSelectedComboBoxItem();
        this.actionAgregarDetalle();
        this.actionEliminarDetalle();
        this.actionGuardarFactura();

        //region Factory
        this.datePickerfechaFact = Helpers.nuevoDatePicker();
        Helpers.appendDatePicker(this.pnlFechaFact, this.datePickerfechaFact);

        //region Populate
        this.populateComboBoxProevedores();
        this.populateRubros();
        this.populateComboBoxItems();

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


    //region Populate
    void populateRubros() {
        for (Rubro r : Rubro.values()) {
            this.comboBoxRubrosFact.addItem(r);
        }
    }

    void populateComboBoxProevedores() {
        try {
            List<ProveedorDTO> proveedores = ProveedorController.getInstance().listar();

            this.comboBoxProveedorFact.addItem("-- Seleccione --");
            proveedores.stream().forEach(x -> {
                this.comboBoxProveedorFact.addItem(x.razonSocial);
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

    void populateComboBoxItems() {
        try {
            List<ItemDTO> items = PrecioController.getInstance().listarItems();

            this.comboBoxItems.addItem("-- Seleccione --");

            items.forEach(item -> {
                this.comboBoxItems.addItem(item.titulo);
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

    //endregion


    // region Actions

    void actionSelectedProveedor() {
        Formulario self = this;
        this.comboBoxProveedorFact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String razonSocial = self.comboBoxProveedorFact.getSelectedItem().toString();
                    ProveedorDTO dto = ProveedorController.getInstance().obtenerPorRazonSocial(razonSocial);

                    if (dto != null) {
                        self.textFieldCuit.setText(dto.cuit);
                    } else {
                        self.textFieldCuit.setText("");
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


    void actionSelectedComboBoxItem() {
        Formulario self = this;
        this.comboBoxItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String selectItemCod = self.comboBoxItems.getSelectedItem().toString();
                    ItemDTO item = PrecioController.getInstance().obtenerItemPorTitulo(selectItemCod);

                    if (item != null)
                        self.textFieldCodItem.setText(item.codigo);
                    else
                        self.textFieldCodItem.setText("");

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

    void actionGuardarFactura() {
        Formulario self = this;
        this.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FacturaDTO factDTO = new FacturaDTO();
                    factDTO.detalles = self.detalle;
                    factDTO.cuitProveedor = self.textFieldCuit.getText();
                    factDTO.fecha = Helpers.datePickerFormatter(self.datePickerfechaFact);
                    factDTO.monto = Double.parseDouble(self.totalFact.getText());
                    factDTO.iva21 = Double.parseDouble(self.iva21Fact.getText());
                    factDTO.iva10 = Double.parseDouble(self.iva10_5Fact.getText());
                    DocumentoController.getInstance().agregarFactura(factDTO);
                    self.dispose();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    void actionAgregarDetalle() {
        Formulario self = this;
        this.btnAgregarDetalle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String itemCodigo = self.textFieldCodItem.getText();

                    if (itemCodigo.equals(""))
                        throw new Exception("Debe seleccionar un producto o servicio");

                    ItemDTO item = PrecioController.getInstance().obtenerItemPorCodigo(itemCodigo);
                    Double cantidad = Double.parseDouble(self.spinnerCantFact.getValue().toString());
                    Double precio = PrecioController.getInstance().buscarPrecio(self.textFieldCuit.getText(), itemCodigo);


                    Integer element = itemAgregado(itemCodigo);

                    if (element >= 0) {
                        DetalleDTO d = self.detalle.get(element);
                        d.cantItem = cantidad;
                        d.alicuotaIVA = item.alicuotaIVA;
                        d.cantItem = cantidad;
                        d.precioTotal = precio * cantidad;
                        d.setIva();
                        self.detalle.set(element, d);
                        self.updateFactura(d);
                    } else {
                        DetalleDTO d = new DetalleDTO();
                        d.alicuotaIVA = item.alicuotaIVA;
                        d.codItem = itemCodigo;
                        d.cantItem = cantidad;
                        d.descripcion = item.titulo;
                        d.precioUnidad = precio;
                        d.precioTotal = precio * cantidad;
                        d.setIva();
                        self.detalle.add(d);
                        self.updateFactura(d);
                    }


                    self.setTableSchemma();

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

    void actionEliminarDetalle() {
        Formulario self = this;
        this.eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    int row = self.tblDetalle.getSelectedRow();

                    if (row < 0)
                        throw new Exception("Debe seleccionar una fila de la tabla");

                    String value = self.tblDetalle.getModel().getValueAt(row, 0).toString();
                    int itemIndex = self.itemAgregado(value);

                    if (itemIndex >= 0) {

                        int confirmResult = JOptionPane.showConfirmDialog(
                                pnlMain,
                                "¿Está seguro que desea eliminar la fila?",
                                "Eliminar Fila",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirmResult == JOptionPane.YES_OPTION) {
                            self.detalle.remove(itemIndex);
                            self.setTableSchemma();
                        }
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


    // region Settings

    void setTableSchemma() {
        try {
            String[] columns = new String[]{
                    "CÓDIGO",
                    "DESCRIPCIÓN",
                    "CANTIDAD",
                    "UNIDAD",
                    "PRECIO",
                    "IMPUESTO",
                    "PRECIO TOTAL"
            };

            DefaultTableModel tblModel = new DefaultTableModel(columns, 0);

            this.detalle.forEach(d -> {
                Object[] o = {
                        d.codItem,
                        d.descripcion,
                        d.cantItem,
                        Unidad.UNIDAD,
                        d.precioUnidad,
                        (d.iva * d.precioTotal) / 100,
                        d.precioTotal
                };

                tblModel.addRow(o);
            });

            this.tblDetalle.setModel(tblModel);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    pnlMain,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    void positionScreen() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2
        );
    }

    // region Helper
    Integer itemAgregado(String itemCodigo) {
        for (DetalleDTO d : this.detalle) {
            if (d.codItem.equals(itemCodigo))
                return this.detalle.indexOf(d);
        }
        return -1;
    }


    // region update

    void updateFactura(DetalleDTO dto) {
        if (dto.alicuotaIVA.equals(AlicuotaIVA.IVA_10_5)) {
            Double iva10 = Double.parseDouble(iva10_5Fact.getText()) + ((dto.iva * dto.precioTotal) / 100);
            this.iva10_5Fact.setText(iva10.toString());
        } else if (dto.alicuotaIVA.equals(AlicuotaIVA.IVA_21)) {
            Double iva21 = Double.parseDouble(iva21Fact.getText()) + ((dto.iva * dto.precioTotal) / 100);
            this.iva21Fact.setText(iva21.toString());
        }
        Double subtotal = Double.parseDouble(subtotalFact.getText()) + dto.precioTotal;
        Double total = Double.parseDouble(totalFact.getText()) + ((dto.iva * dto.precioTotal) / 100) + dto.precioTotal;

        this.totalFact.setText(total.toString());
        this.subtotalFact.setText(subtotal.toString());
    }


}
