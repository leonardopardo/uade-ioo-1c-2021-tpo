package dto;

import modelos.enums.EstadoPago;

import java.time.LocalDate;
import java.util.List;

public class OrdenPagoDTO {

    public Integer numero;
    public Double importeTotal;
    public Double retencionesTotal;
    public Double retencionesIVA;
    public Double retencionesIIBB;
    public Double retencionesGAN;
    public PagoDTO pago;
    public EstadoPago estado;
    public LocalDate fecha;
    public String cuitProveedor;
    public String nombreProveedor;
    public List<FacturaDTO> facturas;

}
