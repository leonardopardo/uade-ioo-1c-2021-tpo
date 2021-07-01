package controllers;

import dto.CertificadoDTO;
import dto.ProveedorDTO;
import modelos.CertificadoExcencion;
import modelos.OrdenCompra;
import modelos.Proveedor;
import modelos.enums.Rubro;
import servicios.ProveedorService;

import java.util.ArrayList;
import java.util.List;

public class ProveedorController {

    private List<Proveedor> proveedores;
    private List<CertificadoExcencion> certificados;
    private static ProveedorController instance;
    private ProveedorService service;

    private ProveedorController() throws Exception {
        this.service = new ProveedorService();
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
    public void actualizar(ProveedorDTO dto) throws Exception {

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

            this.service.update(nuevoProveedor);
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

    /**
     * @param cuit
     * @throws Exception
     * @tarea Dado un cuit, elimina el proveevor.
     */
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
     * @param certifDTO
     * @param cuitProveedor
     * @tarea Dado un DTO certificado y un cuit de proveedor, agrega el certificado al correspondiente proveedor.
     */
    public void agregarCertificado(CertificadoDTO certifDTO, String cuitProveedor) {
        try {
            Proveedor prov = this.obtenerProveedor(cuitProveedor);
            if (prov != null) {
                CertificadoExcencion nuevoCertif = new CertificadoExcencion(certifDTO);
                prov.agregarCertificicado(nuevoCertif);
                this.service.update(prov);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return List<CertificadoDTO>
     * @tarea Lista todos los certificados del dominio.
     */
    public List<CertificadoDTO> obtenerCertificados() {
        List<CertificadoDTO> listaCertificados = new ArrayList<>();
        for (Proveedor p : proveedores) {
            for (CertificadoDTO certif : p.getCertificados()) {
                listaCertificados.add(certif);
            }
        }
        return listaCertificados;
    }

    /**
     * @param cuitProveedor
     * @return List<CertificadoDTO>
     * @tarea Dado un cuit, devuelve la lista de certificados asociados al proveedor.
     */
    public List<CertificadoDTO> obtenerCertificadosProveedor(String cuitProveedor) {
        for (Proveedor p : proveedores) {
            if (p.getCuit().equals(cuitProveedor)) {
                return p.getCertificados();
            }
        }
        return null;
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


    /**
     * @param cuit
     * @param oc
     * @tarea Dado un cuit y una orden de compra, la orden de compra es asignada al correspondiente proveedor.
     */
    public void setProveedorEnOc(String cuit, OrdenCompra oc) {
        for (Proveedor p : this.proveedores) {
            if (p.getCuit().equals(cuit)) {
                oc.setProveedor(p);
                return;
            }
        }
    }
}