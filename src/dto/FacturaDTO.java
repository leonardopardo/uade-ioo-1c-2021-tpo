package dto;

import modelos.enums.EstadoPago;

import java.time.LocalDate;
import java.util.List;

public class FacturaDTO {

    public LocalDate fecha;
    public Double monto;
    public Double iva21;
    public Double iva10;
    public Integer numero;
    public String cuitProveedor;
    public List<DetalleDTO> detalles;
    public String razonSocial;
    public EstadoPago estadoPago;

}