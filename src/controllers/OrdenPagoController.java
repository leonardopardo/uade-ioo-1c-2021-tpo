package controllers;

import dto.FacturaDTO;
import dto.ProveedorCompulsaDTO;
import dto.OrdenPagoDTO;
import dto.ProveedorRetencionDTO;
import modelos.OrdenPago;
import modelos.enums.EstadoPago;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenPagoController {

    class CuentaCorriente {
        List<FacturaDTO> facturasPagas;
        List<FacturaDTO> facturasImpagas;
        ProveedorCompulsaDTO proveedorCompulsaDTO;

        public CuentaCorriente() {
            this.facturasPagas = new ArrayList<>();
            this.facturasImpagas = new ArrayList<>();
        }
    }

    private List<OrdenPago> ordenesPago;

    public CuentaCorriente ctaCte(String cuit) {

        CuentaCorriente ctaCte = new CuentaCorriente();

        this.ordenesPago.forEach(op -> {

            if (op.getProveedor().getCuit().equals(cuit)) {

                ctaCte.proveedorCompulsaDTO = op.getProveedor().toDTO();

                // Pagadas
                if (op.getEstado().equals(EstadoPago.CANCELADO)) {
                    op.getFacturas().forEach(factura -> {
                        ctaCte.facturasPagas.add(factura.facturaDTO());
                    });
                }

                // No pagadas
                if (op.getEstado().equals(EstadoPago.PENDIENTE)) {
                    op.getFacturas().forEach(factura -> {
                        ctaCte.facturasPagas.add(factura.facturaDTO());
                    });
                }
            }

        });

        return ctaCte;
    }


    public List<OrdenPagoDTO> ordenesPagoEmitidas() {
        List<OrdenPagoDTO> opEmitidas = new ArrayList<OrdenPagoDTO>();

        this.ordenesPago.forEach(op -> {

            opEmitidas.add(op.toDTO());

        });

        return opEmitidas;
    }


    public List<OrdenPagoDTO> ordenesPagoEmitidas(String cuit) {

        List<OrdenPagoDTO> opEmitidas = new ArrayList<OrdenPagoDTO>();

        this.ordenesPago.forEach(op -> {

            if (op.getCuitProveedor().equals(cuit)) {
                opEmitidas.add(op.toDTO());
            }
        });

        return opEmitidas;
    }


    public List<OrdenPagoDTO> ordenesPagoEmitidas(String cuit, LocalDate fecha) {

        List<OrdenPagoDTO> opEmitidas = new ArrayList<OrdenPagoDTO>();

        this.ordenesPago.forEach(op -> {

            if (op.getCuitProveedor().equals(cuit) && fecha == op.getFecha()) {
                opEmitidas.add(op.toDTO());
            }
        });

        return opEmitidas;
    }


    public List<OrdenPagoDTO> ordenesPagoEmitidas(LocalDate desde, LocalDate hasta) {

        List<OrdenPagoDTO> opEmitidas = new ArrayList<OrdenPagoDTO>();

        this.ordenesPago.forEach(op -> {
            LocalDate opFecha = op.getFecha();
            if (!opFecha.isBefore(desde) && !opFecha.isAfter(hasta)) {
                opEmitidas.add(op.toDTO());
            }
        });

        return opEmitidas;
    }


    public List<OrdenPagoDTO> ordenesPagoEmitidas(LocalDate desde, LocalDate hasta, String cuit) {

        List<OrdenPagoDTO> opEmitidas = new ArrayList<OrdenPagoDTO>();

        this.ordenesPago.forEach(op -> {
            LocalDate opFecha = op.getFecha();
            if (!opFecha.isBefore(desde) && !opFecha.isAfter(hasta) && op.getCuitProveedor().equals(cuit)) {
                opEmitidas.add(op.toDTO());
            }
        });

        return opEmitidas;
    }

    public ProveedorRetencionDTO retencionPorProveedorPorMes(String cuit, int mes) throws Exception {

        Double totalRetenciones = 0.0;

        for (OrdenPago op : this.ordenesPago) {
            if (op.getFecha().getMonthValue() == mes && op.getCuitProveedor().equals(cuit)) {
                totalRetenciones += op.getRetenciones();
            }
        }

        ProveedorRetencionDTO dto = new ProveedorRetencionDTO();
        dto.monto = totalRetenciones;
        dto.mes = mes;
        dto.cuit = cuit;
        dto.razonSocial = ProveedorController.getInstance().obtener(cuit).razonSocial;

        return dto;
    }
}
