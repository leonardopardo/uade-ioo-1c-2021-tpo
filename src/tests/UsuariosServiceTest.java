package tests;

import dto.UsuarioDTO;
import modelos.Usuario;
import modelos.enums.Role;
import org.junit.jupiter.api.Test;
import servicios.UsuarioService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UsuariosServiceTest {

    @Test
    public void puede_guardar_un_usuario() throws Exception {

        UsuarioDTO u1 = new UsuarioDTO();
        u1.nombre = "Leonardo";
        u1.apellido = "Pardo";
        u1.edad = LocalDate.of(1981, 06, 12);
        u1.username = "leopardo";
        u1.role = Role.ADMINISTRADOR;
        u1.id = 1;
        u1.password = "123123";

        UsuarioService service = new UsuarioService();
        service.save(new Usuario(u1));

        Usuario usuario = (Usuario) service.search(u1.id);

        assertEquals("leopardo@mail.com", usuario.getUsername());

        service.delete(u1.id);
    }

}
