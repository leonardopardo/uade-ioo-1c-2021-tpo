package seeders;

import factories.UsuariosFactory;
import modelos.Usuario;
import servicios.UsuariosService;

import java.time.LocalDate;

public class UsuarioSeeder {

    public static void run() throws Exception {

        Usuario u1 = UsuariosFactory.create(
                "Leonardo",
                "Pardo",
                "leopardo",
                "123123",
                LocalDate.of(1981, 06, 12)
        );

        Usuario u2 = UsuariosFactory.create(
                "Nicolás",
                "Pardo",
                "nicopardo",
                "456789",
                LocalDate.of(2011, 10, 4)
        );

        Usuario u3 = UsuariosFactory.create(
                "Federico",
                "Pardo",
                "fedepardo",
                "789456",
                LocalDate.of(2016, 12, 24)
        );

        Usuario u4 = UsuariosFactory.create(
                "Administador",
                "Administrador",
                "admin",
                "123123",
                LocalDate.of(1900, 1, 1)
        );

        UsuariosService service = UsuariosService.getInstance();
        service.agregar(u1);
        service.agregar(u2);
        service.agregar(u3);
        service.agregar(u4);
    }
}
