package dto;

import modelos.enums.TipoRetencion;

import java.time.LocalDate;

public class CertificadoDTO {
    public TipoRetencion tipo;
    public LocalDate fechaInicio;
    public LocalDate fechaFin;
}
