package model;

import model.enums.LauchRecurrence;
import model.enums.LaunchType;

import java.io.Serializable;
import java.time.LocalDate;

public class Lancamento implements Serializable {

    private Integer id;
    private String title;
    private String category;
    private String description;
    private String note;
    private LocalDate date;
    private Double value;
    private Integer parcelas;
    private LaunchType type;
    private LauchRecurrence recurrence;

    public Lancamento() {
    }

    public Lancamento(Integer id, String title, String category, String description, String note, LocalDate date, Double value, Integer parcelas, LaunchType type, LauchRecurrence recurrence) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.note = note;
        this.date = date;
        this.value = value;
        this.parcelas = parcelas;
        this.type = type;
        this.recurrence = recurrence;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public LaunchType getType() {
        return type;
    }

    public void setType(LaunchType type) {
        this.type = type;
    }

    public LauchRecurrence getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(LauchRecurrence recurrence) {
        this.recurrence = recurrence;
    }
}
