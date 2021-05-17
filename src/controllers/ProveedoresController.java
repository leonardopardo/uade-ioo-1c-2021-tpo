package controllers;

import modelos.Proveedor;

import java.util.List;

public class ProveedoresController implements IABM<Proveedor> {

    private static ProveedoresController instance;

    @Override
    public void agregar(Proveedor ... modelo) throws Exception {

    }

    @Override
    public void eliminar(Proveedor modelo) {

    }

    @Override
    public void actualizar(Proveedor modelo) throws Exception {

    }

    @Override
    public Proveedor obtener(String valor) {
        return null;
    }

    @Override
    public List<Proveedor> listar() {
        return null;
    }

    public void destroy() {
        instance = null;
    }

    public static ProveedoresController getInstance(){
        if(instance == null){
            instance = new ProveedoresController();
        }

        return instance;
    }
}
