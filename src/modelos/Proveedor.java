package modelos;

import dto.ProveedorDTO;
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

    private Double balance;

    public Proveedor() {
        this.rubros = new ArrayList<>();
    }

    public String getCuit() {
        return cuit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public Double getBalance() {
        return balance;
    }

    public ProveedorDTO toDTO(){
        return new ProveedorDTO();
    }
}
