package modelos;


public class Detalle {

    private Item item;
    private Double cantidad;
    private Double precioUnitario;
    private Double iva;


    public Detalle() {
    }


    public Double getPrecioUnitario() {
        return this.precioUnitario;
    }


    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }


}
