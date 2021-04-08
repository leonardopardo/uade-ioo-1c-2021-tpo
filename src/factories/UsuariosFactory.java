package factories;

import modelos.Usuario;
import modelos.enums.Role;

import java.time.LocalDate;

public class UsuariosFactory {

    public static Usuario create(String nombre,
                                 String apellido,
                                 String username,
                                 String password,
                                 LocalDate edad,
                                 Role role) {

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setEdad(edad);
        usuario.setRole(role);

        return usuario;
    }
}
