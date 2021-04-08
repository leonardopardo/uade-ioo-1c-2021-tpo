package servicios;

import modelos.Proveedor;

import java.util.List;

public class ProveedoresService implements IABMService<Proveedor> {

    private static ProveedoresService instance;

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

    public static ProveedoresService getInstance(){
        if(instance == null){
            instance = new ProveedoresService();
        }

        return instance;
    }
}
