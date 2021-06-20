package dto;

import modelos.enums.Role;

import java.time.LocalDate;

public class UsuarioDTO {

    public Integer id;
    public String nombre;
    public String apellido;
    public String username;
    public String password;
    public LocalDate edad;
    public Role role;

}
