package model;

import model.enums.LauchRecurrence;
import model.enums.LaunchType;

import java.io.Serializable;
import java.time.LocalDate;

public class Launch implements Serializable {

    private Integer id;
    private String title;
    private String category;
    private Double value;
    private LaunchType type;
    private LauchRecurrence recurrence;
    private boolean fixed;
    private LocalDate date;
    private boolean paid;

    public Launch() {
    }

    public Launch(Integer id, String title, String category, Double value, LaunchType type, LauchRecurrence recurrence, boolean fixed, LocalDate date, boolean paid) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.value = value;
        this.type = type;
        this.recurrence = recurrence;
        this.fixed = fixed;
        this.date = date;
        this.paid = paid;
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
}
