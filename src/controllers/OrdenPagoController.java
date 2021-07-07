package controllers;

import dto.*;
import modelos.Factura;
import modelos.OrdenPago;
import modelos.Proveedor;
import modelos.enums.EstadoPago;
import servicios.FacturaService;
import servicios.OrdenPagoService;
import servicios.ProveedorService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenPagoController {

    private List<OrdenPago> ordenesPago;
    private OrdenPagoService ordenPagoService;

    private static OrdenPagoController instance;

    private OrdenPagoController() throws Exception {
        this.ordenPagoService = new OrdenPagoService();
        this.ordenesPago = this.ordenPagoService.getAll();
    }

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
                ctaCte.proveedorCompulsaDTO = op.getProveedor().toCompulsaDTO();

                // Facturas pagadas.
                if (op.getEstado().equals(EstadoPago.CANCELADO)) {
                    op.getFacturas().forEach(factura -> {
                        ctaCte.facturasPagas.add(factura.facturaDTO());
                    });
                }

                // Facturas no pagadas.
                if (op.getEstado().equals(EstadoPago.PENDIENTE)) {
                    op.getFacturas().forEach(factura -> {
                        ctaCte.facturasPagas.add(factura.facturaDTO());
                    });
                }
            }
        });

        return ctaCte;
    }

    public void agregar(OrdenPagoDTO orden) throws Exception {
        try {

            OrdenPago nuevaOrden = new OrdenPago(orden);
            nuevaOrden.setNumero(this.ordenPagoService.getProximoNumero());
            nuevaOrden.setProveedor(ProveedorController.getInstance().obtener(orden.cuitProveedor));

            for (FacturaDTO f:orden.facturas) {
                nuevaOrden.agregarFactura(f);
                DocumentoController.getInstance().cambiarEstadoFactura(f.numero, EstadoPago.CANCELADO);
            }

            ProveedorController.getInstance().actualizarBalance(orden.importeTotal + orden.retencionesTotal, orden.cuitProveedor);

            this.ordenPagoService.save(nuevaOrden);
            this.ordenesPago.add(nuevaOrden);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean eliminarOrdenPago(int numero) throws Exception {
        return true;
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

    public List<OrdenPagoDTO> ordenesPagoEmitidas(LocalDate fecha){
        List<OrdenPagoDTO> opEmitidas = new ArrayList<OrdenPagoDTO>();
        this.ordenesPago.forEach(op -> {
            if (op.getFecha().equals(fecha)) {
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

    private OrdenPago obtenerOrden(int numero) throws Exception {
        OrdenPago orden = null;

        for (OrdenPago op : this.ordenesPago) {
            if (op.getNumero().equals(numero)) {
                orden = op;
                break;
            }
        }

        return orden;
    }
}