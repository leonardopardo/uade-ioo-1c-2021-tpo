package controllers;

import dto.DetalleDTO;
import dto.FacturaDTO;
import dto.OrdenCompraDTO;
import dto.ProveedorDTO;
import modelos.*;
import modelos.enums.EstadoPago;
import servicios.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DocumentoController {

    private List<OrdenCompra> ordenes;
    private List<Factura> facturas;
    private List<Item> items;
    private List<Precio> precios;

    private OrdenCompraService ordenService;
    private FacturaService facturaService;
    private ItemsService itemsService;
    private PrecioController precioController;

    private static DocumentoController instance;

    public DocumentoController() throws Exception {

        this.precioController = PrecioController.getInstance();

        this.ordenService = new OrdenCompraService();
        this.ordenes = this.ordenService.getAll();

        this.facturaService = new FacturaService();
        this.facturas = this.facturaService.getAll();

        this.itemsService = new ItemsService();
        this.items = this.itemsService.getAll();
    }

    public static DocumentoController getInstance() throws Exception {
        if (instance == null)
            instance = new DocumentoController();

        return instance;
    }

    //region Ordenes
    public OrdenCompraDTO orden(Integer numero) {
        OrdenCompraDTO orden = null;
        for (OrdenCompra o : this.ordenes) {
            if (o.getNumero().equals(numero)) {
                orden = o.toDTO();
                break;
            }
        }
        return orden;
    }

    public List<OrdenCompraDTO> listarOrdenes() {
        List<OrdenCompraDTO> ordenesDTO = new ArrayList<>();
        for (OrdenCompra o : this.ordenes) {
            ordenesDTO.add(o.toDTO());
        }
        return ordenesDTO;
    }

    public List<OrdenCompraDTO> listarOrdenes(String cuit) {
        List<OrdenCompraDTO> ordenesDTO = new ArrayList<>();
        for (OrdenCompra o : this.ordenes) {
            if (o.getProveedor().getCuit().equals(cuit)) {
                ordenesDTO.add(o.toDTO());
            }
        }
        return ordenesDTO;
    }

    public List<OrdenCompraDTO> listarOrdenes(LocalDate fecha) {
        List<OrdenCompraDTO> ordenesDTO = new ArrayList<>();
        for (OrdenCompra o : this.ordenes) {
            if (o.getFecha().equals(fecha)) {
                ordenesDTO.add(o.toDTO());
            }
        }
        return ordenesDTO;
    }

    public List<OrdenCompraDTO> listarOrdenes(String cuit, LocalDate fecha) {
        List<OrdenCompraDTO> ordenesDTO = new ArrayList<>();
        for (OrdenCompra o : this.ordenes) {
            if (o.getProveedor().getCuit().equals(cuit) && o.getFecha().equals(fecha)) {
                ordenesDTO.add(o.toDTO());
            }
        }
        return ordenesDTO;
    }

    public List<OrdenCompraDTO> listarOrdenes(LocalDate fechaDesde, LocalDate fechaHasta) {
        List<OrdenCompraDTO> ordenesDTO = new ArrayList<>();
        for (OrdenCompra o : this.ordenes) {
            if ((o.getFecha().isAfter(fechaDesde) || o.getFecha().equals(fechaDesde))
                    && (o.getFecha().isBefore(fechaHasta) || o.getFecha().equals(fechaHasta))) {
                ordenesDTO.add(o.toDTO());
            }
        }
        return ordenesDTO;
    }

    public List<OrdenCompraDTO> listarOrdenes(String cuit, LocalDate fechaDesde, LocalDate fechaHasta) {
        List<OrdenCompraDTO> ordenesDTO = new ArrayList<>();
        for (OrdenCompra o : this.ordenes) {
            if (o.getProveedor().getCuit().equals(cuit)
                    && (o.getFecha().isAfter(fechaDesde) || o.getFecha().equals(fechaDesde))
                    && (o.getFecha().isBefore(fechaHasta) || o.getFecha().equals(fechaHasta))) {
                ordenesDTO.add(o.toDTO());
            }
        }
        return ordenesDTO;
    }

    public void agregarOrden(OrdenCompraDTO dto) throws Exception {

        ProveedorDTO proveedor = ProveedorController.getInstance().obtener(dto.cuit);

        OrdenCompra orden = new OrdenCompra(dto);
        orden.setProveedor(proveedor);

        orden.setNumero(this.ordenes.size() + 1);

        for (DetalleDTO d : dto.detalles) {
            Detalle detalle = new Detalle(d);
            detalle.setItem(this.obtenerItem(d.codItem));
            Double precioUnitario = this.precioController.buscarPrecio(dto.cuit, d.codItem);
            detalle.setPrecioUnitario(precioUnitario);
            orden.setDetalle(detalle);
        }

        this.ordenes.add(orden);

        this.ordenService.save(orden);
    }

    public void eliminarOrden(Integer numero) throws Exception {
        OrdenCompra orden = this.obtenerOrden(numero);
        this.ordenes.remove(orden);
        this.ordenService.delete(orden.getNumero());
    }


    //endregion

    //region Factuas
    public void agregarFactura(FacturaDTO factDTO) throws Exception {
        factDTO.numero = this.facturaService.getProximoId();
        Factura nuevaFactura = new Factura(factDTO);

        for (DetalleDTO d : factDTO.detalles) {
            Detalle nuevoDetalle = new Detalle(d);
            nuevoDetalle.setItem(this.obtenerItem(d.codItem));
            nuevaFactura.setDetalle(nuevoDetalle);
        }
        nuevaFactura.setProveedor(ProveedorController.getInstance().obtener(factDTO.cuitProveedor));

        this.facturas.add(nuevaFactura);
        this.facturaService.save(nuevaFactura);
        ProveedorController.getInstance().actualizarBalance(factDTO);

    }

    public void cambiarEstadoFactura(Integer numeroFactura, EstadoPago estadoPago) throws Exception {
        try {
            Factura factura = this.obtenerFactura(numeroFactura);
            factura.setEstadoPago(estadoPago);

            if(!this.facturaService.update(factura))
                throw new Exception("Error al cambiar estado en las facturas");

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private Factura obtenerFactura(Integer numero) {
        for (Factura f : this.facturas) {
            if (f.getNumero().equals(numero)) {
                return f;
            }
        }
        return null;
    }

    public List<FacturaDTO> listarFacturas() {
        List<FacturaDTO> facturasDTO = new ArrayList<>();
        for (Factura f : this.facturas) {
            facturasDTO.add(f.toDTO());
        }
        return facturasDTO;
    }

    public List<FacturaDTO> listarFacturas(String cuit) {
        List<FacturaDTO> facturasDTO = new ArrayList<>();
        for (Factura f : this.facturas) {
            if (f.getCuitProveedor().equals(cuit)) {
                facturasDTO.add(f.toDTO());
            }
        }
        return facturasDTO;
    }

    public List<FacturaDTO> listarFacturas(LocalDate fecha) {
        List<FacturaDTO> facturasDTO = new ArrayList<>();
        for (Factura f : this.facturas) {
            if (f.getFecha().equals(fecha)) {
                facturasDTO.add(f.toDTO());
            }
        }
        return facturasDTO;
    }

    public List<FacturaDTO> listarFacturas(String cuit, LocalDate fecha) {
        List<FacturaDTO> facturasDTO = new ArrayList<>();
        for (Factura f : this.facturas) {
            if (f.getCuitProveedor().equals(cuit) && f.getFecha().equals(fecha)) {
                facturasDTO.add(f.toDTO());
            }
        }
        return facturasDTO;
    }

    public List<FacturaDTO> listarFacturas(LocalDate fechaDesde, LocalDate fechaHasta) {
        List<FacturaDTO> facturasDTO = new ArrayList<>();
        for (Factura f : this.facturas) {
            if ((f.getFecha().isAfter(fechaDesde) || f.getFecha().equals(fechaDesde))
                    && (f.getFecha().isBefore(fechaHasta) || f.getFecha().equals(fechaHasta))) {
                facturasDTO.add(f.toDTO());
            }
        }
        return facturasDTO;
    }

    public List<FacturaDTO> listarFacturas(String cuit, LocalDate fechaDesde, LocalDate fechaHasta) {
        List<FacturaDTO> facturasDTO = new ArrayList<>();
        for (Factura f : this.facturas) {
            if ((f.getCuitProveedor().equals(cuit)
                    && (f.getFecha().isAfter(fechaDesde) || f.getFecha().equals(fechaDesde))
                    && (f.getFecha().isBefore(fechaHasta) || f.getFecha().equals(fechaHasta)))) {
                facturasDTO.add(f.toDTO());
            }
        }
        return facturasDTO;
    }

    public List<FacturaDTO> listarFacturasPorEstado(EstadoPago estado) {
        List<FacturaDTO> facturasDTO = new ArrayList<>();
        for (Factura f : this.facturas) {
            if (f.getEstadoPago().equals(estado))
                facturasDTO.add(f.toDTO());
        }
        return facturasDTO;
    }

    public List<FacturaDTO> listarFacturasPorEstado(String cuit, EstadoPago estado) throws Exception {
        try {
            List<FacturaDTO> facturasDTO = new ArrayList<>();
            for (Factura f : this.facturas) {
                if (f.getCuitProveedor().equals(cuit) && f.getEstadoPago().equals(estado))
                    facturasDTO.add(f.toDTO());
            }
            return facturasDTO;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    //endregion

    //region Private Methods
    private Item obtenerItem(String codigo) {
        Item item = null;
        for (Item i : this.items) {
            if (i.getCodigo().equals(codigo)) {
                item = i;
                break;
            }
        }
        return item;
    }

    private OrdenCompra obtenerOrden(Integer numero) {
        OrdenCompra orden = null;
        for (OrdenCompra o : this.ordenes) {
            if (o.getNumero().equals(numero)) {
                orden = o;
                break;
            }
        }
        return orden;
    }
    //endregion
}
