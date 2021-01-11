package enums;

public enum Recorrencia {

    ANUAL(1, "Anual"), SEMESTRAL(2, "Semestral"), TRIMESTRAL(3, "Trimestral"), BIMESTRAL(4, "Bimestral"),
    MENSAL(5, "Mensal"), QUINZENAL(6, "Quinzenal"), SEMANAL(7, "Semanal"), DIARIO(8, "Diario") ;

    private int cod;
    private String descricao;

    private Recorrencia(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static Recorrencia toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (Recorrencia x : Recorrencia.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("ID inválido: " + cod);
    }
}
