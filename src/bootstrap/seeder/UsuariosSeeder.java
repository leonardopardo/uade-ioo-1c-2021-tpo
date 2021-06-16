package bootstrap.seeder;

import dto.UsuarioDTO;
import modelos.Usuario;
import modelos.enums.Role;
import servicios.UsuarioService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuariosSeeder {

    public static void runSeeder() throws Exception{

        List<Usuario> usuarios = new ArrayList<>();

        UsuarioDTO u1 = new UsuarioDTO();
        u1.nombre = "Leonardo";
        u1.apellido = "Pardo";
        u1.edad = LocalDate.of(1981, 06, 12);
        u1.username = "leopardo@mail.com";
        u1.role = Role.ADMINISTRADOR;
        u1.id = 1;
        u1.password = "123123";

        UsuarioDTO u2 = new UsuarioDTO();
        u2.nombre = "Nicolás";
        u2.apellido = "Pardo";
        u2.edad = LocalDate.of(2011, 10, 4);
        u2.username = "nicopardo@mail.com";
        u2.role = Role.ADMINISTRADOR;
        u2.id = 2;
        u2.password = "123123";

        UsuarioDTO u3 = new UsuarioDTO();
        u3.nombre = "Federico";
        u3.apellido = "Pardo";
        u3.edad = LocalDate.of(2016, 12, 24);
        u3.username = "fedepardo@mail.com";
        u3.role = Role.ADMINISTRADOR;
        u3.id = 3;
        u3.password = "123123";

        UsuarioDTO u4 = new UsuarioDTO();
        u4.nombre = "OPERADOR";
        u4.apellido = "OPERADOR";
        u4.edad = LocalDate.of(2000, 1, 1);
        u4.username = "operador@mail.com";
        u4.role = Role.OPERADOR;
        u4.id = 4;
        u4.password = "123123";

        usuarios.add(new Usuario(u1));
        usuarios.add(new Usuario(u2));
        usuarios.add(new Usuario(u3));
        usuarios.add(new Usuario(u4));

        UsuarioService service = new UsuarioService();
        service.saveAll(usuarios);

    }


}
