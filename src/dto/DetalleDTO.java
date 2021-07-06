package dto;

import modelos.enums.AlicuotaIVA;
import modelos.enums.TipoIVA;

public class DetalleDTO {

    public String codItem;
    public String descripcion;
    public Double cantItem;
    public Double precioUnidad;
    public AlicuotaIVA alicuotaIVA;
    public Double iva;
    public Double precioTotal;

    public DetalleDTO() {
        this.iva = setIva();
    }

    Double setIva() {

        if (this.alicuotaIVA.equals(AlicuotaIVA.IVA_21)) {
            return 21.0;
        } else if (this.alicuotaIVA.equals(AlicuotaIVA.IVA_10_5)) {
            return 10.5;
        } else if (this.alicuotaIVA.equals(AlicuotaIVA.IVA_5)) {
            return 5.0;
        } else if (this.alicuotaIVA.equals(AlicuotaIVA.IVA_2_5)) {
            return 2.5;
        }

        return 0.0;
    }

}
