package dto;

import modelos.enums.AlicuotaIVA;

public class DetalleDTO {

    public String codItem;
    public String descripcion;
    public Double cantItem;
    public Double precioUnidad;
    public AlicuotaIVA alicuotaIVA;
    public Double iva;
    public Double precioTotal;


    public void setIva() {

        if (this.alicuotaIVA.equals(AlicuotaIVA.IVA_21)) {
            this.iva = 21.0;
        } else if (this.alicuotaIVA.equals(AlicuotaIVA.IVA_10_5)) {
            this.iva = 10.5;
        } else if (this.alicuotaIVA.equals(AlicuotaIVA.IVA_5)) {
            this.iva = 5.0;
        } else if (this.alicuotaIVA.equals(AlicuotaIVA.IVA_2_5)) {
            this.iva = 2.5;
        } else {
            this.iva = 0.0;
        }
    }

    public void setPrecioTotal() {
        if (this.precioUnidad != null && this.cantItem != null) {
            this.precioTotal = precioUnidad * cantItem;
        }
    }
}
