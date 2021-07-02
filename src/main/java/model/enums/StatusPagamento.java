package model.enums;

public enum StatusPagamento {

    P("Pago"),
    NP("Não pago");

    private String descricao;

    private StatusPagamento(String desc) {
        this.descricao = desc;
    }

    public String getDescricao() {
        return descricao;
    }
}
