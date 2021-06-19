package controllers;

import modelos.Precio;
import modelos.enums.Rubro;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PrecioController {

    List<Precio> precios;
    public static PrecioController instance;

    class CompulsaPrecio {
        public String itemTitulo;
        public Rubro rubro;
        public List<ProveedorCompulsa> listado;

        public CompulsaPrecio() {
            this.listado = new ArrayList<>();
        }
    }

    class ProveedorCompulsa {
        public String razonSocial;
        public String cuit;
        public Double precio;

        public ProveedorCompulsa(String razonSocial, String cuit, Double precio) {
            this.razonSocial = razonSocial;
            this.precio = precio;
            this.cuit = cuit;
        }
    }

    public CompulsaPrecio filtrarPorItem(String codigo, Rubro rubro) {

        try {

            if (this.buscarItemTituloPorCodigo(codigo).equals(null))
                throw new Exception("El código suministrado no corresponde a un item del catálogo.");

            CompulsaPrecio compulsa = new CompulsaPrecio();
            compulsa.itemTitulo = this.buscarItemTituloPorCodigo(codigo);
            compulsa.rubro = rubro;

            precios.forEach(precio -> {
                if (precio.getItem().getCodigo().equals(codigo)
                        && precio.getItem().getRubro().equals(rubro)) {

                    compulsa.listado.add(
                            new ProveedorCompulsa(
                                    precio.getProveedor().getRazonSocial(),
                                    precio.getProveedor().getCuit(),
                                    precio.getPrecio()
                            ));
                }
            });

            return compulsa;

        } catch (Exception ex) {
            ex.getMessage();
        }

        return null;
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

    public static PrecioController getInstance() throws Exception {
        if (instance == null) {
            instance = new PrecioController();
        }
        return instance;
    }

}
