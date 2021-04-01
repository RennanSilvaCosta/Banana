package model;

import model.enums.LauchRecurrence;
import model.enums.LaunchType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Launch implements Serializable {

    private Integer id;
    private String title;
    private String category;
    private Double value;
    private LaunchType type;
    private LauchRecurrence recurrence;
    private LocalDate date;
    private boolean fixed;
    private boolean paid;
    private boolean parcel;
    private List<Prestacao> prestacaoes;

    public Launch() {
    }

    public Launch(Integer id, String title, String category, Double value, LaunchType type, LauchRecurrence recurrence, LocalDate date, boolean fixed, boolean paid, boolean parcel, List<Prestacao> prestacaoes) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.value = value;
        this.type = type;
        this.recurrence = recurrence;
        this.date = date;
        this.fixed = fixed;
        this.paid = paid;
        this.parcel = parcel;
        this.prestacaoes = prestacaoes;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
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

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isParcel() {
        return parcel;
    }

    public void setParcel(boolean parcel) {
        this.parcel = parcel;
    }

    public List<Prestacao> getPrestacaoes() {
        return prestacaoes;
    }

    public void setPrestacaoes(List<Prestacao> prestacaoes) {
        this.prestacaoes = prestacaoes;
    }
}
