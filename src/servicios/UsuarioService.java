package servicios;

import modelos.Usuario;
import vendor.GenericDAO;


public class UsuarioService extends GenericDAO {

    public UsuarioService() throws Exception {
        super(Usuario.class, "./src/json/usuarios.json");
    }

}
