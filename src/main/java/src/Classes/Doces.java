package src.Classes;

import src.database.MySQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Doces extends Produtos {
    protected float peso;
    protected String origem;

    public Doces(float peso, String origem){
        // super.SetProduto(nome, descricao, tipo, siglaUnidadeMed);
        // this.setIdProduto(idProduto);
        this.setPeso(peso);
        this.setOrigem(origem);
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public void insertIntoDb(Produtos novoProduto){
        String queryInsertNovoProduto = "INSERT INTO produtos(nome, descricao, tipo, siglaUnidadeMed) VALUES(?,?,?,?)";
        String queryInsertNovoDoce = "INSERT INTO doces(idProduto, peso, origem) VALUES(?,?,?)";

        try{
            var db = new MySQLConnection();
            PreparedStatement produtoStmt = db.connect().prepareStatement(queryInsertNovoProduto, Statement.RETURN_GENERATED_KEYS);
            produtoStmt.setString(1, novoProduto.getNome());
            produtoStmt.setString(2, novoProduto.getDescricao());
            produtoStmt.setString(3, novoProduto.getTipo());
            produtoStmt.setString(4, novoProduto.getSiglaUnidadeMed());

            produtoStmt.executeUpdate();

            ResultSet generatedKeys = produtoStmt.getGeneratedKeys();

            if(generatedKeys.next()){
                int idProduto = generatedKeys.getInt(1);

                PreparedStatement doceStmt = db.connect().prepareStatement(queryInsertNovoDoce);
                doceStmt.setInt(1, idProduto);
                doceStmt.setFloat(2, this.getPeso());
                doceStmt.setString(3, this.getOrigem());

                doceStmt.executeUpdate();

                System.out.println("Doce inserido ao Banco de Dados com sucesso!");
            } else{
                throw new SQLException("Falha ao inserir produto, ID não gerado.");
            }

        } catch(Exception e){
            System.out.println("Não foi possível inserir o doce no banco de dados. Erro: " + e);
        }

    }
}

