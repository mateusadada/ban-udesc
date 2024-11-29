package src.Classes;

import src.database.MySQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Bebidas extends Produtos{
    protected float volume;
    protected boolean temCafeina;

    // Constructor for "bebidas"
    public Bebidas( int idProduto, float volume, boolean temCafeina ){
        // super.SetProduto(nome, descricao, tipo, siglaUnidadeMed);
        this.setIdProduto(idProduto);
        this.setVolume(volume);
        this.setTemCafeina(temCafeina);
    }

    // Insert the product and after that get the return ID and then insert into the Bebidas database using that id as FK
    public void insertIntoDb(Produtos novoProduto, float volume, boolean temCafeina) {
        MySQLConnection db = new MySQLConnection();
        String insertProdutoQuery = "INSERT INTO produtos(nome, descricao, tipo, siglaUnidadeMed) VALUES(?, ?, ?, ?)";
        String insertBebidaQuery = "INSERT INTO bebidas(idProduto, volume, temCafeina) VALUES(?, ?, ?)";

        try {
            // Inserting product
            PreparedStatement produtoStmt = db.connection.prepareStatement(insertProdutoQuery, Statement.RETURN_GENERATED_KEYS);
            produtoStmt.setString(1, novoProduto.getNome());
            produtoStmt.setString(2, novoProduto.getDescricao());
            produtoStmt.setString(3, novoProduto.getTipo());
            produtoStmt.setString(4, novoProduto.getSiglaUnidadeMed());
            produtoStmt.executeUpdate();

            // Getting the product ID generated
            ResultSet generatedKeys = produtoStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idProduto = generatedKeys.getInt(1);

                // Inserting details about the drink in table Bebidas
                PreparedStatement bebidaStmt = db.connection.prepareStatement(insertBebidaQuery);
                bebidaStmt.setInt(1, idProduto);
                bebidaStmt.setFloat(2, volume);
                bebidaStmt.setBoolean(3, temCafeina);
                bebidaStmt.executeUpdate();

                System.out.println("Bebida inserida com sucesso!");
            } else {
                throw new SQLException("Falha ao inserir produto, ID não gerado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir bebida ao Banco de Dados: " + e.getMessage());
        }
    }


    @Override
    public String toString() {
        return "Bebidas{" +
                "volume=" + volume +
                ", temCafeina=" + temCafeina +
                '}';
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public boolean getTemCafeina() {
        return temCafeina;
    }

    public void setTemCafeina(boolean temCafeina) {
        this.temCafeina = temCafeina;
    }
}
