package dto;

import modelos.enums.Rubro;

import java.util.ArrayList;
import java.util.List;

public class CompulsaPrecioDTO {

    public String itemTitulo;
    public Rubro rubro;
    public List<ProveedorCompulsaDTO> listado;

    public CompulsaPrecioDTO() {
        this.listado = new ArrayList<>();
    }

}
