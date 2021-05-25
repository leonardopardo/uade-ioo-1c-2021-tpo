package modelos;

import java.util.ArrayList;

public class Factura extends Documento {

    private OrdenCompra ordenCompra;

    public Factura() {
        this.detalles = new ArrayList<>();
    }

}
