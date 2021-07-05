package controllers;

import dto.DetalleDTO;
import dto.OrdenCompraDTO;
import modelos.*;
import servicios.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DocumentoController {

    private List<OrdenCompra> ordenes;
    private List<Factura> facturas;
    private List<Item> items;
    private List<Precio> precios;
    private List<Proveedor> proveedores;

    private OrdenCompraService ordenService;
    private FacturaService facturaService;
    private ItemsService itemsService;
    private PrecioService precioService;
    private ProveedorService proveedorService;

    private static DocumentoController instance;

    public DocumentoController() throws Exception {

        this.ordenService = new OrdenCompraService();
        this.ordenes = this.ordenService.getAll();

        this.facturaService = new FacturaService();
        this.facturas = this.facturaService.getAll();

        this.itemsService = new ItemsService();
        this.items = this.itemsService.getAll();

        this.precioService = new PrecioService();
        this.precios = this.precioService.getAll();

        this.proveedorService = new ProveedorService();
        this.proveedores = this.proveedorService.getAll();

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

    public List<OrdenCompraDTO> listarOrdenes(LocalDate fechaDesde, LocalDate fechaHasta) {
        List<OrdenCompraDTO> ordenesDTO = new ArrayList<>();
        for (OrdenCompra o : this.ordenes) {
            if (o.getFecha().isAfter(fechaDesde) && o.getFecha().isBefore(fechaHasta)) {
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

    public List<OrdenCompraDTO> listarOrdenes(String cuit, LocalDate fechaDesde, LocalDate fechaHasta) {
        return new ArrayList<>();
    }

    public void agregarOrden(OrdenCompraDTO dto) throws Exception {

        Proveedor proveedor = this.obtenerProveedor(dto.cuit);

        OrdenCompra orden = new OrdenCompra(dto);
        orden.setProveedor(proveedor);

        orden.setNumero(this.ordenes.size() + 1);

        for (DetalleDTO d : dto.detalles) {
            Detalle detalle = new Detalle(d);
            detalle.setItem(this.obtenerItem(d.codItem));
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


    //region Private Methods
    private Proveedor obtenerProveedor(String cuit) {
        Proveedor proveedor = null;
        for (Proveedor p : this.proveedores) {
            if (p.getCuit().equals(cuit)) {
                proveedor = p;
                break;
            }
        }
        return proveedor;
    }

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
