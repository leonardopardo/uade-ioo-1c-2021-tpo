package controllers;

import dto.ProveedorCompulsaDTO;
import modelos.Proveedor;


import java.util.List;

public class ProveedorController {
    private List<Proveedor> proveedores;

    private static ProveedorController instance;


    public ProveedorCompulsaDTO obtener(String cuit) {

        ProveedorCompulsaDTO dto = new ProveedorCompulsaDTO();

        for (Proveedor proveedor : this.proveedores) {
            if (proveedor.getCuit().equals(cuit)) {
                dto.razonSocial = proveedor.getRazonSocial();
                dto.cuit = proveedor.getCuit();
                return dto;
            }
        }

        return null;
    }

    public static ProveedorController getInstance() throws Exception {
        if (instance == null) {
            instance = new ProveedorController();
        }
        return instance;
    }

}
