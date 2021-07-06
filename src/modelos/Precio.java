package modelos;

import dto.PrecioDTO;

public class Precio {

    private Integer id;
    private Item item;
    private Proveedor proveedor;
    private Double precio;

    public Precio(PrecioDTO precio) {
        this.item = precio.item;
        this.proveedor = precio.proveedor;
        this.precio = precio.precio;
    }

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

    public PrecioDTO toDTO() {
        PrecioDTO dto = new PrecioDTO();
        dto.item = this.item;
        dto.proveedor = this.proveedor;
        dto.precio = this.precio;
        dto.rubro = this.item.getRubro();

        return dto;
    }
}
