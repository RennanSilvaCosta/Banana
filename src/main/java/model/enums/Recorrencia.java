package model.enums;

public enum Recorrencia {

    MENSAL("Mensal"),
    ANUAL("Anual"),
    FIXA("Fixa");

    private String descricao;

    private Recorrencia(String desc) {
        this.descricao = desc;
    }

    public String getDescricao() {
        return descricao;
    }
}
