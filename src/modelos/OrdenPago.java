package modelos;

import modelos.enums.EstadoPago;

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

    public OrdenPago() {
        this.facturas = new ArrayList<>();
        this.pagos = new ArrayList<>();
    }

    private void agregarPago(){
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
}
