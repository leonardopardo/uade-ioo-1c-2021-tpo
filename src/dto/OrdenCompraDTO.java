package dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrdenCompraDTO {
    public int numero;
    public String cuitProveedor;
    public Double monto;
    public LocalDateTime fecha;
    public List<DetalleDTO> detalles;
    public String descripcion;
}
