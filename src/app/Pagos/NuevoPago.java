package app.Pagos;

import controllers.ProveedorController;
import dto.ProveedorDTO;
import helpers.Helpers;
import modelos.enums.EstadoPago;
import modelos.enums.TipoPago;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class NuevoPago extends JDialog implements ActionListener {
    private JComboBox comboBoxProveedores;
    private JTextField textFieldCUIT;
    private JPanel pnlMain;
    private JPanel pnlHeader;
    private JLabel lblCuit;
    private JLabel lblProveedor;
    private JPanel pnlContainerFecha;
    private JLabel lblFechaPago;
    private JPanel pnlFormProveedor;
    private JPanel pnlFormCuit;
    private JPanel pnlFormFecha;
    private JLabel lblEstado;
    private JComboBox comboBoxEstadoPago;
    private JPanel pnlEstadoPago;
    private JButton button1;
    private JDatePickerImpl datePickerFecha;

    public NuevoPago(JFrame parent) {
        super(parent);

        this.onChangeComboBoxProveedor();

        this.setDefaults();
    }

    public NuevoPago(JFrame parent, Integer orden) {
        super(parent);

        this.comboBoxProveedores.setEditable(false);

        this.setDefaults();
    }

    //region Populate
    void populateComboBoxEstadoPago() {
        for (EstadoPago estado : EstadoPago.values()) {
            this.comboBoxEstadoPago.addItem(estado);
        }
    }

    void populateComboBoxProveedores(){
        try {
            List<ProveedorDTO> proveedores = ProveedorController.getInstance().listar();

            this.comboBoxProveedores.addItem("-- Seleccione --");
            proveedores.stream().forEach(x -> {
                this.comboBoxProveedores.addItem(x.razonSocial);
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

    //region Actions
    void onChangeComboBoxProveedor(){
        NuevoPago self = this;
        this.comboBoxProveedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String razonSocial = self.comboBoxProveedores.getSelectedItem().toString();

                    ProveedorDTO proveedor = ProveedorController.getInstance().obtenerPorRazonSocial(razonSocial);

                    if(proveedor != null)
                        self.textFieldCUIT.setText(proveedor.cuit);
                    else
                        self.textFieldCUIT.setText("");

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

    void positionScreen() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(
                dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2
        );
    }
    //endregion

    //region Settings
    void setDefaults() {

        this.populateComboBoxProveedores();
        this.populateComboBoxEstadoPago();
        this.textFieldCUIT.setEditable(false);

        this.datePickerFecha = Helpers.nuevoDatePicker();
        Helpers.appendDatePicker(this.pnlContainerFecha, this.datePickerFecha);

        this.setContentPane(this.pnlMain);
        this.setResizable(false);
        this.setModal(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(this.pnlMain.getPreferredSize());
        this.pack();
        this.positionScreen();
        this.setVisible(true);
    }
    //endregion

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
