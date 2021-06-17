package servicios;

import modelos.OrdenCompra;
import vendor.GenericDAO;

public class OrdenCompraService extends GenericDAO {


    public OrdenCompraService() throws Exception {
        super(OrdenCompra.class, "./src/json/ordenesCompra.json");
    }

    public int getProximoNumero() throws Exception {
        return this.getAll().size() + 1;
    }

}
