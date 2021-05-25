package controllers;

import java.util.List;

public interface IController<T> {

    List<T> listar();

    T obtener(String valor);

    void agregar(T ... modelo) throws Exception;

    void actualizar(T modelo) throws Exception;

    void eliminar(T modelo);

}
