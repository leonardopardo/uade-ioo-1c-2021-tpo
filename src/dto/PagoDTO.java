package dto;

import modelos.enums.TipoPago;

import java.time.LocalDate;

public class PagoDTO {
    public TipoPago tipo;
    public Double monto;
    public LocalDate fecha;
    public OrdenPagoDTO orden;
    public ChequeDTO cheque;
}
