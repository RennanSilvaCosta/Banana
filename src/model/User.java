package model;

import java.io.Serializable;

public class User implements Serializable {

    private Integer idUsuario;
    private String name;
    private String email;
    private String password;

    public User() {
    }

    public User(Integer idUsuario, String name, String email, String password) {
        this.idUsuario = idUsuario;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
