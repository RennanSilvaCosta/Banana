package model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private Double saldo;

    @OneToMany(mappedBy = "user")
    private List<Lancamento> lancamentos;

    public Usuario() {
    }

    public Usuario(long id, String nome, String email, Double saldo, List<Lancamento> lancamentos) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.saldo = saldo;
        this.lancamentos = lancamentos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }
}
