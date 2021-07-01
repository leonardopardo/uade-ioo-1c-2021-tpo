package controllers;

import dto.CompulsaPrecioDTO;
import dto.ItemDTO;
import dto.ProveedorCompulsaDTO;
import modelos.Detalle;
import modelos.Item;
import modelos.Precio;
import modelos.enums.Rubro;
import servicios.ItemsService;
import servicios.PrecioService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PrecioController {

    private List<Precio> precios;
    private List<Item> items;
    private ItemsService itemsService;
    private PrecioService precioService;

    public static PrecioController instance;
    protected static final String ITEM_EXISTENTE_EXCEPTION = "El item que intenta agregar ya existe.";

    private PrecioController() throws Exception {
        this.precios = new ArrayList<>();
        this.items = new ArrayList<>();
        this.itemsService = new ItemsService();
        this.precioService = new PrecioService();

        this.items = this.itemsService.getAll();
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
        try {
            List<ItemDTO> items = new ArrayList<>();
            this.items.forEach(item -> {
                items.add(item.toDTO());
            });

            return items;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void agregar(ItemDTO item) throws Exception {
        try {
            if (this.items.contains(item)) {
                throw new Exception(ITEM_EXISTENTE_EXCEPTION);
            }

            Item nuevoItem = new Item(item);
            this.itemsService.save(nuevoItem);
            Collections.addAll(this.items, nuevoItem);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public ItemDTO obtenerItemPorCodigo(String itemCodigo) {
        try {

            ItemDTO item = null;

            for (Item i : this.items) {
                if (i.getCodigo().equals(itemCodigo))
                    item = i.toDTO();
            }

            return item;

        } catch (Exception ex) {
            throw ex;
        }
    }

    public ItemDTO obtenerItemPorTitulo(String itemTitulo) {
        try {
            ItemDTO item = null;

            for (Item i : this.items) {
                if (i.getTitulo().equals(itemTitulo))
                    item = i.toDTO();
            }

            return item;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void setItemEnDetalle(String codigo, Detalle detalle) {
        for (Item i : this.items) {
            if(i.getCodigo().equals(codigo))
                detalle.setItem(i);
        }
    }
}