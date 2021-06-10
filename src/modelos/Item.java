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

    public String getCodigo() {
        return codigo;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public String getTitulo() {
        return titulo;
    }
}