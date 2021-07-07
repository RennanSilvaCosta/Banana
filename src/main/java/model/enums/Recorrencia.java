package model.enums;

public enum Recorrencia {

    MENSAL("Mensal"),
    ANUAL("Anual"),
    FIXA("Fixa"),
    SEM_RECORRENCIA("Sem recorrencia");

    private String descricao;

    private Recorrencia(String desc) {
        this.descricao = desc;
    }

    public String getDescricao() {
        return descricao;
    }
}
