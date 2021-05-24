package tests;

import modelos.Usuario;
import org.junit.jupiter.api.Test;
import servicios.ConnectionService;
import servicios.dal.Usuarios;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDALTest {

    @Test
    public void puede_conectar_a_la_base_de_datos() throws Exception{
        ConnectionService s = new ConnectionService();
        assertNotNull(s.connect());
        s.disconnect();
    }

    @Test
    public void puede_acceder_a_la_tabla_usuarios() throws Exception{
        Usuarios usuariosDal = new Usuarios();
        List<Usuario> listaUsuarios = usuariosDal.list();

        assertEquals(4, listaUsuarios.size());
    }
}
