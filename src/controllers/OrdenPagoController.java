package controllers;

import dto.*;
import modelos.OrdenPago;
import modelos.enums.EstadoPago;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenPagoController {

    private List<OrdenPago> ordenesPago;
    private static OrdenPagoController instance;

    public static OrdenPagoController getInstance() throws Exception {
        if (instance == null) {
            instance = new OrdenPagoController();
        }
        return instance;
    }

    public CuentaCorrienteDTO ctaCte(String cuit) {

        CuentaCorrienteDTO ctaCte = new CuentaCorrienteDTO();

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
        dto.razonSocial = ProveedorController.getInstance().obtenerCompulsa(cuit).razonSocial;

        return dto;
    }
}