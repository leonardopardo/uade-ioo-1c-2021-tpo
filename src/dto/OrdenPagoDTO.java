package dto;

import modelos.enums.EstadoPago;

import java.time.LocalDate;

public class OrdenPagoDTO {

    public Integer numero;
    public Double importeTotal;
    public EstadoPago estado;
    public LocalDate fecha;
    public String cuitProveedor;
    public String nombreProveedor;

}
