package modelos;

import dto.OrdenPagoDTO;
import modelos.enums.EstadoPago;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenPago {

    private Integer numero;
    private List<Factura> facturas;
    private Double importeTotal;
    private Double retenciones;
    private Pago pago;
    private EstadoPago estado;
    private Proveedor proveedor;
    private LocalDate fecha;
    private Double rIVA;
    private Double rIIBB;
    private Double rGAN;

    public OrdenPago(OrdenPagoDTO orden) {
        this.facturas = new ArrayList<>();
    }

    private void agregarPago() {
        Pago p = new Pago();
    }

    public Integer getNumero() {
        return numero;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public Double getImporteTotal() {
        return importeTotal;
    }

    public Double getRetenciones() {
        return retenciones;
    }

    public Pago getPago() {
        return pago;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getCuitProveedor() {
        return this.proveedor.getCuit();
    }

    public OrdenPagoDTO toDTO() {

        OrdenPagoDTO dto = new OrdenPagoDTO();
        dto.numero = this.numero;
        dto.cuitProveedor = this.getProveedor().getCuit();
        dto.importeTotal = this.importeTotal;
        dto.estado = this.estado;
        dto.fecha = this.fecha;

        return dto;
    }
}
