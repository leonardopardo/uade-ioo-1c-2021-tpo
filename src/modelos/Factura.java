package modelos;

import dto.FacturaDTO;

import java.util.ArrayList;

public class Factura extends Documento {

    private OrdenCompra ordenCompra;

    public Factura() {
        this.detalles = new ArrayList<>();
    }

    public FacturaDTO facturaDTO(){
        return new FacturaDTO();
    }
}
