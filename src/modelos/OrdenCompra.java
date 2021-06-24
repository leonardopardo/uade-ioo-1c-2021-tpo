package modelos;

import dto.OrdenCompraDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdenCompra extends Documento {

    private Integer numero;
    private LocalDateTime fecha;
    private String descripcion;
    private List<Detalle> detalles;
    private Proveedor proveedor;

    public OrdenCompra(OrdenCompraDTO dto) {
        this.fecha = dto.fecha;
        this.descripcion = dto.descripcion;
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
        return numero;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Detalle> getDetalles() {
        return detalles;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public String getProveedorCuit(){
        return this.proveedor.getCuit();
    }

    public OrdenCompraDTO toDTO(){
        OrdenCompraDTO o = new OrdenCompraDTO();
        o.fecha = this.fecha;
        o.numero = this.numero;
        o.descripcion = this.descripcion;
        o.cuitProveedor = this.proveedor.getCuit();
        o.razonSocial = this.proveedor.getRazonSocial();

        return o;
    }
}
