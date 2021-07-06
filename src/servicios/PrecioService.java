package servicios;

import modelos.OrdenCompra;
import modelos.Precio;
import vendor.GenericDAO;

public class PrecioService extends GenericDAO {

    public PrecioService() throws Exception {
        super(Precio.class, "./src/json/precios.json");
    }
    public int getProximoId() throws Exception {
        return this.getAll().size() + 1;
    }
}