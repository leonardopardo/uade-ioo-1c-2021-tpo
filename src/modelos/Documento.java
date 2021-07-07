package modelos;

import dto.ProveedorDTO;
import modelos.enums.EstadoPago;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Documento {
    protected Integer numero;
    protected LocalDate fecha;
    protected Proveedor proveedor;
    protected List<Detalle> detalles;
    protected EstadoPago estadoPago;

    //region Getters
    public Integer getNumero() {
        return numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public List<Detalle> getDetalles() {
        return detalles;
    }

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }
    //endregion

    //region Setters
    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setProveedor(ProveedorDTO proveedor) throws Exception {
        this.proveedor = new Proveedor(proveedor);
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }
    //endregion
}
