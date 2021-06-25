package servicios;

import modelos.Proveedor;
import vendor.GenericDAO;

public class ProveedoreService extends GenericDAO {

    public ProveedoreService() throws Exception {
        super(Proveedor.class, "./src/json/proveedores.json");
    }

    public int getProximoId() throws Exception {
        return this.getAll().size() + 1;
    }

}
