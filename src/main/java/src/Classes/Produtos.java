package src.Classes;

public class Produtos {
    protected int idProduto;
    protected String nome;
    protected String descricao;
    protected String tipo;

    public Produtos( int idProduto, String nome, String descricao, String tipo ){
        this.idProduto = idProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
    }
}
