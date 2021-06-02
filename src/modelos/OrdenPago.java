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

}
