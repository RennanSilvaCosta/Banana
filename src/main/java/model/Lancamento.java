package model;

import model.enums.Recorrencia;
import model.enums.StatusPagamento;
import model.enums.TipoLancamento;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String titulo;

    @Column(length = 100, nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Double valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento statusPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoLancamento tipoLancamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Recorrencia recorrencia;

    @Column(nullable = false)
    private LocalDate data;

    private LocalDate dataVencimento;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user;

    @OneToMany(mappedBy = "lancamento")
    private List<Parcela> parcelas;

    public Lancamento() {
    }

    public Lancamento(long id, String titulo, String descricao, Double valor, StatusPagamento statusPagamento, TipoLancamento tipoLancamento, Recorrencia recorrencia, LocalDate data, LocalDate dataVencimento, Usuario user, List<Parcela> parcelas) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.valor = valor;
        this.statusPagamento = statusPagamento;
        this.tipoLancamento = tipoLancamento;
        this.recorrencia = recorrencia;
        this.data = data;
        this.dataVencimento = dataVencimento;
        this.user = user;
        this.parcelas = parcelas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public Recorrencia getRecorrencia() {
        return recorrencia;
    }

    public void setRecorrencia(Recorrencia recorrencia) {
        this.recorrencia = recorrencia;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

}
