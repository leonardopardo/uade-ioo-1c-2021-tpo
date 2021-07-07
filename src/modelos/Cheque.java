package modelos;

import java.time.LocalDateTime;

public class Cheque {

    public Integer id;
    public LocalDateTime fechaEmision;
    public LocalDateTime fechaVencimiento;
    public Double monto;
    public Double numero;
    public String titular;
    public String banco;

}
