package modelos;

import dto.PagoDTO;
import modelos.enums.TipoPago;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pago {

    private TipoPago tipo;
    private Double monto;
    private LocalDate fecha;
    private OrdenPago orden;
    private Cheque cheque;

    public Pago(PagoDTO pago) {
        this.tipo = pago.tipo;
        this.monto = pago.monto;
        this.orden = new OrdenPago(pago.orden);
        this.cheque = new Cheque(pago.cheque);
    }

    //region Getters
    public TipoPago getTipo() {
        return tipo;
    }

    public Double getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public OrdenPago getOrden() {
        return orden;
    }

    public Cheque getCheque() {
        return cheque;
    }
    //endregion

    //region Setters
    public void setTipo(TipoPago tipo) {
        this.tipo = tipo;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setOrden(OrdenPago orden) {
        this.orden = orden;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }
    //endregion

    public PagoDTO toDTO(){
        PagoDTO p = new PagoDTO();

        if(this.cheque != null)
            p.cheque = this.cheque.toDTO();

        p.monto = this.monto;
        p.fecha = this.fecha;
        p.tipo = this.tipo;
        p.orden = this.orden.toDTO();

        return p;
    }
}
