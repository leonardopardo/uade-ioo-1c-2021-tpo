package modelos;

import modelos.enums.TipoPago;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pago {

    private Integer id;

    private TipoPago tipo;

    private Double monto;

    private LocalDateTime fecha;

    private OrdenPago orden;

    private List<Cheque> chequeList;

    public Pago(){
        this.chequeList = new ArrayList<>();
    }

    void agregarPago(){
        this.chequeList.add(new Cheque());
    }

}
