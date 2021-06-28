package modelos;

import dto.CertificadoDTO;
import modelos.enums.TipoRetencion;

import java.time.LocalDate;

public class CertificadoExcencion {

    private Integer id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private TipoRetencion tipo;
    private Float alicuota;

    public CertificadoExcencion(CertificadoDTO certifDTO) {
        this.fechaInicio = certifDTO.fechaInicio;
        this.fechaFin = certifDTO.fechaFin;
        this.tipo = certifDTO.tipo;
        this.alicuota = certifDTO.alicuota;
    }

    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    public LocalDate getFechaFin() {
        return this.fechaFin;
    }

    public TipoRetencion getTipo() {
        return this.tipo;
    }

    public Float getAlicuota() {
        return this.alicuota;
    }
}
