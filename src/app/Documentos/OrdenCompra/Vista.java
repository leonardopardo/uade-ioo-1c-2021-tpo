package app.Documentos.OrdenCompra;

import controllers.DocumentosController;
import dto.OrdenCompraDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Vista extends JPanel {
    private JPanel pnlMain;
    private JTable tableOrdenesCompra;
    private JComboBox comboBoxProveedores;
    private JButton buscarButton;
    private JButton nuevaOrdenButton;
    private JButton cancelarButton;

    public Vista(){

        this.add(this.pnlMain);

        //region Populate
        this.populateTable();
        //endregion
    }

    void populateTable(){

        try{
            List<OrdenCompraDTO> ordenes = DocumentosController.getInstance().listarOrdenes();

            String[] columns = new String[]{
                    "NÚMERO".toUpperCase(),
                    "FECHA",
                    "RAZON SOCIAL".toUpperCase(),
                    "CUIT".toUpperCase()
            };

            DefaultTableModel tblModel = new DefaultTableModel(columns, 0);

            ordenes.stream().forEach(x -> {
                Object[] o = {
                        x.numero,
                        x.fecha,
                        x.razonSocial,
                        x.cuitProveedor,
                };

                tblModel.addRow(o);
            });

            this.tableOrdenesCompra.setModel(tblModel);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                pnlMain,
                ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
