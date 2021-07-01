package dto;

import modelos.enums.Rubro;
import modelos.enums.Unidad;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ServicioDTO {

    public String codigo;
    public String titulo;
    public String descripcion;
    public Unidad unidad;
    public Rubro rubro;
    public LocalDate inicio;
    public LocalDateTime fin;

}
