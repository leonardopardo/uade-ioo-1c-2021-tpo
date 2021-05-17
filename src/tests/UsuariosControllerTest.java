package tests;

import factories.UsuariosFactory;
import modelos.Usuario;
import modelos.enums.Role;
import org.junit.jupiter.api.Test;
import controllers.UsuariosController;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UsuariosControllerTest {

    @Test
    void validar_credenciales_correctas_de_usuario() throws Exception {

        Usuario u1 = UsuariosFactory.create("Leonardo", "Pardo", "leopardo",
                "123123", LocalDate.of(1981, 06, 12), Role.OPERADOR);

        Usuario u2 = UsuariosFactory.create("Nicolás", "Pardo", "nicopardo",
                "456789", LocalDate.of(2011, 10, 4), Role.OPERADOR);

        Usuario u3 = UsuariosFactory.create("Federico", "Pardo", "fedepardo",
                "789456", LocalDate.of(2011, 10, 4), Role.OPERADOR);

        UsuariosController service = UsuariosController.getInstance();
        service.agregar(u1, u2, u3);

        assertTrue(service.validarCredenciales("leopardo", "123123".toCharArray()));
        assertTrue(service.validarCredenciales("nicopardo", "456789".toCharArray()));
        assertTrue(service.validarCredenciales("fedepardo", "789456".toCharArray()));
        assertFalse(service.validarCredenciales("leopardo", "789456".toCharArray()));

        service.destroy();
    }

    @Test
    void validar_credenciales_de_usuario_con_el_usuario_vacio() throws Exception {

        Usuario u1 = UsuariosFactory.create("Leonardo", "Pardo", "leopardo",
                "123123", LocalDate.of(1981, 06, 12), Role.OPERADOR);

        UsuariosController service = UsuariosController.getInstance();
        service.agregar(u1);

        assertFalse(service.validarCredenciales("", "123123".toCharArray()));

        service.destroy();
    }

    @Test
    void validar_credenciales_de_usuario_con_el_password_vacio() throws Exception {

        Usuario u1 = UsuariosFactory.create("Leonardo", "Pardo", "leopardo",
                "123123", LocalDate.of(1981, 06, 12), Role.OPERADOR);

        UsuariosController service = UsuariosController.getInstance();
        service.agregar(u1);

        assertFalse(service.validarCredenciales("leopardo", "".toCharArray()));

        service.destroy();
    }

    @Test
    void validar_existencia_de_usuario_cuando_el_usuario_existe() throws Exception {
        Usuario u1 = UsuariosFactory.create("Leonardo", "Pardo", "leopardo",
                "123123", LocalDate.of(1981, 06, 12), Role.OPERADOR);

        UsuariosController service = UsuariosController.getInstance();
        service.agregar(u1);

        Usuario ux = service.obtener("leopardo");

        assertEquals(ux, u1);

        service.destroy();
    }

    @Test
    void validar_la_existencia_de_usuario_cuando_el_usuario_no_existe() throws Exception {
        Usuario u1 = UsuariosFactory.create("Leonardo", "Pardo", "leopardo",
                "123123", LocalDate.of(1981, 06, 12), Role.OPERADOR);

        UsuariosController service = UsuariosController.getInstance();
        service.agregar(u1);

        Usuario ux = service.obtener("nicopardo");

        assertEquals(ux, null);

        service.destroy();
    }

    @Test
    void validar_que_el_role_del_usuario_es_operador() throws Exception {
        Usuario u1 = UsuariosFactory.create("Leonardo", "Pardo", "leopardo",
                "123123", LocalDate.of(1981, 06, 12), Role.OPERADOR);

        UsuariosController service = UsuariosController.getInstance();
        service.agregar(u1);

        Usuario ux = service.obtener("leopardo");

        assertEquals(ux.getRole(), Role.OPERADOR);
        assertNotEquals(ux.getRole(), Role.ADMINISTRADOR);

        service.destroy();
    }

    @Test
    void validar_que_el_role_del_usuario_es_administrador() throws Exception {
        Usuario u1 = UsuariosFactory.create("Leonardo", "Pardo", "leopardo",
                "123123", LocalDate.of(1981, 06, 12), Role.ADMINISTRADOR);

        UsuariosController service = UsuariosController.getInstance();
        service.agregar(u1);

        Usuario ux = service.obtener("leopardo");

        assertEquals(ux.getRole(), Role.ADMINISTRADOR);
        assertNotEquals(ux.getRole(), Role.OPERADOR);

        service.destroy();
    }
}
