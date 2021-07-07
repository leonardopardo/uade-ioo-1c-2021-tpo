package controllers;

import dto.*;
import modelos.*;
import modelos.enums.Rubro;
import servicios.ItemsService;
import servicios.PrecioService;
import servicios.ProveedorService;

import java.util.ArrayList;
import java.util.List;

public class ProveedorController {

    private static ProveedorController instance;

    private ProveedorService proveedorService;

    private ItemsService itemsService;

    private PrecioService precioService;

    private List<Proveedor> proveedores;

    private ProveedorController() throws Exception {
        this.proveedorService = new ProveedorService();
        this.proveedores = this.proveedorService.getAll();
    }

    public static ProveedorController getInstance() throws Exception {
        if (instance == null) {
            instance = new ProveedorController();
        }
        return instance;
    }

    //region Proveedor

    /**
     * @param proveedor
     * @throws Exception
     * @tarea Dado un proveedor dto el mismo construye el objeto proveedor del dominio y lo agrega a la lista y servicio.
     */
    public void agregar(ProveedorDTO proveedor) throws Exception {
        try {
            Proveedor nuevoProveedor = new Proveedor(proveedor);

            for (Rubro r : proveedor.rubros) {
                nuevoProveedor.agregarRubro(r);
            }

            nuevoProveedor.setId(this.proveedorService.getProximoId());
            this.proveedorService.save(nuevoProveedor);
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

    private Proveedor obtenerProveedorPorRazonSocial(String razonSocial) {
        for (Proveedor proveedor : this.proveedores) {
            if (proveedor.getRazonSocial().equals(razonSocial)) {
                return proveedor;
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

            this.proveedorService.update(nuevoProveedor);
        } catch (Exception ex) {
            throw ex;
        }
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
                    this.proveedorService.delete(p.getId());
                    this.proveedores.remove(p);
                    break;
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }
    //endregion

    //region Certificado

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
                this.proveedorService.update(prov);
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
     * @return List<CertificadoDTO>
     * @tarea Lista los certificados como DTO de un provedor en particular.
     */
    public List<CertificadoDTO> listarCertificadosPorProveedor(String cuit) {

        Proveedor p = this.obtenerProveedor(cuit);
        if (p != null)
            return p.getCertificados();

        return new ArrayList<>();
    }

    public void actualizarBalance(FacturaDTO factDTO) throws Exception {
        Proveedor prov = this.obtenerProveedor(factDTO.cuitProveedor);
        prov.setBalance(factDTO.monto, true);
        this.proveedorService.update(prov);
    }

    public void actualizarBalance(Double monto, String cuit) throws Exception {
        Proveedor prov = this.obtenerProveedor(cuit);
        prov.setBalance(monto, true);
        this.proveedorService.update(prov);
    }

    /**
     * @param provCuit
     * @param nuevoCertificado
     * @return Boolean
     * @tarea Dado un cuit y un certificado dto, este mismo corrobora si el certificado existe.
     */
    public boolean existeCertificado(String provCuit, CertificadoDTO nuevoCertificado) {
        for (Proveedor p : this.proveedores) {
            if (p.getCuit().equals(provCuit)) {
                for (CertificadoDTO c : p.getCertificados()) {
                    if (c.fechaInicio.equals(nuevoCertificado.fechaInicio) && c.tipo.equals(nuevoCertificado.tipo)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //endregion

    /**
     * @param cuit
     * @return proveedor
     * @tarea Obtine un Proveedor proporcionando el cuit, null en caso de que no lo encuentre.
     * @comment El método es privado ya que devuelve un objeto del dominio.
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