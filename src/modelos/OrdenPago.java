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

    private List<Pago> pagos;

    private EstadoPago estado;

    private Proveedor proveedor;

    private LocalDate fecha;

    public OrdenPago() {
        this.facturas = new ArrayList<>();
        this.pagos = new ArrayList<>();
    }

    private void agregarPago() {
        Pago p = new Pago();
        this.pagos.add(p);
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

    public List<Pago> getPagos() {
        return pagos;
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
