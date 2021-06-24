package modelos;

import dto.ItemDTO;
import modelos.enums.Rubro;
import modelos.enums.TipoItem;
import modelos.enums.Unidad;

import java.time.LocalDate;

public class Item {

    private Integer id;
    private String codigo;
    private String titulo;
    private String descripcion;
    private Unidad unidad;
    private Rubro rubro;
    private LocalDate inicio;
    private LocalDate fin;
    private TipoItem tipoItem;


    public Item(ItemDTO dto) {
        this.tipoItem = dto.tipo;
        this.codigo = dto.codigo;
        this.titulo = dto.titulo;
        this.descripcion = dto.descripcion;
        this.unidad = dto.unidad;
        this.rubro = dto.rubro;
        this.inicio = dto.inicio;
        this.fin = dto.fin;
    }


    public String getCodigo() {
        return codigo;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public String getTitulo() {
        return titulo;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public TipoItem getTipo() {
        return tipoItem;
    }

}