package modelos;

import modelos.enums.AlicuotaIVA;

public class Detalle {

    private Item item;

    private Float cantidad;

    private Float precioUnitario;

    private AlicuotaIVA iva;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public Float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public AlicuotaIVA getIva() {
        return iva;
    }

    public void setIva(AlicuotaIVA iva) {
        this.iva = iva;
    }
}
