package modelos;

import dto.FacturaDTO;
import dto.ProveedorDTO;
import modelos.enums.EstadoPago;

import java.util.ArrayList;

public class Factura extends Documento {

    private OrdenCompra ordenCompra;
    public Double iva10;
    public Double iva21;
    public Double monto;

    public Factura(FacturaDTO nuevaFact) {
        this.detalles = new ArrayList<>();
        this.iva21 = nuevaFact.iva21;
        this.iva10 = nuevaFact.iva10;
        this.numero = nuevaFact.numero;
        this.monto = nuevaFact.monto;
        this.fecha = nuevaFact.fecha;
        this.estadoPago = EstadoPago.PENDIENTE;
    }

    public FacturaDTO facturaDTO() {
        return new FacturaDTO();
    }

    public FacturaDTO toDTO() {
        FacturaDTO facturaDTO = new FacturaDTO();
        facturaDTO.monto = this.monto;
        facturaDTO.iva21 = this.iva21;
        facturaDTO.iva10 = this.iva10;
        facturaDTO.numero = this.numero;
        facturaDTO.fecha = this.fecha;
        facturaDTO.razonSocial = this.getRazonSocialProveedor();
        facturaDTO.cuitProveedor = this.getCuitProveedor();
        facturaDTO.estadoPago = this.estadoPago;

        return facturaDTO;
    }

    public void setDetalle(Detalle nuevoDetalle) {
        this.detalles.add(nuevoDetalle);
    }

    public void setProveedor(ProveedorDTO provDTO) throws Exception {
        this.proveedor = new Proveedor(provDTO);
    }

    public String getRazonSocialProveedor() {
        return this.proveedor.getRazonSocial();
    }

    public String getCuitProveedor() {
        return this.proveedor.getCuit();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Factura other = (Factura) obj;

        if (!this.numero.equals(other.getNumero())) {
            return false;
        }

        return true;
    }
}
