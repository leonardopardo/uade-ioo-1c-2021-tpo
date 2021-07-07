package servicios;

import modelos.OrdenCompra;
import modelos.OrdenPago;
import vendor.GenericDAO;

public class OrdenPagoService extends GenericDAO {


    public OrdenPagoService() throws Exception {
        super(OrdenPago.class, "./src/json/ordenesPago.json");
    }

    public int getProximoNumero() throws Exception {
        return this.getAll().size() + 1;
    }

}
