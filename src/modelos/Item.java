package modelos;

import dto.ItemDTO;
import dto.ProductoDTO;
import dto.ServicioDTO;
import modelos.enums.Rubro;
import modelos.enums.TipoItem;
import modelos.enums.Unidad;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Item {

    private Integer id;
    private String codigo;
    private String titulo;
    private String descripcion;
    private Unidad unidad;
    private Rubro rubro;
    private LocalDate inicio;
    private LocalDateTime fin;
    private TipoItem tipoItem;


    public Item(ServicioDTO dto) {
        this.tipoItem = TipoItem.SERVICIO;
        this.inicio = dto.inicio;
        this.fin = dto.fin;

        this.codigo = dto.codigo;
        this.titulo = dto.titulo;
        this.descripcion = dto.descripcion;
        this.unidad = dto.unidad;
        this.rubro = dto.rubro;

    }

    public Item(ProductoDTO dto) {
        this.tipoItem = TipoItem.PRODUCTO;

        this.codigo = dto.codigo;
        this.titulo = dto.titulo;
        this.descripcion = dto.descripcion;
        this.unidad = dto.unidad;
        this.rubro = dto.rubro;
    }

    public Item(ItemDTO dto) {
        this.tipoItem = TipoItem.PRODUCTO;

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

}