package servicios;

import modelos.Item;
import modelos.OrdenCompra;
import vendor.GenericDAO;

public class ItemsService extends GenericDAO {

    public ItemsService() throws Exception {
        super(Item.class, "./src/json/items.json");
    }

    public int getProximoId() throws Exception {
        return this.getAll().size() + 1;
    }

}
