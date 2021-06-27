package servicios;

import modelos.OrdenCompra;
import vendor.GenericDAO;

public class PrecioService extends GenericDAO {

    public PrecioService() throws Exception {
        super(OrdenCompra.class, "./src/json/precios.json");
    }
}