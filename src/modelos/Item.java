package modelos;

import modelos.enums.Rubro;
import modelos.enums.Unidad;

public abstract class Item {

    protected Integer id;

    protected String codigo;

    protected String titulo;

    protected String descripcion;

    protected Unidad unidad;

    protected Rubro rubro;

}