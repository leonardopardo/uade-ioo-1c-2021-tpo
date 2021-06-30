package tests;

import controllers.DocumentosController;
import dto.DetalleDTO;
import dto.OrdenCompraDTO;
import org.junit.jupiter.api.Test;
import servicios.OrdenCompraService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrdenCompraServiceTest {

    @Test
    public void test_get_all() throws Exception {

        OrdenCompraService service = new OrdenCompraService();
        int proximoID;
        proximoID = service.getProximoNumero();

        assertEquals(1, proximoID);

    }


    @Test
    public void test_puede_agregar_orden_compra() throws Exception {
        OrdenCompraService service = new OrdenCompraService();
        DocumentosController controller = DocumentosController.getInstance();

        OrdenCompraDTO ordenCompraDTO = new OrdenCompraDTO();
        ordenCompraDTO.numero = service.getProximoNumero();
        ordenCompraDTO.cuitProveedor = "23385247249";
        ordenCompraDTO.fecha = LocalDate.now();


        DetalleDTO detalleDTO1 = new DetalleDTO();
        DetalleDTO detalleDTO2 = new DetalleDTO();

        detalleDTO1.codItem = "11";
        detalleDTO1.cantItem = 1.0;
        detalleDTO1.precioUnidad = 130.0;
        detalleDTO1.iva = 2.5;


        detalleDTO2.codItem = "22";
        detalleDTO2.cantItem = 2.0;
        detalleDTO2.precioUnidad = 200.0;
        detalleDTO2.iva = 10.5;

        ordenCompraDTO.detalles.add(detalleDTO1);
        ordenCompraDTO.detalles.add(detalleDTO2);


        controller.agregarOrdenCompra(ordenCompraDTO);
    }

}


