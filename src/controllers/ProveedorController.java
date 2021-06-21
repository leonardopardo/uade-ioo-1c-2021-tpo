package controllers;

import dto.ProveedorDTO;
import modelos.Proveedor;
import servicios.ProveedoreService;

import java.util.ArrayList;
import java.util.List;

public class ProveedorController {

    private List<Proveedor> proveedores;

    private static ProveedorController instance;

    private ProveedoreService service;

    private ProveedorController() throws Exception{
        this.service = new ProveedoreService();
        this.proveedores = this.service.getAll();
    }

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

    public List<ProveedorDTO> listar(){

        List<ProveedorDTO> lista = new ArrayList<>();

        for (Proveedor p: this.proveedores) {
            ProveedorDTO x = new ProveedorDTO();
            x.razonSocial = p.getRazonSocial();
            x.cuit = p.getCuit();

            lista.add(x);
        }

        return lista;
    }

}