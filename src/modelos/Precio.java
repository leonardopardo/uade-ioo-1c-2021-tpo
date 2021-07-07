package modelos;

import dto.PrecioDTO;
import dto.ProveedorDTO;
import modelos.enums.Rubro;

public class Precio {

    private Integer id;
    private Item item;
    private Proveedor proveedor;
    private Double precio;

    public Precio(PrecioDTO precio) {
        this.precio = precio.precio;
    }

    //region Getters
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

    public Rubro getItemRubro(){
        return this.getItem().getRubro();
    }
    //endregion

    //region Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setProveedor(ProveedorDTO proveedor) throws Exception {
        this.proveedor = new Proveedor(proveedor);
    }
    //endregion

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
