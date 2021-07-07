package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "valor", nullable = false)
    private Double valorParcela;

    @Column(nullable = false)
    private LocalDate data;

    private LocalDate dateVencimento;

    @ManyToOne
    @JoinColumn(name = "lancmento_id")
    private Lancamento lancamento;

    public Parcela() {
    }

    public Parcela(long id, Double valorParcela, LocalDate data, LocalDate dateVencimento, Lancamento lancamento) {
        this.id = id;
        this.valorParcela = valorParcela;
        this.data = data;
        this.dateVencimento = dateVencimento;
        this.lancamento = lancamento;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(Double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDate getDateVencimento() {
        return dateVencimento;
    }

    public void setDateVencimento(LocalDate dateVencimento) {
        this.dateVencimento = dateVencimento;
    }

    public Lancamento getLancamento() {
        return lancamento;
    }

}
