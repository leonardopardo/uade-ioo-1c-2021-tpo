package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IService<T, T2> {

    List<T> list() throws SQLException;

    T find(Integer value) throws SQLException;

    void save(T2 dto) throws SQLException;

    void update(T obj, T2 dto) throws SQLException;

    void delete(Integer value) throws SQLException;

    T map(ResultSet rs) throws SQLException;

}
