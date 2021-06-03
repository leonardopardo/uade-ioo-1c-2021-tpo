package servicios;

import modelos.Proveedor;
import vendor.GenericDAO;

public class ProveedoreService extends GenericDAO {

    public ProveedoreService() throws Exception {
        super(Proveedor.class, "./src/json/proveedores.json");
    }

}
