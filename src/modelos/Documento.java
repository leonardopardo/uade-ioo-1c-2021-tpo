package modelos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Documento {
    protected Integer numero;
    protected LocalDate fecha;
    protected Proveedor proveedor;
    protected List<Detalle> detalles;

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

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }
}
