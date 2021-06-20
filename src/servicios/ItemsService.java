package servicios;

import modelos.OrdenCompra;
import vendor.GenericDAO;

public class ItemsService extends GenericDAO {

    public ItemsService() throws Exception {
        super(OrdenCompra.class, "./src/json/items.json");
    }

}
