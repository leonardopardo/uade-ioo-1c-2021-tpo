package seeders;

import factories.UsuariosFactory;
import modelos.Usuario;
import modelos.enums.Role;
import servicios.UsuariosService;

import java.time.LocalDate;

public class UsuarioSeeder {

    public static void run() throws Exception {

        Usuario u1 = UsuariosFactory.create(
                "Leonardo",
                "Pardo",
                "leopardo",
                "123123",
                LocalDate.of(1981, 06, 12),
                Role.OPERADOR
        );

        Usuario u2 = UsuariosFactory.create(
                "Nicolás",
                "Pardo",
                "nicopardo",
                "456789",
                LocalDate.of(2011, 10, 4),
                Role.OPERADOR
        );

        Usuario u3 = UsuariosFactory.create(
                "Federico",
                "Pardo",
                "fedepardo",
                "789456",
                LocalDate.of(2016, 12, 24),
                Role.OPERADOR
        );

        Usuario u4 = UsuariosFactory.create(
                "Administador",
                "Administrador",
                "admin",
                "123123",
                LocalDate.of(1900, 1, 1),
                Role.ADMINISTRADOR
        );

        UsuariosService service = UsuariosService.getInstance();
        service.agregar(u1, u2, u3, u4);
    }
}
