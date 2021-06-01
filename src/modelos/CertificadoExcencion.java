package modelos;

import modelos.enums.TipoRetencion;

import java.time.LocalDate;

public class CertificadoExcencion {

    private Integer id;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private TipoRetencion tipo;

    private Float alicuota;

}
