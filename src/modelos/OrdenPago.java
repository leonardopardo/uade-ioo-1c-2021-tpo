package modelos;

import dto.FacturaDTO;
import dto.OrdenPagoDTO;
import dto.ProveedorDTO;
import modelos.enums.EstadoPago;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenPago {

    private Integer numero;
    private List<Factura> facturas;
    private Double importeTotal;
    private Pago pago;
    private EstadoPago estado;
    private Proveedor proveedor;
    private LocalDate fecha;
    private Double retenciones;
    private Double rIVA;
    private Double rIIBB;
    private Double rGAN;

    public OrdenPago(OrdenPagoDTO orden) {
        this.facturas = new ArrayList<>();
        this.importeTotal = orden.importeTotal;
        this.retenciones = orden.retencionesTotal;
        this.rIVA = orden.retencionesIVA;
        this.rIIBB = orden.retencionesIIBB;
        this.rGAN = orden.retencionesGAN;
        this.estado = orden.estado;
        this.fecha = orden.fecha;

        if(orden.pago != null)
            this.pago = new Pago(orden.pago);
    }

    //region Setters
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void agregarFactura(FacturaDTO factura) {
        this.facturas.add(new Factura(factura));
    }

    public void setProveedor(ProveedorDTO proveedor) throws Exception {
        this.proveedor = new Proveedor(proveedor);
    }
    //endregion

    //region Getters
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
    //endregion

    public OrdenPagoDTO toDTO() {
        OrdenPagoDTO dto = new OrdenPagoDTO();
        dto.numero = this.numero;
        dto.cuitProveedor = this.proveedor.getCuit();
        dto.retencionesTotal = this.retenciones;
        dto.nombreProveedor = this.proveedor.getCuit();
        dto.importeTotal = this.importeTotal;
        dto.estado = this.estado;
        dto.fecha = this.fecha;

        if(this.pago != null)
            dto.pago = this.pago.toDTO();

        return dto;
    }
}
