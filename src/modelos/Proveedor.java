package modelos;

import modelos.enums.Rubro;
import modelos.enums.TipoIVA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Proveedor {

    private String razonSocial;

    private String nombreFantasia;

    private String cuit;

    private String email;

    private String telefono;

    private TipoIVA tipoIVA;

    private LocalDate inicioActividad;

    private String ingresosBrutos;

    private Double limiteCtaCte;

    private List<Rubro> rubros;

    public Proveedor() {
        this.rubros = new ArrayList<>();
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TipoIVA getTipoIVA() {
        return tipoIVA;
    }

    public void setTipoIVA(TipoIVA tipoIVA) {
        this.tipoIVA = tipoIVA;
    }

    public LocalDate getInicioActividad() {
        return inicioActividad;
    }

    public void setInicioActividad(LocalDate inicioActividad) {
        this.inicioActividad = inicioActividad;
    }

    public String getIngresosBrutos() {
        return ingresosBrutos;
    }

    public void setIngresosBrutos(String ingresosBrutos) {
        this.ingresosBrutos = ingresosBrutos;
    }

    public Double getLimiteCtaCte() {
        return limiteCtaCte;
    }

    public void setLimiteCtaCte(Double limiteCtaCte) {
        this.limiteCtaCte = limiteCtaCte;
    }

    public List<Rubro> getRubro() {
        return rubros;
    }

    public void setRubro(List<Rubro> rubro) {
        this.rubros = rubro;
    }
}
