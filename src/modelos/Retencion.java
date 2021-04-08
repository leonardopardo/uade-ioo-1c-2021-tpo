package modelos;

import modelos.enums.TipoRetencion;

public class Retencion {
    private TipoRetencion tipo;
    private Float alicuota;

    public Retencion(TipoRetencion tipo, Double alicuota) {
        this.tipo = tipo;
    }
}
