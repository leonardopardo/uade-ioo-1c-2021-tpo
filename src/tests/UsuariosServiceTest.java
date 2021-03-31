package tests;

import factories.UsuariosFactory;
import modelos.Usuario;
import org.junit.jupiter.api.Test;
import servicios.UsuariosService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UsuariosServiceTest {

    @Test
    void validar_credenciales_correctas_de_usuario() throws Exception {

        Usuario u1 = UsuariosFactory.create("Leonardo", "Pardo", "leopardo",
                "123123", LocalDate.of(1981, 06, 12));

        Usuario u2 = UsuariosFactory.create("Nicolás", "Pardo", "nicopardo",
                "456789", LocalDate.of(2011, 10, 4));

        Usuario u3 = UsuariosFactory.create("Federico", "Pardo", "fedepardo",
                "789456", LocalDate.of(2011, 10, 4));

        UsuariosService service = UsuariosService.getInstance();
        service.agregar(u1);
        service.agregar(u2);
        service.agregar(u3);

        assertTrue(service.validarCredenciales("leopardo", "123123".toCharArray()));
        assertTrue(service.validarCredenciales("nicopardo", "456789".toCharArray()));
        assertTrue(service.validarCredenciales("fedepardo", "789456".toCharArray()));
        assertFalse(service.validarCredenciales("leopardo", "789456".toCharArray()));
    }

    @Test
    void validar_credenciales_de_usuario_con_el_usuario_vacio() throws Exception {

        Usuario u1 = UsuariosFactory.create("Leonardo", "Pardo", "leopardo",
                "123123", LocalDate.of(1981, 06, 12));

        UsuariosService service = UsuariosService.getInstance();
        service.agregar(u1);

        assertFalse(service.validarCredenciales("", "123123".toCharArray()));
    }

    @Test
    void validar_credenciales_de_usuario_con_el_password_vacio() throws Exception {

        Usuario u1 = UsuariosFactory.create("Leonardo", "Pardo", "leopardo",
                "123123", LocalDate.of(1981, 06, 12));

        UsuariosService service = UsuariosService.getInstance();
        service.agregar(u1);

        assertFalse(service.validarCredenciales("leopardo", "".toCharArray()));
    }

    @Test
    void validar_existencia_de_usuario_cuando_el_usuario_existe() throws Exception {
        Usuario u1 = UsuariosFactory.create("Leonardo", "Pardo", "leopardo",
                "123123", LocalDate.of(1981, 06, 12));

        UsuariosService service = UsuariosService.getInstance();
        service.agregar(u1);

        Usuario ux = service.obtener("leopardo");

        assertEquals(ux, u1);
    }

    @Test
    void validar_la_existencia_de_usuario_cuando_el_usuario_no_existe() throws Exception {
        Usuario u1 = UsuariosFactory.create("Leonardo", "Pardo", "leopardo",
                "123123", LocalDate.of(1981, 06, 12));

        UsuariosService service = UsuariosService.getInstance();
        service.agregar(u1);

        Usuario ux = service.obtener("nicopardo");

        assertEquals(ux, null);
    }
}
