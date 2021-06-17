package servicios;

import modelos.Factura;
import vendor.GenericDAO;

public class FacturaService extends GenericDAO {

    public FacturaService() throws Exception {
        super(Factura.class, "./src/json/facturas.json");
    }
}
