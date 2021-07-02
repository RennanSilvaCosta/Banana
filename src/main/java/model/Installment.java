package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Installment implements Serializable {

    private Integer id;
    private LocalDate date;
    private Double value;
    private int paid;
    private Integer idLancamento;

    public Installment() {
    }

    public Installment(Integer id, LocalDate date, Double value, int paid, Integer idLancamento) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.paid = paid;
        this.idLancamento = idLancamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public Integer getIdLancamento() {
        return idLancamento;
    }

    public void setIdLancamento(Integer idLancamento) {
        this.idLancamento = idLancamento;
    }
}
