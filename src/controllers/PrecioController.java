package controllers;

import dto.*;
import modelos.Item;
import modelos.Precio;
import modelos.enums.Rubro;
import modelos.enums.TipoItem;
import modelos.enums.Unidad;
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

    //region Exceptions
    protected static final String ITEM_EXISTENTE_EXCEPTION =
            "El item que intenta agregar ya existe.";
    protected static final String PRECIO_EXISTENTE_EXCEPTION =
            "El precio que intenta agregar ya existe.";
    //endregion

    private PrecioController() throws Exception {
        this.itemsService = new ItemsService();
        this.items = this.itemsService.getAll();
        this.precioService = new PrecioService();
        this.precios = this.precioService.getAll();
    }

    public static PrecioController getInstance() throws Exception {
        if (instance == null) {
            instance = new PrecioController();
        }
        return instance;
    }

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

    public List<PrecioDTO> listarPrecios() {
        try {
            List<PrecioDTO> precios = new ArrayList<>();
            this.precios.forEach(precio -> {
                precios.add(precio.toDTO());
            });

            return precios;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<PrecioDTO> listarPrecios(Rubro rubro) {
        List<PrecioDTO> lista = new ArrayList<>();
        for (Precio p : this.precios) {
            if (p.getItemRubro().equals(rubro)) {
                lista.add(p.toDTO());
            }
        }
        return lista;
    }

    public List<PrecioDTO> listarPreciosPorItem(Rubro rubro, String codigoItem) {
        List<PrecioDTO> lista = new ArrayList<>();
        for (Precio p : this.precios) {
            if (p.getItemRubro().equals(rubro) && p.getCodigoItem().equals(codigoItem)) {
                lista.add(p.toDTO());
            }
        }
        return lista;
    }

    public List<PrecioDTO> listarPreciosPorProveedor(Rubro rubro, String cuitProveedor) {
        List<PrecioDTO> lista = new ArrayList<>();
        for (Precio p : this.precios) {
            if (p.getItemRubro().equals(rubro) && p.getProveedorCuit().equals(cuitProveedor)) {
                lista.add(p.toDTO());
            }
        }
        return lista;
    }

    public List<PrecioDTO> listarPrecios(Rubro rubro, String codigoItem, String cuitProveedor) {
        List<PrecioDTO> lista = new ArrayList<>();
        for (Precio p : this.precios) {
            if (p.getItemRubro().equals(rubro)
                    && p.getCodigoItem().equals(codigoItem)
                    && p.getProveedorCuit().equals(cuitProveedor)) {
                lista.add(p.toDTO());
            }
        }
        return lista;
    }

    public List<PrecioDTO> listarPrecios(String codigoItem, String cuitProveedor) {
        List<PrecioDTO> lista = new ArrayList<>();
        for (Precio p : this.precios) {
            if (p.getCodigoItem().equals(codigoItem) && p.getProveedorCuit().equals(cuitProveedor)) {
                lista.add(p.toDTO());
            }
        }
        return lista;
    }

    public List<PrecioDTO> listarPreciosPorItem(String codigoItem) {
        List<PrecioDTO> lista = new ArrayList<>();
        for (Precio p : this.precios) {
            if (p.getCodigoItem().equals(codigoItem)) {
                lista.add(p.toDTO());
            }
        }
        return lista;
    }

    public List<PrecioDTO> listarPreciosPorProveedor(String cuitProveedor) {
        List<PrecioDTO> lista = new ArrayList<>();
        for (Precio p : this.precios) {
            if (p.getProveedorCuit().equals(cuitProveedor)) {
                lista.add(p.toDTO());
            }
        }
        return lista;
    }

    public List<ItemDTO> listarItems(Rubro rubro) {
        List<ItemDTO> lista = new ArrayList<>();
        for (Item i : this.items) {
            if (i.getRubro().equals(rubro)) {
                lista.add(i.toDTO());
            }
        }
        return lista;
    }

    public List<ItemDTO> listarItems(Rubro rubro, TipoItem tipoItem, Unidad unidad) {
        List<ItemDTO> lista = new ArrayList<>();
        for (Item i : this.items) {
            if (i.getRubro().equals(rubro) && i.getTipoItem().equals(tipoItem) && i.getUnidad().equals(unidad)) {
                lista.add(i.toDTO());
            }
        }
        return lista;
    }

    public List<ItemDTO> listarItems(TipoItem tipoItem, Unidad unidad) {
        List<ItemDTO> lista = new ArrayList<>();
        for (Item i : this.items) {
            if (i.getTipoItem().equals(tipoItem) && i.getUnidad().equals(unidad)) {
                lista.add(i.toDTO());
            }
        }
        return lista;
    }

    public List<ItemDTO> listarItemsPorTipo(Rubro rubro, TipoItem tipoItem) {
        List<ItemDTO> lista = new ArrayList<>();
        for (Item i : this.items) {
            if (i.getRubro().equals(rubro) && i.getTipoItem().equals(tipoItem)) {
                lista.add(i.toDTO());
            }
        }
        return lista;
    }

    public List<ItemDTO> listarItemsPorTipo(TipoItem tipoItem) {
        List<ItemDTO> lista = new ArrayList<>();
        for (Item i : this.items) {
            if (i.getTipoItem().equals(tipoItem)) {
                lista.add(i.toDTO());
            }
        }
        return lista;
    }

    public List<ItemDTO> listarItemsPorUnidad(Rubro rubro, Unidad unidad) {
        List<ItemDTO> lista = new ArrayList<>();
        for (Item i : this.items) {
            if (i.getRubro().equals(rubro) && i.getUnidad().equals(unidad)) {
                lista.add(i.toDTO());
            }
        }
        return lista;
    }

    public List<ItemDTO> listarItemsPorUnidad(Unidad unidad) {
        List<ItemDTO> lista = new ArrayList<>();
        for (Item i : this.items) {
            if (i.getUnidad().equals(unidad)) {
                lista.add(i.toDTO());
            }
        }
        return lista;
    }

    //region ABM Item-Precio
    public void agregar(ItemDTO item) throws Exception {
        try {
            if (this.items.contains(item)) {
                throw new Exception(ITEM_EXISTENTE_EXCEPTION);
            }

            Item nuevoItem = new Item(item);
            nuevoItem.setId(this.itemsService.getProximoId());

            this.itemsService.save(nuevoItem);
            this.items.add(nuevoItem);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void agregar(PrecioDTO precio) throws Exception {
        try {

            if (this.precios.contains(precio))
                throw new Exception(PRECIO_EXISTENTE_EXCEPTION);

            Precio nuevoPrecio = new Precio(precio);
            nuevoPrecio.setProveedor(ProveedorController.getInstance().obtener(precio.cuit));
            nuevoPrecio.setItem(this.obtenerItemModelPorCodigo(precio.itemCodigo));
            nuevoPrecio.setId(this.precioService.getProximoId());

            this.precioService.save(nuevoPrecio);

            this.precios.add(nuevoPrecio);

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void actualizar(ItemDTO item) throws Exception {

        try {
            Item nuevoItem = obtenerItemModelPorCodigo(item.codigo);

            if (nuevoItem == null)
                throw new Exception("El item que intenta actualizar no se encuentra en la lista de precios.");

            nuevoItem.setTitulo(item.titulo);
            nuevoItem.setCodigo(item.codigo);
            nuevoItem.setDescripcion(item.descripcion);
            nuevoItem.setRubro(item.rubro);
            nuevoItem.setUnidad(item.unidad);
            nuevoItem.setId(this.itemsService.getProximoId());

            this.itemsService.update(nuevoItem);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void actualizar(PrecioDTO precio) throws Exception {
        try {
            Precio precioExistente = this.obtenerPrecio(precio.itemCodigo, precio.cuit);

            if (precioExistente == null)
                throw new Exception("El precio que intenta actualizar no se encuentra en la lista de precios.");

            precioExistente.setPrecio(precio.precio);
            precioExistente.setProveedor(ProveedorController.getInstance().obtener(precio.cuit));
            precioExistente.setItem(this.obtenerItemModelPorCodigo(precio.itemCodigo));
            precioExistente.setId(this.precioService.getProximoId());

            this.precioService.update(precioExistente);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void eliminar(String codigo) throws Exception {
        try {
            Item item = this.obtenerItemModelPorCodigo(codigo);
            if (item != null) {
                this.itemsService.delete(item.getId());
                this.items.remove(item);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public void eliminar(String codigoItem, String cuitProveedor) throws Exception {
        try {
            Precio precio = this.obtenerPrecio(codigoItem, cuitProveedor);
            if (precio != null) {
                this.precioService.delete(precio.getId());
                this.precios.remove(precio);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    //endregion

    //region Búsqueda Items
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
    //endregion

    //region Métodos Privados
    private Item obtenerItemModelPorTitulo(String itemTitulo) {
        try {
            Item item = null;

            for (Item i : this.items) {
                if (i.getTitulo().equals(itemTitulo))
                    item = i;
            }

            return item;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private Item obtenerItemModelPorCodigo(String itemCodigo) {
        try {

            Item item = null;

            for (Item i : this.items) {
                if (i.getCodigo().equals(itemCodigo))
                    item = i;
            }

            return item;

        } catch (Exception ex) {
            throw ex;
        }
    }

    private String buscarItemTituloPorCodigo(String codigo) {

        AtomicReference<String> titulo = null;

        this.precios.forEach(precio -> {
            if (precio.getItem().getCodigo().equals(codigo)) {
                titulo.set(precio.getItem().getTitulo());
            }
        });

        return titulo.get();
    }

    private Precio obtenerPrecio(String itemCodigo, String cuitProveedor) {
        for (Precio p : this.precios) {
            if (p.getProveedor().getCuit().equals(cuitProveedor) && p.getItem().getCodigo().equals(itemCodigo)) {
                return p;
            }
        }

        return null;
    }
    //endregion

    public Double buscarPrecio(String cuit, String codItem) {
        for (Precio p : this.precios) {
            if (p.getProveedorCuit().equals(cuit) && p.getCodigoItem().equals(codItem)) {
                return p.getPrecio();
            }
        }
        return null;
    }
}