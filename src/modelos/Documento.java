package modelos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Documento {

    protected Integer numero;
    protected LocalDate fecha;
    protected String descripcion;
    protected Proveedor proveedor;
    protected List<Detalle> detalles;

}
