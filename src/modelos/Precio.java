package modelos;

import java.time.LocalDate;

public class Precio {

    private Integer id;

    private Item item;

    private Proveedor proveedor;

    private Double precio;

    private LocalDate fecha;

    public Item getItem() {
        return item;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Double getPrecio() {
        return precio;
    }
}
