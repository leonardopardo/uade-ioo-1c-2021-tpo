package modelos;

import dto.OrdenCompraDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenCompra extends Documento {

    public OrdenCompra(OrdenCompraDTO dto) {
        this.fecha = dto.fecha;
        this.detalles = new ArrayList<Detalle>();
    }

    public void setDetalle(Detalle detalle) {
        this.detalles.add(detalle);
    }

    public void setNumero(int num) {
        this.numero = num;
    }

    public OrdenCompraDTO toDTO(){
        OrdenCompraDTO o = new OrdenCompraDTO();
        o.fecha = this.fecha;
        o.numero = this.numero;
        o.razonSocial = this.proveedor.getRazonSocial();
        o.cuit = this.proveedor.getCuit();
        return o;
    }
}
