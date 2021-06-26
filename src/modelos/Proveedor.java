package modelos;

import dto.CertificadoDTO;
import dto.ProveedorCompulsaDTO;
import dto.ProveedorDTO;
import modelos.enums.Rubro;
import modelos.enums.TipoIVA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Proveedor {

    private Integer id;
    private String razonSocial;
    private String cuit;
    private TipoIVA tipoIVA;
    private String nombreFantasia;
    private String email;
    private String telefono;
    private LocalDate inicioActividad;
    private String ingresosBrutos;
    private List<Rubro> rubros;
    private Double limiteCtaCte;
    private List<CertificadoExcencion> certificados;
    private Double balance;

    public Proveedor(ProveedorDTO p) {
        this.razonSocial = p.razonSocial;
        this.cuit = p.cuit;
        this.tipoIVA = p.tipoIVA;
        this.nombreFantasia = p.nombreFantasia;
        this.email = p.email;
        this.telefono = p.telefono;
        this.inicioActividad = p.inicioActividad;
        this.ingresosBrutos = p.ingresosBrutos;
        this.limiteCtaCte = p.limiteCtaCte;

        this.rubros = new ArrayList<>();
        this.certificados = new ArrayList<>();
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public void setTipoIVA(TipoIVA tipoIVA) {
        this.tipoIVA = tipoIVA;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setIngresosBrutos(String ingresosBrutos) {
        this.ingresosBrutos = ingresosBrutos;
    }

    public void setLimiteCtaCte(Double limiteCtaCte) {
        this.limiteCtaCte = limiteCtaCte;
    }

    public void setCertificados(List<CertificadoExcencion> certificados) {
        this.certificados = certificados;
    }

    public String getCuit() {
        return this.cuit;
    }

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public Double getBalance() {
        return this.balance;
    }

    public ProveedorCompulsaDTO toCompulsaDTO() {
        return new ProveedorCompulsaDTO();
    }

    public void agregarRubro(Rubro r) {
        this.rubros.add(r);
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CertificadoDTO> getCertificados() {
        List<CertificadoDTO> list = new ArrayList<>();
        for (CertificadoExcencion c : this.certificados) {
            CertificadoDTO x = new CertificadoDTO();
            x.tipo = c.getTipo();
            x.fechaInicio = c.getFechaInicio();
            x.fechaFin = c.getFechaFin();

            list.add(x);
        }

        return list;
    }

    public ProveedorDTO toDTO() {
        ProveedorDTO dto = new ProveedorDTO();
        dto.razonSocial = this.razonSocial;
        dto.nombreFantasia = this.nombreFantasia;
        dto.cuit = this.cuit;
        dto.ingresosBrutos = this.ingresosBrutos;
        dto.email = this.email;
        dto.telefono = this.telefono;
        dto.limiteCtaCte = this.limiteCtaCte;
        dto.rubros = this.rubros;
        dto.tipoIVA = this.tipoIVA;
        dto.inicioActividad = this.inicioActividad;

        return dto;
    }

    public void reemplazarRubros(List nuevoRubros) {
        this.rubros = nuevoRubros;
    }

}