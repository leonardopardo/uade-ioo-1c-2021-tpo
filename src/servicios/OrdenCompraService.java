package servicios;

import modelos.Usuario;
import vendor.GenericDAO;

public class OrdenCompraService extends GenericDAO {


    public OrdenCompraService() throws Exception {
        super(Usuario.class, "./src/json/ordenesCompra.json");
    }

    public int getProximoNumero() throws Exception {
        return this.getAll().size() + 1;
    }

}
