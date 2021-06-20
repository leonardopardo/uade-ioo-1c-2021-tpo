package controllers;

import modelos.Proveedor;

import java.util.List;

public class ProveedorController {

    private List<Proveedor> proveedores;
    private static ProveedorController instance;

    public static ProveedorController getInstance() throws Exception {
        if (instance == null) {
            instance = new ProveedorController();
        }
        return instance;
    }

    /**
     * @param cuit
     * @return Proveedor
     * @tarea Dado un cuit, en caso de que exista un proveedor con dicho cuit, devuelve un objeto Proveedor, si no null.
     */
    public Proveedor obtener(String cuit) {
        for (Proveedor proveedor : this.proveedores) {
            if (proveedor.getCuit().equals(cuit)) {
                return proveedor;
            }
        }
        return null;

    }

}