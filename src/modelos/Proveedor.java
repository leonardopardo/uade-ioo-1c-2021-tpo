package modelos;

import dto.CertificadoDTO;
import dto.OrdenCompraDTO;
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
    private Double limiteCtaCte;
    private Double balance;
    private List<CertificadoExcencion> certificados;
    private List<Rubro> rubros;
    private List<OrdenCompra> ordenesCompra;
    private List<Factura> facturas;

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
        this.ordenesCompra = new ArrayList<>();
        this.facturas = new ArrayList<>();
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCertificados(List<CertificadoExcencion> certificados) {
        this.certificados = certificados;
    }

    public void setOrdenCompra(OrdenCompraDTO oc) {
        OrdenCompra nuevaOC = new OrdenCompra(oc);
        this.ordenesCompra.add(nuevaOC);
    }

    public String getCuit() {
        return this.cuit;
    }

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public Integer getId() {
        return this.id;
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

    public Double getBalance() {
        return this.balance;
    }

    public void agregarRubro(Rubro r) {
        this.rubros.add(r);
    }

    public void agregarCertificicado(CertificadoExcencion nuevoCertif) {
        this.certificados.add(nuevoCertif);
    }

    public void reemplazarRubros(List nuevoRubros) {
        this.rubros = nuevoRubros;
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

    public ProveedorCompulsaDTO toCompulsaDTO() {
        return new ProveedorCompulsaDTO();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Proveedor other = (Proveedor) obj;

        if (!this.cuit.equals(other.getCuit())) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "id=" + id +
                ", razonSocial='" + razonSocial + '\'' +
                ", cuit='" + cuit + '\'' +
                ", tipoIVA=" + tipoIVA +
                ", nombreFantasia='" + nombreFantasia + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", inicioActividad=" + inicioActividad +
                ", ingresosBrutos='" + ingresosBrutos + '\'' +
                ", rubros=" + rubros +
                ", limiteCtaCte=" + limiteCtaCte +
                ", certificados=" + certificados +
                ", balance=" + balance +
                '}';
    }

    public List<OrdenCompra> getOrdenesCompra() {
        return this.ordenesCompra;
    }
}