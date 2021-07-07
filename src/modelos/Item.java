package modelos;

import dto.ItemDTO;
import modelos.enums.AlicuotaIVA;
import modelos.enums.Rubro;
import modelos.enums.TipoItem;
import modelos.enums.Unidad;

public class Item {

    private Integer id;
    private String codigo;
    private String titulo;
    private String descripcion;
    private Unidad unidad;
    private Rubro rubro;
    private TipoItem tipoItem;
    private AlicuotaIVA alicuotaIVA;

    public Item(ItemDTO item) {
        this.codigo = item.codigo;
        this.titulo = item.titulo;
        this.tipoItem = item.tipo;
        this.unidad = item.unidad;
        this.rubro = item.rubro;
        this.alicuotaIVA = item.alicuotaIVA;
    }

    //region Getters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
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
        dto.alicuotaIVA = this.alicuotaIVA;

        return dto;
    }
    //endregion
}