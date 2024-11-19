package src.Classes;

public class Doces extends Produtos {
    protected float peso;
    protected String origem;

    public Doces(int idProduto, String nome, String descricao, String tipo, float peso, String origem){
        super(idProduto, nome, descricao, tipo);
        this.peso = peso;
        this.origem = origem;
    }
}

