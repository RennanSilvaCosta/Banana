package model;

import java.io.Serializable;

public class User implements Serializable {

    private Integer idUsuario;
    private String name;
    private String email;

    public User() {
    }

    public User(Integer idUsuario, String name, String email) {
        this.idUsuario = idUsuario;
        this.name = name;
        this.email = email;
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

}
