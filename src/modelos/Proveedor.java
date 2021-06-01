package modelos;

import modelos.enums.Rubro;
import modelos.enums.TipoIVA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Proveedor {

    private Integer id;

    private String razonSocial;

    private String cuit;

    private TipoIVA tipoIVA;

    private String nombreFantasia;

    private String email;

    private String telefono;

    private LocalDate inicioActividad;

    private String ingresosBrutos;

    private List<Rubro> rubros;

    private Double limiteCtaCte;

    public Proveedor() {
        this.rubros = new ArrayList<>();
    }
}
