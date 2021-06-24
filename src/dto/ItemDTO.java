package dto;

import modelos.enums.Rubro;
import modelos.enums.TipoItem;
import modelos.enums.Unidad;

import java.time.LocalDate;

public class ItemDTO {

    public String codigo;
    public String titulo;
    public String descripcion;
    public Rubro rubro;
    public Unidad unidad;
    public TipoItem tipo;
    public LocalDate inicio;
    public LocalDate fin;

}
