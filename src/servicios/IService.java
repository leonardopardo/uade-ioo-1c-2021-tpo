package servicios;

import java.util.List;

public interface IService<T> {
    void agregar(T modelo) throws Exception;
    void eliminar(T modelo);
    void actualizar(T modelo) throws Exception;
    T obtener(String valor);
    List<T> listar();
}
