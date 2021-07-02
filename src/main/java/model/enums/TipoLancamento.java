package model.enums;

public enum TipoLancamento {

    D("Despesa"),
    R("Receita");

    private String descricao;

    private TipoLancamento(String desc) {
        this.descricao = desc;
    }

    public String getDescricao() {
        return descricao;
    }
}
