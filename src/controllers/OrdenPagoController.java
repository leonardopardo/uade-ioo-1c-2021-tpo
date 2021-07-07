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
    private List<Factura> facturas;
    //private List<Proveedor> proveedores;
    private OrdenPagoService ordenPagoService;
    //private ProveedorService proveedorService;
    private FacturaService facturaService;

    private static OrdenPagoController instance;

    private OrdenPagoController() throws Exception {

        this.ordenPagoService = new OrdenPagoService();
        this.ordenesPago = this.ordenPagoService.getAll();

        this.facturaService = new FacturaService();
        this.facturas = this.facturaService.getAll();

        //this.proveedorService = new ProveedorService();
        //this.proveedores = this.proveedorService.getAll();
    }

    public static OrdenPagoController getInstance() throws Exception {
        if (instance == null) {
            instance = new OrdenPagoController();
        }
        return instance;
    }

    /**
     * @param cuit
     * @return CuentaCorrienteDTO
     * @tarea Dado un proveedor devuelve su Cuenta corriente, la cual consta de: deuda, documentos recibidos, documentos impagos y pagos realizados.
     */
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

    /**
     * @return List<OrdenPagoDTO>
     * @tarea Devuelve todas las ordenes de pago emitidas por el sistema.
     */
    public List<OrdenPagoDTO> ordenesPagoEmitidas() {

        List<OrdenPagoDTO> opEmitidas = new ArrayList<OrdenPagoDTO>();

        this.ordenesPago.forEach(op -> {
            opEmitidas.add(op.toDTO());
        });

        return opEmitidas;
    }

    /**
     * @param cuit
     * @return List<OrdenPagoDTO>
     * @tarea Dado un provedor, devuelve todas las ordenes de pago emitidas por ese proveedor.
     */
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

    /**
     * @param cuit
     * @param fecha
     * @return List<OrdenPagoDTO>
     * @tarea Dado un proveedor y una fecha, devuelve las ordenes de pago emitidas por el proveedor en la fecha.
     */
    public List<OrdenPagoDTO> ordenesPagoEmitidas(String cuit, LocalDate fecha) {

        List<OrdenPagoDTO> opEmitidas = new ArrayList<OrdenPagoDTO>();

        this.ordenesPago.forEach(op -> {

            if (op.getCuitProveedor().equals(cuit) && fecha == op.getFecha()) {
                opEmitidas.add(op.toDTO());
            }
        });

        return opEmitidas;
    }

    /**
     * @param desde
     * @param hasta
     * @return List<OrdenPagoDTO>
     * @tarea Dadas dos fechas (desde, hasta) devuelve las ordenes de pago emitidas entre estas dos fechas.
     */
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

    /**
     * @param desde
     * @param hasta
     * @param cuit
     * @return List<OrdenPagoDTO>
     * @tarea Dadas dos fechas (desde, hasta) y un proveedor, devuelve las ordenes de pago emitidas entre estas dos fechas.
     */
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

    /**
     * @param cuit
     * @param mes
     * @return ProveedorRetencionDTO
     * @throws Exception
     * @tarea Dado un proveedor y un mes, devuelve los impeustos retenidos al proveedor en el mes especificado.
     */
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

    /**
     * @param numero
     * @return
     * @throws Exception
     * @tarea Recibe un identificador de registro y lo elimina en la capa de persistencia.
     */
    public boolean eliminarOrdenPago(int numero) throws Exception {
        return true;
    }

    /**
     * @param numero
     * @return
     * @throws Exception
     * @tarea Recibido el número de Orden de Pago retororna esta, null en caso contrario.
     */
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