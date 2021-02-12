package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Prestacao implements Serializable {

    private Integer id_pretacao;
    private LocalDate date_prestacao;
    private Double value_prestacao;
    private boolean paid_prestacao;
    private Integer id_lancamento;

    public Prestacao() {
    }

    public Prestacao(Integer id_pretacao, LocalDate date_prestacao, Double value_prestacao, boolean paid_prestacao, Integer id_lancamento) {
        this.id_pretacao = id_pretacao;
        this.date_prestacao = date_prestacao;
        this.value_prestacao = value_prestacao;
        this.paid_prestacao = paid_prestacao;
        this.id_lancamento = id_lancamento;
    }

    public Integer getId_pretacao() {
        return id_pretacao;
    }

    public void setId_pretacao(Integer id_pretacao) {
        this.id_pretacao = id_pretacao;
    }

    public LocalDate getDate_prestacao() {
        return date_prestacao;
    }

    public void setDate_prestacao(LocalDate date_prestacao) {
        this.date_prestacao = date_prestacao;
    }

    public Double getValue_prestacao() {
        return value_prestacao;
    }

    public void setValue_prestacao(Double value_prestacao) {
        this.value_prestacao = value_prestacao;
    }

    public boolean isPaid_prestacao() {
        return paid_prestacao;
    }

    public void setPaid_prestacao(boolean paid_prestacao) {
        this.paid_prestacao = paid_prestacao;
    }

    public Integer getId_lancamento() {
        return id_lancamento;
    }

    public void setId_lancamento(Integer id_lancamento) {
        this.id_lancamento = id_lancamento;
    }
}
