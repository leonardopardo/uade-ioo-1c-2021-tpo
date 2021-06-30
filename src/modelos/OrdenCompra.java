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

    public void setProveedor(Proveedor p) {
        this.proveedor = p;
    }

    public void setDetalle(Detalle detalle) {
        this.detalles.add(detalle);
    }

    public void setNumero(int num) {
        this.numero = num;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public List<Detalle> getDetalles() {
        return this.detalles;
    }

    public Proveedor getProveedor() {
        return this.proveedor;
    }

    public String getProveedorCuit(){
        return this.proveedor.getCuit();
    }

    public OrdenCompraDTO toDTO(){
        OrdenCompraDTO o = new OrdenCompraDTO();
        o.fecha = this.fecha;
        o.numero = this.numero;
        o.cuitProveedor = this.proveedor.getCuit();
        o.razonSocial = this.proveedor.getRazonSocial();

        return o;
    }
}
