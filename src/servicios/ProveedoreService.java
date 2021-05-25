package servicios;

import dto.ProveedorDTO;
import modelos.Proveedor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProveedoreService implements IService<Proveedor, ProveedorDTO> {

    private ConnectionService conn;

    @Override
    public List<Proveedor> list() throws SQLException {
        return null;
    }

    @Override
    public Proveedor find(Integer value) throws SQLException {
        return null;
    }

    @Override
    public void save(ProveedorDTO dto) throws SQLException {

    }

    @Override
    public void update(Proveedor obj, ProveedorDTO dto) throws SQLException {

    }

    @Override
    public void delete(Integer value) throws SQLException {

    }

    @Override
    public Proveedor map(ResultSet rs) throws SQLException {
        return null;
    }
}
