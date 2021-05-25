package modelos;

import modelos.enums.TipoPago;

import java.time.LocalDateTime;

public class Pago {

    private Integer id;

    private TipoPago tipo;

    private Double monto;

    private LocalDateTime fecha;

    private OrdenPago orden;

}
