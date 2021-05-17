package tests;

import factories.UsuariosFactory;
import modelos.Usuario;
import modelos.enums.Role;
import org.junit.jupiter.api.Test;
import controllers.UsuariosController;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioModelTest {
    @Test
    void validar_edad_de_usuario() throws Exception {
        Usuario u1 = UsuariosFactory.create("Leonardo", "Pardo", "leopardo",
                "123123", LocalDate.of(1981, 06, 12), Role.OPERADOR);

        UsuariosController service = UsuariosController.getInstance();
        service.agregar(u1);

        assertEquals(39, service.obtener("leopardo").getEdad());
    }
}
