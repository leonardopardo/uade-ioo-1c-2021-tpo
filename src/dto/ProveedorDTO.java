package dto;

import modelos.CertificadoExcencion;
import modelos.enums.Rubro;
import modelos.enums.TipoIVA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDTO {

    public Integer id;
    public String razonSocial;
    public String cuit;
    public TipoIVA tipoIVA;
    public String nombreFantasia;
    public String email;
    public String telefono;
    public LocalDate inicioActividad;
    public String ingresosBrutos;
    public List<Rubro> rubros;
    public double limiteCtaCte;

    public ProveedorDTO() {
        this.rubros = new ArrayList<>();
    }

}
