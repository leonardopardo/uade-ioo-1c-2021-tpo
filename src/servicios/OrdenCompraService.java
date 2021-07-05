package servicios;

import modelos.Factura;
import modelos.OrdenCompra;
import vendor.GenericDAO;

public class OrdenCompraService extends GenericDAO {

    public OrdenCompraService() throws Exception {
        super(OrdenCompra.class, "./src/json/ordenes_compra.json");
    }

    public int getProximoId() throws Exception {
        return this.getAll().size() + 1;
    }
}
