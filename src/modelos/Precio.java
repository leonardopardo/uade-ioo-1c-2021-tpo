package modelos;

import java.time.LocalDate;

public class Precio {

    private Integer id;
    private Item item;
    private Proveedor proveedor;
    private Double precio;
    private LocalDate fecha;

    public Item getItem() {
        return this.item;
    }

    public Proveedor getProveedor() {
        return this.proveedor;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public String getProveedorCuit() {
        return this.getProveedor().getCuit();
    }

    public String getCodigoItem() {
        return this.getItem().getCodigo();
    }
}
