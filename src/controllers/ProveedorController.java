package controllers;

import dto.CertificadoDTO;
import dto.ProveedorDTO;
import dto.ProveedorUIDTO;
import modelos.Proveedor;
import modelos.enums.Rubro;
import servicios.ProveedoreService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProveedorController {

    private List<Proveedor> proveedores;
    private static ProveedorController instance;
    private ProveedoreService service;
    protected static final String PROVEEDOR_EXISTENTE_EXCEPTION = "El proveedor que intenta agregar ya existe.";

    private ProveedorController() throws Exception {
        this.service = new ProveedoreService();
        this.proveedores = this.service.getAll();
    }

    public static ProveedorController getInstance() throws Exception {
        if (instance == null) {
            instance = new ProveedorController();
        }
        return instance;
    }

    public void agregar(ProveedorDTO proveedor) throws Exception {
        try {
            if (this.proveedores.contains(proveedor)) {
                throw new Exception(PROVEEDOR_EXISTENTE_EXCEPTION);
            }

            Proveedor nuevoProveedor = new Proveedor(proveedor);

            for (Rubro r : proveedor.rubros) {
                nuevoProveedor.agregarRubro(r);
            }

            this.service.save(nuevoProveedor);
            Collections.addAll(this.proveedores, nuevoProveedor);

        } catch (Exception ex) {
            throw ex;
        }
    }

    // TODO: CAMBIAR EL RETORNO DEL MÉTODO A DTO
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

    public ProveedorDTO obtenerPorRazonSocial(String razonSocial){
        for (Proveedor proveedor : this.proveedores) {
            if (proveedor.getRazonSocial().equals(razonSocial)) {
                return proveedor.toDTO();
            }
        }
        return null;
    }

    /**
     * @return ArrayList<ProveedorDTO>
     * @tarea Lista todos los proveedores del dominio como DTOS.
     */
    public List<ProveedorUIDTO> listar() {

        List<ProveedorUIDTO> lista = new ArrayList<>();

        for (Proveedor p : this.proveedores) {
            ProveedorUIDTO x = new ProveedorUIDTO();
            x.razonSocial = p.getRazonSocial();
            x.cuit = p.getCuit();

            lista.add(x);
        }

        return lista;
    }

    /**
     * @param cuit
     * @return Lista los certificados como DTO de un provedor en particular.
     */
    public List<CertificadoDTO> listarCertificadosPorProveedor(String cuit) {
        Proveedor p = this.obtener(cuit);

        if (p != null)
            return p.getCertificados();

        return new ArrayList<>();
    }

}