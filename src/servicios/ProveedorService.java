package servicios;

import modelos.Proveedor;
import vendor.GenericDAO;

public class ProveedorService extends GenericDAO {

    public ProveedorService() throws Exception {
        super(Proveedor.class, "./src/json/proveedores.json");
    }

    public int getProximoId() throws Exception {
        return this.getAll().size() + 1;
    }

}
