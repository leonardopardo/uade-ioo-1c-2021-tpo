package controllers;

import dto.UsuarioDTO;
import modelos.Usuario;
import servicios.UsuarioService;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UsuariosController{

    private List<Usuario> usuarios;

    private static UsuariosController instance;

    protected static final String USUARIO_EXISTENTE_EXCEPTION
            = "El usuario que intenta agregar ya existe.";

    protected static final String USUARIO_NO_EXISTENTE_EXCEPTION
            = "El usuario con el que intenta operar no existe.";

    protected UsuariosController() throws Exception {
        UsuarioService service = new UsuarioService();
        this.usuarios = service.getAll();
    }

    public static UsuariosController getInstance() throws Exception {
        if(instance == null){
            instance = new UsuariosController();
        }

        return instance;
    }

    public List<UsuarioDTO> listar() {

        List<UsuarioDTO> usuarios = new ArrayList<>();

        this.usuarios.stream().forEach(usuario -> {
            UsuarioDTO u = new UsuarioDTO();
            u.nombre = usuario.getNombre();
            u.apellido = usuario.getApellido();
            u.username = usuario.getUsername();
            u.edad = usuario.getEdad();
            u.role = usuario.getRole();
            u.id = usuario.getId();

            usuarios.add(u);
        });

        return usuarios;
    }

    public UsuarioDTO obtener(String valor) {

        UsuarioDTO u = new UsuarioDTO();

        for (Usuario usuario : this.usuarios) {
            if(usuario.getUsername().contentEquals(valor)){
                u.nombre = usuario.getNombre();
                u.apellido = usuario.getApellido();
                u.role = usuario.getRole();
                u.username = usuario.getUsername();
                u.edad = usuario.getEdad();

                return u;
            }
        }

        return null;
    }

    public void agregar(Usuario ... modelo) throws Exception {
        try {

            if(this.usuarios.contains(modelo))
                throw new Exception(USUARIO_EXISTENTE_EXCEPTION);

            Collections.addAll(this.usuarios, modelo);

        } catch (Exception e) {
            throw e;
        }
    }

    public void actualizar(UsuarioDTO dto) throws Exception {
        try{

            UsuarioDTO usuario = this.obtener(dto.username);

            if(usuario == null)
                throw new Exception(USUARIO_NO_EXISTENTE_EXCEPTION);

        } catch(Exception e) {
            throw e;
        }
    }

    public void eliminar(Usuario modelo){
        try{
            this.usuarios.remove(modelo);
        } catch (Exception e) {
            throw e;
        }
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
}
