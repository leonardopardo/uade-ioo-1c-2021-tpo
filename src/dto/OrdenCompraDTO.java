package dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdenCompraDTO {

    public int numero;
    public LocalDateTime fecha;
    public String cuitProveedor;
    public String descripcion;
    public List<DetalleDTO> detalles;
    public Double monto;

    public OrdenCompraDTO() {
        this.detalles = new ArrayList<>();
    }

}