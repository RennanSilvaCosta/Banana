package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Prestacao implements Serializable {

    private Integer idPrestacao;
    private LocalDate datePrestacao;
    private Double valuePrestacao;
    private boolean paidPrestacao;
    private Integer idLancamento;

    public Prestacao() {
    }

    public Prestacao(Integer idPrestacao, LocalDate datePrestacao, Double valuePrestacao, boolean paidPrestacao, Integer idLancamento) {
        this.idPrestacao = idPrestacao;
        this.datePrestacao = datePrestacao;
        this.valuePrestacao = valuePrestacao;
        this.paidPrestacao = paidPrestacao;
        this.idLancamento = idLancamento;
    }

    public Integer getIdPrestacao() {
        return idPrestacao;
    }

    public void setIdPrestacao(Integer idPrestacao) {
        this.idPrestacao = idPrestacao;
    }

    public LocalDate getDatePrestacao() {
        return datePrestacao;
    }

    public void setDatePrestacao(LocalDate datePrestacao) {
        this.datePrestacao = datePrestacao;
    }

    public Double getValuePrestacao() {
        return valuePrestacao;
    }

    public void setValuePrestacao(Double valuePrestacao) {
        this.valuePrestacao = valuePrestacao;
    }

    public boolean isPaidPrestacao() {
        return paidPrestacao;
    }

    public void setPaidPrestacao(boolean paidPrestacao) {
        this.paidPrestacao = paidPrestacao;
    }

    public Integer getIdLancamento() {
        return idLancamento;
    }

    public void setIdLancamento(Integer idLancamento) {
        this.idLancamento = idLancamento;
    }
}
