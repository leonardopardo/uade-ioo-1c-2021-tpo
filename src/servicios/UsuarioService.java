package servicios;

import dto.UsuarioDTO;
import modelos.Usuario;
import modelos.enums.Role;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService implements IService<Usuario, UsuarioDTO> {

    private ConnectionService ctx;

    public UsuarioService() throws SQLException {
        this.ctx = new ConnectionService();
    }

    @Override
    public List<Usuario> list() throws SQLException {

        Connection conn = this.ctx.connect();

        Statement stmt = conn.createStatement();

        ResultSet rs  = stmt.executeQuery("SELECT * FROM usuarios");

        List<Usuario> usuarios = new ArrayList<>();

        while(rs.next()){
            usuarios.add(this.map(rs));
        }

        conn.close();

        return usuarios;
    }

    @Override
    public Usuario find(Integer value) throws SQLException {

        Connection conn = this.ctx.connect();

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE id = ?");
        stmt.setInt(1, value);

        ResultSet rs = stmt.executeQuery();

        Usuario u = this.map(rs);

        conn.close();

        return u;

    }

    @Override
    public void save(UsuarioDTO dto) throws SQLException {

    }

    @Override
    public void update(Usuario obj, UsuarioDTO dto) throws SQLException {

    }

    @Override
    public void delete(Integer value) throws SQLException {

    }

    private Usuario map(ResultSet rs) throws SQLException {

        UsuarioDTO dto = new UsuarioDTO();
        dto.id = rs.getInt("id");
        dto.nombre = rs.getString("nombre");
        dto.apellido = rs.getString("apellido");
        dto.username = rs.getString("username");
        dto.password = rs.getString("password");
        dto.edad = LocalDate.parse(rs.getString("edad"));
        dto.role = Role.valueOf(rs.getString("role"));

        return new Usuario(dto);

    }
}
