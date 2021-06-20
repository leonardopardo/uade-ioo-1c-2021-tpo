package modelos;

import dto.UsuarioDTO;
import modelos.enums.Role;

import java.time.LocalDate;

public class Usuario {

    private Integer id;
    private String nombre;
    private String apellido;
    private String username;
    private String password;
    private LocalDate edad;
    private Role role;

    public Usuario(UsuarioDTO dto) {
        this.id = dto.id;
        this.nombre = dto.nombre.trim().toLowerCase();
        this.apellido = dto.apellido.trim().toLowerCase();
        this.username = dto.username.trim().toLowerCase();
        this.password = dto.password.trim();
        this.edad = dto.edad;
        this.role = dto.role;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre.toUpperCase();
    }

    public String getApellido() {
        return apellido.toUpperCase();
    }

    public String getUsername() {
        return username.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getEdad() {
        return edad;
    }

    public Role getRole() {
        return role;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEdad(LocalDate edad) {
        this.edad = edad;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
