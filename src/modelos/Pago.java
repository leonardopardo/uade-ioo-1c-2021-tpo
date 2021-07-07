package modelos;

import modelos.enums.TipoPago;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pago {

    private Integer id;
    private TipoPago tipo;
    private Double monto;
    private LocalDateTime fecha;
    private OrdenPago orden;
    private Cheque cheque;

    public Pago() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoPago getTipo() {
        return tipo;
    }

    public void setTipo(TipoPago tipo) {
        this.tipo = tipo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public OrdenPago getOrden() {
        return orden;
    }

    public void setOrden(OrdenPago orden) {
        this.orden = orden;
    }

    public Cheque getCheque() {
        return cheque;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }
}
