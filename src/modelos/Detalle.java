package modelos;


import dto.DetalleDTO;

public class Detalle {

    private Item item;

    private Double cantidad;

    private Double precioUnitario;

    private Double iva;

    public Detalle(DetalleDTO detalle){
        this.cantidad = detalle.cantItem;
        this.precioUnitario = detalle.precioUnidad;
        this.iva = detalle.iva;
    }

    //region Getters
    public Double getPrecioUnitario() {
        return this.precioUnitario;
    }

    public String getItemDescription(){
        return this.item.getTitulo();
    }
    //endregion

    //region Setters
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    //endregion

    public DetalleDTO toDTO() {
        DetalleDTO dto = new DetalleDTO();
        dto.codItem = this.item.getCodigo();
        dto.descripcion = this.item.getTitulo();
        return dto;
    }

}
