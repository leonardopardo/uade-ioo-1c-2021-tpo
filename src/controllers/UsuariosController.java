package controllers;

import modelos.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UsuariosController implements IABM<Usuario> {

    private List<Usuario> usuarios;

    private static UsuariosController instance;

    protected static final String USUARIO_EXISTENTE_EXCEPTION
            = "El usuario que intenta agregar ya existe.";

    protected static final String USUARIO_NO_EXISTENTE_EXCEPTION
            = "El usuario con el que intenta operar no existe.";

    protected UsuariosController() {
        this.usuarios = new ArrayList<Usuario>();
    }

    @Override
    public void agregar(Usuario ... modelo) throws Exception {
        try {

            if(this.usuarios.contains(modelo))
                throw new Exception(USUARIO_EXISTENTE_EXCEPTION);

            Collections.addAll(this.usuarios, modelo);

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
            if(usuario.getUsername().contentEquals(valor))
                u = usuario;
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

    public void destroy() {
        instance = null;
    }

    public static UsuariosController getInstance() {
        if(instance == null){
            instance = new UsuariosController();
        }

        return instance;
    }
}
