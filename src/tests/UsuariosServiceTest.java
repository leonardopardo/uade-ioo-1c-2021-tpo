package tests;

import modelos.Usuario;
import org.junit.jupiter.api.Test;
import servicios.ConnectionService;
import servicios.UsuarioService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsuariosServiceTest {

    @Test
    public void puede_conectar_a_la_base_de_datos() throws Exception{
        ConnectionService service = new ConnectionService();
        assertNotNull(service.connect());
        service.disconnect();
    }

    @Test
    public void puede_obtener_una_lista_de_usuarios() throws Exception {
        UsuarioService service = new UsuarioService();
        List<Usuario> usuarios = service.list();

        assertEquals(5, usuarios.size());
    }

    @Test
    public void puede_obtener_un_usuarios_dado_un_id() throws Exception {
        UsuarioService service = new UsuarioService();
        assertEquals("leonardo".toLowerCase(), service.find(1).getNombre().toLowerCase());
    }

}
