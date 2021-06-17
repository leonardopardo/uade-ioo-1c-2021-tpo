package servicios;

import modelos.OrdenCompra;
import vendor.GenericDAO;


public class UsuarioService extends GenericDAO {

    public UsuarioService() throws Exception {
        super(OrdenCompra.class, "./src/json/usuarios.json");
    }

}
