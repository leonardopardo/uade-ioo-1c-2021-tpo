package controllers;

import dto.*;
import modelos.Item;
import modelos.Precio;
import modelos.enums.Rubro;
import servicios.ItemsService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PrecioController {

    private List<Precio> precios;
    private List<Item> items;
    public static PrecioController instance;
    private ItemsService service;
    protected static final String ITEM_EXISTENTE_EXCEPTION = "El item que intenta agregar ya existe.";

    private PrecioController() throws Exception {
        this.service = new ItemsService();
        this.items = this.service.getAll();
    }

    public static PrecioController getInstance() throws Exception {
        if (instance == null) {
            instance = new PrecioController();
        }
        return instance;
    }

    /**
     * @param codigo
     * @param rubro
     * @return CompulsaPrecioDTO
     * @tarea Dado un codigo de item y un rubro, devuelve una lista de proveedores que ofrecen el item y su precio.
     */
    public CompulsaPrecioDTO filtrarPorItem(String codigo, Rubro rubro) {

        try {

            if (this.buscarItemTituloPorCodigo(codigo).equals(null))
                throw new Exception("El código suministrado no corresponde a un item del catálogo.");

            CompulsaPrecioDTO compulsa = new CompulsaPrecioDTO();
            compulsa.itemTitulo = this.buscarItemTituloPorCodigo(codigo);
            compulsa.rubro = rubro;

            precios.forEach(precio -> {
                if (precio.getItem().getCodigo().equals(codigo) &&
                        precio.getItem().getRubro().equals(rubro)) {

                    ProveedorCompulsaDTO pcDTO = new ProveedorCompulsaDTO();
                    pcDTO.razonSocial = precio.getProveedor().getRazonSocial();
                    pcDTO.cuit = precio.getProveedor().getCuit();
                    pcDTO.precio = precio.getPrecio();

                    compulsa.listado.add(pcDTO);
                }
            });

            return compulsa;

        } catch (Exception ex) {
            ex.getMessage();
        }

        return null;
    }

    /**
     * @param codigo
     * @return String titulo
     * @tarea Dado un codigo de item devuelve el titulo del mismo.
     */
    private String buscarItemTituloPorCodigo(String codigo) {

        AtomicReference<String> titulo = null;

        this.precios.forEach(precio -> {
            if (precio.getItem().getCodigo().equals(codigo)) {
                titulo.set(precio.getItem().getTitulo());
            }
        });

        return titulo.get();
    }

    /**
     * @return
     */
    public List<ItemDTO> listarItems() {
        List<ItemDTO> lista = new ArrayList<>();

        for (Item i : this.items) {
            ItemDTO x = new ItemDTO();
            x.codigo = i.getCodigo();
            x.titulo = i.getTitulo();
            x.unidad = i.getUnidad();
            x.rubro = i.getRubro();
            x.tipo = i.getTipo();

            lista.add(x);
        }

        return lista;
    }

    public void agregar(ItemDTO item) throws Exception {
        try {
            if (this.items.contains(item)) {
                throw new Exception(ITEM_EXISTENTE_EXCEPTION);
            }

            Item nuevoItem = new Item(item);

            this.service.save(nuevoItem);
            Collections.addAll(this.items, nuevoItem);

        } catch (Exception ex) {
            throw ex;
        }
    }
}