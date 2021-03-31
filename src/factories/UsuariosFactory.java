package factories;

import modelos.Usuario;

import java.time.LocalDate;

public class UsuariosFactory {

    private static Usuario usuario;

    public static Usuario create(String nombre, String apellido,
                                 String username, String password, LocalDate edad) {

        usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setEdad(edad);

        return usuario;
    }
}
