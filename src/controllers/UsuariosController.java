package controllers;

import dto.UsuarioDTO;
import modelos.Usuario;
import servicios.UsuarioService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UsuariosController {

    private List<Usuario> usuarios;
    private static UsuariosController instance;
    private UsuarioService service;
    protected static final String USUARIO_EXISTENTE_EXCEPTION = "El usuario que intenta agregar ya existe.";

    private UsuariosController() throws Exception {
        this.service = new UsuarioService();
        this.usuarios = this.service.getAll();
    }

    public static UsuariosController getInstance() throws Exception {
        if (instance == null) {
            instance = new UsuariosController();
        }

        return instance;
    }

    /**
     * @return List<UsuarioDTO>
     * @tarea Lista todos los usuarios del sistema.
     */
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

    /**
     * @param valor
     * @return Dado un usuario, busca si existe tal usuario en el sistema, si existe, devuelve un DTO usuario, si no null.
     */
    public UsuarioDTO obtener(String valor) {

        UsuarioDTO u = new UsuarioDTO();

        for (Usuario usuario : this.usuarios) {
            if (usuario.getUsername().contentEquals(valor)) {
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

    /**
     * @param modelo
     * @throws Exception
     * @tarea Dado un DTO usuario, crea y agrega un nuevo usuario en el sistema.
     */
    public void agregar(UsuarioDTO modelo) throws Exception {
        try {

            if (this.usuarios.contains(modelo)) {
                throw new Exception(USUARIO_EXISTENTE_EXCEPTION);
            }

            Usuario nuevoUsuario = new Usuario(modelo);
            this.service.save(nuevoUsuario);
            Collections.addAll(this.usuarios, nuevoUsuario);

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @param dto
     * @throws Exception
     * @tarea Dado un DTO usuario, este método actualiza los datos del usuario con los recibidos.
     */
    public void actualizar(UsuarioDTO dto) throws Exception {
        try {

            for (Usuario u : this.usuarios) {
                if (u.getUsername().equals(dto.username)) {
                    u.setNombre(dto.nombre);
                    u.setApellido(dto.apellido);
                    u.setEdad(dto.edad);
                    u.setPassword(dto.password);
                    u.setRole(dto.role);

                    this.service.update(u);
                }
            }

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @param username
     * @throws Exception
     * @tarea Dado un usuario, este método elimina el mismo del sistema.
     */
    public void eliminar(String username) throws Exception {
        try {

            for (Usuario u : this.usuarios) {
                if (u.getUsername().equals(username)) {
                    this.service.delete(u.getId());
                    this.usuarios.remove(u);
                    break;
                }
            }

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @param username
     * @param password
     * @return boolean
     * @tarea Dado un usuario y contraseña, el método valida la correctitud.
     */
    public boolean validarCredenciales(String username, char[] password) {

        for (Usuario usuario : this.usuarios) {

            char[] p = usuario.getPassword().toCharArray();

            if (Arrays.equals(usuario.getUsername().toCharArray(), username.toCharArray()) &&
                    Arrays.equals(p, password)) {
                return true;
            }
        }

        return false;
    }

    public void destroy() {
        instance = null;
    }
}