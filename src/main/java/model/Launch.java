package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Launch implements Serializable {

    private Integer id;
    private String title;
    private int category;
    private Double value;
    private int type;
    private int recurrence;
    private LocalDate date;
    private boolean fixed;
    private int paid;
    private boolean parcel;
    private List<Installment> prestacaoes;
    private User user;

    public Launch() {
    }

    public Launch(Integer id, String title, int category, Double value, int type, int recurrence, LocalDate date, boolean fixed, int paid, boolean parcel, List<Installment> prestacaoes, User user) {
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
        this.user = user;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(int recurrence) {
        this.recurrence = recurrence;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public boolean isParcel() {
        return parcel;
    }

    public void setParcel(boolean parcel) {
        this.parcel = parcel;
    }

    public List<Installment> getPrestacaoes() {
        return prestacaoes;
    }

    public void setPrestacaoes(List<Installment> prestacaoes) {
        this.prestacaoes = prestacaoes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
