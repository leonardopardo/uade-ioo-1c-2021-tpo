package modelos;

import modelos.enums.Role;

import java.time.LocalDate;
import java.time.Period;

public class Usuario {

    private String nombre;

    private String apellido;

    private String username;

    private String password;

    private LocalDate edad;

    private Role role;

    public Integer getEdad() {
        return Period.between(edad, LocalDate.now()).getYears();
    }

    public void setEdad(LocalDate edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre.toUpperCase();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toLowerCase();
    }

    public String getApellido() {
        return apellido.toUpperCase();
    }

    public void setApellido(String apellido) {
        this.apellido = apellido.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
