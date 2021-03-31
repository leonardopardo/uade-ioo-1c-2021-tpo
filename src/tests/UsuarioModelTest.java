package tests;

import factories.UsuariosFactory;
import modelos.Usuario;
import org.junit.jupiter.api.Test;
import servicios.UsuariosService;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioModelTest {
    @Test
    void validar_edad_de_usuario() throws Exception {
        Usuario u1 = UsuariosFactory.create("Leonardo", "Pardo", "leopardo",
                "123123", LocalDate.of(1981, 06, 12));

        UsuariosService service = UsuariosService.getInstance();
        service.agregar(u1);

        assertEquals(39, service.obtener("leopardo").getEdad());
    }
}
