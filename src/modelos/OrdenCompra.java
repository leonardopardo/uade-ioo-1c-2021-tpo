package modelos;

import dto.OrdenCompraDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdenCompra extends Documento {

    private int numero;
    private LocalDateTime fecha;
    private String descripcion;
    private List<Detalle> detalles;
    private Double monto;
    private Proveedor proveedor;

    public OrdenCompra(OrdenCompraDTO dto) {
        this.numero = dto.numero;
        this.fecha = dto.fecha;
        this.descripcion = dto.descripcion;
        this.monto = dto.monto;
        this.detalles = new ArrayList<Detalle>();
    }

    public void setProveedor(Proveedor p) {
        this.proveedor = p;
    }


    public void setDetalle(Detalle detalle) {
        this.detalles.add(detalle);
    }


    public void setNumero(int num) {
        this.numero = num;
    }


}
