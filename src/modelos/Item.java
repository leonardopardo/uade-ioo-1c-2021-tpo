package modelos;

import dto.ItemDTO;
import modelos.enums.AlicuotaIVA;
import modelos.enums.Rubro;
import modelos.enums.TipoItem;
import modelos.enums.Unidad;

import java.time.LocalDateTime;

public class Item {

    private Integer id;
    private String codigo;
    private String titulo;
    private String descripcion;
    private Unidad unidad;
    private Rubro rubro;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private TipoItem tipoItem;
    private AlicuotaIVA alicuotaIVA;

    public Item(ItemDTO item) {
        this.codigo = item.codigo;
        this.titulo = item.titulo;
        this.tipoItem = item.tipo;
        this.unidad = item.unidad;
        this.rubro = item.rubro;
        this.inicio = item.inicio;
        this.fin = item.fin;
        this.alicuotaIVA = item.alicuotaIVA;
    }

    //region Getters
    public Integer getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public TipoItem getTipoItem() {
        return tipoItem;
    }

    public AlicuotaIVA getAlicuotaIVA() {
        return alicuotaIVA;
    }
    //endregion

    //region Methods
    public ItemDTO toDTO() {
        ItemDTO dto = new ItemDTO();
        dto.codigo = this.codigo;
        dto.titulo = this.titulo;
        dto.tipo = this.tipoItem;
        dto.unidad = this.unidad;
        dto.rubro = this.rubro;
        dto.inicio = this.inicio;
        dto.fin = this.fin;
        dto.alicuotaIVA = this.alicuotaIVA;

        return dto;
    }
    //endregion
}