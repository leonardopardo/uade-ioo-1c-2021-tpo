package controllers;

import dto.DetalleDTO;
import dto.OrdenCompraDTO;
import modelos.Detalle;
import modelos.OrdenCompra;
import modelos.Factura;
import servicios.FacturaService;
import servicios.OrdenCompraService;

import java.util.ArrayList;
import java.util.List;


public class DocumentosController {

    private static DocumentosController instance;
    private ProveedorController pController;
    private PrecioController precController;
    private List<OrdenCompra> ordenesCompra;
    private List<Factura> facturas;
    private OrdenCompraService ordenCompraService;
    private FacturaService facturaService;

    protected DocumentosController() throws Exception {
        this.ordenCompraService = new OrdenCompraService();
        this.facturaService = new FacturaService();
        this.ordenesCompra = new ArrayList<OrdenCompra>();
        this.facturas = new ArrayList<Factura>();
        this.pController = ProveedorController.getInstance();
        this.precController = PrecioController.getInstance();
    }

    public static DocumentosController getInstance() throws Exception {
        if (instance == null) {
            instance = new DocumentosController();
        }
        return instance;
    }


    // TODO -> Resolver linea 52

    /**
     * @param dto
     * @throws Exception
     * @tarea Dado un DTO OrdenCompra, crea un objeto del dominio.
     */
    public void agregarOrdenCompra(OrdenCompraDTO dto) throws Exception {
        OrdenCompra oc = new OrdenCompra(dto);
        this.ordenesCompra.add(oc);

        //oc.setProveedor(this.pController.obtener(dto.cuitProveedor));
        oc.setNumero(ordenCompraService.getProximoNumero());

        for (DetalleDTO d : dto.detalles) {
            Detalle nuevoDetalle = new Detalle();
            nuevoDetalle.setCantidad(d.cantItem);
        }
    }

    public List<OrdenCompraDTO> listarOrdenes() {
        List<OrdenCompraDTO> ordenes = new ArrayList<>();

        for (OrdenCompra op : this.ordenesCompra) {
            OrdenCompraDTO o = op.toDTO();
            ordenes.add(o);
        }

        return ordenes;
    }

}