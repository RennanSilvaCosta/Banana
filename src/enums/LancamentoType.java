package enums;

public enum LancamentoType {

    RECEITA(1, "Receita"), DESPESA(2, "Despesa");


    private int cod;
    private String description;

    private LancamentoType(int cod, String description){
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static LancamentoType toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (LancamentoType x : LancamentoType.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("ID inválido: " + cod);
    }
}
