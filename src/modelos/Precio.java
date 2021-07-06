package modelos;

import dto.PrecioDTO;
import dto.ProveedorDTO;

public class Precio {

    private Integer id;
    private Item item;
    private Proveedor proveedor;
    private Double precio;

    public Precio(PrecioDTO precio) {
        this.precio = precio.precio;
    }

    public Integer getId() {
        return id;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setProveedor(ProveedorDTO proveedor) throws Exception {
        this.proveedor = new Proveedor(proveedor);
    }

    public PrecioDTO toDTO() {
        PrecioDTO dto = new PrecioDTO();
        dto.itemCodigo = this.item.getCodigo();
        dto.itemTitulo = this.item.getTitulo();
        dto.rubro = this.item.getRubro();
        dto.razonSocial = this.proveedor.getRazonSocial();
        dto.cuit = this.proveedor.getCuit();
        dto.precio = this.precio;
        return dto;
    }
}
