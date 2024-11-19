package src.database;

import java.sql.*;

public class MySQLConnection {
    // URL do banco de dados
    private static final String URL = "jdbc:mysql://localhost:3306/projeto1";
    private static final String USER = "root"; // Utilizador do banco
    private static final String PASSWORD = "V1n1c1us"; // Senha do banco

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Conectar ao banco de dados
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão bem-sucedida!");

            String getAllProductsQuery = "SELECT * FROM produtos";
            Statement cursor = connection.createStatement();

            ResultSet productsResultSet = cursor.executeQuery(getAllProductsQuery);

            while(productsResultSet.next()){
                int productId = productsResultSet.getInt("idProduto");
                String productName = productsResultSet.getString("nome");
                String productDescription = productsResultSet.getString("descricao");
                String productType = productsResultSet.getString("tipo");

                System.out.println(
                        "ID PRODUTO: " + productId + "\nNOME: " + productName + "\n" + "DESCRIÇÃO DO PRODUTO: "
                                + productDescription + "\nTIPO: " + productType + "\n"
                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro na conexão com o banco de dados.");
        } finally {
            // Fechar a conexão
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("Conexão fechada.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

