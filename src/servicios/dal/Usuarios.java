package servicios.dal;

import modelos.Usuario;
import modelos.enums.Role;
import servicios.ConnectionService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Usuarios {

    private ConnectionService ctx;

    private String tableName;

    private String idValue;

    public Usuarios() throws SQLException {
        this.ctx = new ConnectionService();
        this.tableName = "usuarios";
    }

    public Usuarios(String tableName) throws SQLException {
        this.ctx = new ConnectionService();
        this.tableName = tableName;
    }

    public List<Usuario> list() throws SQLException {

        List<Usuario> usuarioList = new ArrayList<>();

        String sql = "SELECT * FROM usuarios";

        PreparedStatement query = this.ctx.connect().prepareStatement(sql);

        ResultSet rs = query.executeQuery();

        while (rs.next()) {

            Usuario u = new Usuario();

            u.setNombre(rs.getString("nombre"));
            u.setApellido(rs.getString("apellido"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setEdad(rs.getDate("edad").toLocalDate());
            u.setRole(Role.valueOf(rs.getString("role")));

            usuarioList.add(u);
        }

        this.ctx.disconnect();

        return usuarioList;
    }

    public Usuario find(Integer value) throws SQLException {

        String sql = "SELECT * FROM ? WHERE id = ?";

        PreparedStatement query = this.ctx.connect().prepareStatement(sql);
        query.setString(1, this.tableName);
        query.setInt(2, value);

        ResultSet rs = query.executeQuery();

        Usuario u = new Usuario();

        while(rs.next()){
            u.setNombre(rs.getString("nombre"));
            u.setApellido(rs.getString("apellido"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setEdad(rs.getDate("edad").toLocalDate());
            u.setRole(Role.valueOf(rs.getString("role")));
        }

        this.ctx.disconnect();

        return u;
    }

    public void save(dto.Usuario dto) throws Exception {
        try {

        } catch (Exception e) {

        }
    }

    public void update(Integer id, dto.Usuario dto) throws Exception {
        try {

        } catch (Exception e) {

        }
    }

    public void delete(Integer id) throws Exception {
        try {

        } catch (Exception e) {

        }
    }
}
