package dto;

import java.util.ArrayList;
import java.util.List;

public class CuentaCorrienteDTO {

    public List<FacturaDTO> facturasPagas;
    public List<FacturaDTO> facturasImpagas;
    public ProveedorCompulsaDTO proveedorCompulsaDTO;

    public CuentaCorrienteDTO() {
        this.facturasPagas = new ArrayList<>();
        this.facturasImpagas = new ArrayList<>();
    }
}