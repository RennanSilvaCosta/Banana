package model;

import enums.LancamentoType;
import enums.Recorrencia;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Lancamento implements Serializable {

    private Integer id;
    private String title;
    private String description;
    private String note;
    private LocalDate date;
    private Double value;
    private Set<Integer> type = new HashSet<>();
    private Set<Integer> recorrencia = new HashSet<>();
    private Integer parcelas;

    public Lancamento() {
    }

    public Lancamento(Integer id, String title, String description, String note, LocalDate date, Double value) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.note = note;
        this.date = date;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public Set<LancamentoType> getType() {
        return type.stream().map(x -> LancamentoType.toEnum(x)).collect(Collectors.toSet());
    }

    public void setType(LancamentoType lancamentoType) {
        type.add(lancamentoType.getCod());
    }

    public Set<Recorrencia> getRecorrencia() {
        return recorrencia.stream().map(x -> Recorrencia.toEnum(x)).collect(Collectors.toSet());
    }

    public void setRecorrencia(Recorrencia lancamentoRecorrencia) {
        recorrencia.add(lancamentoRecorrencia.getCod());
    }

}
