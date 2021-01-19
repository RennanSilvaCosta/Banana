package model;

import model.enums.LauchRecurrence;
import model.enums.LaunchType;

import java.io.Serializable;

public class Lancamento implements Serializable {

    private Integer id;
    private String title;
    private String category;
    private Double value;
    private Integer totalParcelas;
    private Integer parcelas;
    private LaunchType type;
    private LauchRecurrence recurrence;
    private boolean fixed;
    private int month;
    private int year;
    private boolean paid;

    public Lancamento() {
    }

    public Lancamento(Integer id, String title, String category, Double value, Integer totalParcelas, LaunchType type, LauchRecurrence recurrence, boolean fixed, int month, int year, boolean paid) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.value = value;
        this.totalParcelas = totalParcelas;
        this.type = type;
        this.recurrence = recurrence;
        this.fixed = fixed;
        this.month = month;
        this.year = year;
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

    public Integer getTotalParcelas() {
        return totalParcelas;
    }

    public void setTotalParcelas(Integer totalParcelas) {
        this.totalParcelas = totalParcelas;
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

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
