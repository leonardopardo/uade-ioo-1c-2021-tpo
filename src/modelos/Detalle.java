package modelos;

import dto.DetalleDTO;
import modelos.enums.AlicuotaIVA;

public class Detalle {

    private Integer id;

    private Item item;

    private Float cantidad;

    private Double precioUnitario;

    private AlicuotaIVA iva;

    public Detalle(DetalleDTO d) {
        this.cantidad = d.cantItem;
        this.precioUnitario = d.precioUnidad;
        this.iva = d.iva;
    }

    public void setItem(Item item){
        this.item = item;
    }


}
