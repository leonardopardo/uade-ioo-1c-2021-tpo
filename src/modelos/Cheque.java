package modelos;

import dto.ChequeDTO;

import java.time.LocalDate;

public class Cheque {

    public LocalDate fechaEmision;
    public LocalDate fechaVencimiento;
    public Double monto;
    public String numero;
    public String titular;
    public String banco;

    public Cheque(ChequeDTO cheque){
        this.banco = cheque.banco;
        this.numero = cheque.numero;
        this.monto = cheque.monto;
        this.titular = cheque.titular;
        this.fechaEmision = cheque.fechaEmision;
        this.fechaVencimiento = cheque.fechaVencimiento;
    }

    public ChequeDTO toDTO(){
        ChequeDTO c = new ChequeDTO();
        c.numero = this.numero;
        c.fechaVencimiento = this.fechaVencimiento;
        c.fechaEmision = this.fechaEmision;
        c.titular = this.titular;
        c.banco = this.banco;
        c.monto = this.monto;

        return c;
    }
}
