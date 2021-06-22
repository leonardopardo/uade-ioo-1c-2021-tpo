package modelos;

import dto.CertificadoDTO;
import dto.ProveedorCompulsaDTO;
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
    private List<CertificadoExcencion> certificados;

    private Double balance;

    public Proveedor() {

        this.rubros = new ArrayList<>();
        this.certificados = new ArrayList<>();
    }

    public String getCuit() {
        return cuit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public List<CertificadoDTO> getCertificados(){
        List<CertificadoDTO> list = new ArrayList<>();
        for (CertificadoExcencion c : this.certificados) {
            CertificadoDTO x = new CertificadoDTO();
            x.tipo = c.getTipo();
            x.fechaInicio = c.getFechaInicio();
            x.fechaFin = c.getFechaFin();

            list.add(x);
        }

        return list;
    }

    public Double getBalance() {
        return balance;
    }

    public ProveedorCompulsaDTO toDTO() {
        return new ProveedorCompulsaDTO();
    }
}
