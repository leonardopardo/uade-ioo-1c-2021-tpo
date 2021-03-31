package servicios;

import modelos.Usuario;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsuariosService implements IService<Usuario> {

    private List<Usuario> usuarios;

    private static UsuariosService instance;

    protected static final String USUARIO_EXISTENTE_EXCEPTION
            = "El usuario que intenta agregar ya existe.";

    protected static final String USUARIO_NO_EXISTENTE_EXCEPTION
            = "El usuario con el que intenta operar no existe.";

    protected UsuariosService() {
        this.usuarios = new ArrayList<Usuario>();
    }

    @Override
    public void agregar(Usuario modelo) throws Exception {
        try {

            if(this.usuarios.contains(modelo))
                throw new Exception(USUARIO_EXISTENTE_EXCEPTION);

            this.usuarios.add(modelo);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void eliminar(Usuario modelo){
        try{
            this.usuarios.remove(modelo);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void actualizar(Usuario modelo) throws Exception {
        try{

            Usuario usuario = this.obtener(modelo.getUsername());

            if(usuario == null)
                throw new Exception(USUARIO_NO_EXISTENTE_EXCEPTION);

        } catch(Exception e) {
            throw e;
        }
    }

    @Override
    public Usuario obtener(String valor) {
        Usuario u = null;

        for (Usuario usuario : this.usuarios) {
            if(usuario.getUsername() == valor){
                u = usuario;
            }
        }

        return u;
    }

    @Override
    public List<Usuario> listar() {
        return this.usuarios;
    }

    public boolean validarCredenciales(String username, char[] password) {

        for (Usuario usuario : this.usuarios) {

            char[] p = usuario.getPassword().toCharArray();

            if (Arrays.equals(usuario.getUsername().toCharArray(), username.toCharArray())
                    && Arrays.equals(p,password)) {
                return true;
            }
        }

        return false;
    }

    public static UsuariosService getInstance() {
        if(instance == null){
            instance = new UsuariosService();
        }

        return instance;
    }
}
