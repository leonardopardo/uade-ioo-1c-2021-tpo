package tests;

import org.junit.jupiter.api.Test;
import servicios.OrdenCompraService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrdenCompraServiceTest {

    @Test
    public void test_get_all() throws Exception {

        OrdenCompraService service = new OrdenCompraService();
        int proximoID;
        proximoID = service.getProximoNumero();

        assertEquals(1, proximoID);

    }
}


