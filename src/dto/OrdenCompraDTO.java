package dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenCompraDTO {

    public Integer numero;
    public LocalDate fecha;
    public String razonSocial;
    public String cuit;
    public List<DetalleDTO> detalles;

    public OrdenCompraDTO() {
        this.detalles = new ArrayList<>();
    }

}