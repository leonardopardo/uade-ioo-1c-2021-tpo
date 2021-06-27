package controllers;

import dto.CertificadoDTO;
import dto.ProveedorDTO;
import modelos.CertificadoExcencion;
import modelos.Proveedor;
import modelos.enums.Rubro;
import modelos.enums.TipoIVA;
import servicios.ProveedoreService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProveedorController {

    private List<Proveedor> proveedores;

    private static ProveedorController instance;

    private ProveedoreService service;

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
            Proveedor nuevoProveedor = new Proveedor(proveedor);

            for (Rubro r : proveedor.rubros) {
                nuevoProveedor.agregarRubro(r);
            }

            nuevoProveedor.setId(this.service.getProximoId());
            this.service.save(nuevoProveedor);
            this.proveedores.add(nuevoProveedor);

        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * @param cuit
     * @return ProveedorDTO
     * @tarea Dado un cuit, en caso de que exista un proveedor con dicho cuit, devuelve un objeto ProveedorDTO, si no null.
     */
    public ProveedorDTO obtener(String cuit) {
        for (Proveedor proveedor : this.proveedores) {
            if (proveedor.getCuit().equals(cuit)) {
                return proveedor.toDTO();
            }
        }
        return null;
    }

    /**
     * @param dto
     * @tarea Dado un ProveedorDTO y un proveedor existente, se actualizan las propiedades del mismo.
     */
    public void actualizar(ProveedorDTO dto) {

        try {
            Proveedor nuevoProveedor = obtenerProveedor(dto.cuit);

            nuevoProveedor.reemplazarRubros(dto.rubros);
            nuevoProveedor.setCuit(dto.cuit);
            nuevoProveedor.setEmail(dto.email);
            nuevoProveedor.setTipoIVA(dto.tipoIVA);
            nuevoProveedor.setNombreFantasia(dto.nombreFantasia);
            nuevoProveedor.setIngresosBrutos(dto.ingresosBrutos);
            nuevoProveedor.setLimiteCtaCte(dto.limiteCtaCte);
            nuevoProveedor.setTelefono(dto.telefono);
            nuevoProveedor.setRazonSocial(dto.razonSocial);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * @param razonSocial
     * @return ProveedorDTO
     * @tarea Dado una razonSocial, en caso de que exista un proveedor con dicha razonSocial, devuelve un objeto ProveedorDTO, si no null.
     */
    public ProveedorDTO obtenerPorRazonSocial(String razonSocial) {
        for (Proveedor proveedor : this.proveedores) {
            if (proveedor.getRazonSocial().equals(razonSocial)) {
                return proveedor.toDTO();
            }
        }
        return null;
    }

    /**
     * @return ArrayList<ProveedorDTO>
     * @tarea Lista todos los proveedores del dominio como objetos DTO.
     */
    public List<ProveedorDTO> listar() {

        List<ProveedorDTO> lista = new ArrayList<>();

        for (Proveedor p : this.proveedores) {
            ProveedorDTO x = new ProveedorDTO();
            x.razonSocial = p.getRazonSocial();
            x.cuit = p.getCuit();

            lista.add(x);
        }

        return lista;
    }

    /**
     * @param cuit
     * @return List<CertificadoDTO>
     * @tarea Lista los certificados como DTO de un provedor en particular.
     */
    public List<CertificadoDTO> listarCertificadosPorProveedor(String cuit) {

        Proveedor p = this.obtenerProveedor(cuit);

        if (p != null)
            return p.getCertificados();

        return new ArrayList<>();
    }

    public void eliminar(String cuit) throws Exception {
        try {
            if (this.proveedores.size() == 0) {
                throw new Exception("No hay proveedores registrados");
            }
            for (Proveedor p : this.proveedores) {
                if (p.getCuit().equals(cuit)) {
                    this.service.delete(p.getId());
                    this.proveedores.remove(p);
                    break;
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @param cuit
     * @return proveedor
     * @tarea metodo privado para poder operar con objetos del dominio.
     * @comment El metodo es privado ya que devuelve un objeto del dominio.
     */
    private Proveedor obtenerProveedor(String cuit) {
        for (Proveedor proveedor : this.proveedores) {
            if (proveedor.getCuit().equals(cuit)) {
                return proveedor;
            }
        }
        return null;
    }

}