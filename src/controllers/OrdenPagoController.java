package controllers;

import dto.FacturaDTO;
import dto.ProveedorDTO;
import modelos.OrdenPago;
import modelos.enums.EstadoPago;

import java.util.ArrayList;
import java.util.List;

public class OrdenPagoController {

    class CuentaCorriente {
        List<FacturaDTO> facturasPagas;
        List<FacturaDTO> facturasImpagas;
        ProveedorDTO proveedorDTO;

        public CuentaCorriente(){
            this.facturasPagas = new ArrayList<>();
            this.facturasImpagas = new ArrayList<>();
        }
    }

    private List<OrdenPago> ordenesPago;

    public CuentaCorriente ctaCte(String cuit){

        CuentaCorriente ctaCte = new CuentaCorriente();

        this.ordenesPago.forEach(op -> {

            if(op.getProveedor().getCuit().equals(cuit)){

                ctaCte.proveedorDTO = op.getProveedor().toDTO();

                // las pagadas
                if(op.getEstado().equals(EstadoPago.CANCELADO)){
                    op.getFacturas().forEach(factura -> {
                        ctaCte.facturasPagas.add(factura.facturaDTO());
                    });
                }

                // las no pagadas
                if(op.getEstado().equals(EstadoPago.PENDIENTE)){
                    op.getFacturas().forEach(factura -> {
                        ctaCte.facturasPagas.add(factura.facturaDTO());
                    });
                }
            }

        });

        return ctaCte;
    }
}
