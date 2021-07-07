package dto;

import java.time.LocalDate;

public class ChequeDTO {
    public LocalDate fechaEmision;
    public LocalDate fechaVencimiento;
    public Double monto;
    public String numero;
    public String titular;
    public String banco;
}
