package src.Classes;

import src.database.MySQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Clientes {
    protected int idCliente;
    protected String nome;
    protected Date clienteDesde;

    @Override
    public String toString() {
        return "Clientes{" +
                "clienteDesde=" + clienteDesde +
                ", nome='" + nome + '\'' +
                ", idCliente=" + idCliente +
                '}';
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getClienteDesde() {
        return clienteDesde;
    }

    public void setClienteDesde(Date clienteDesde) {
        this.clienteDesde = clienteDesde;
    }

    public ArrayList<Clientes> get(){
        String queryGetClientes = "SELECT * FROM clientes";
        ArrayList<Clientes> clientesList = new ArrayList<>();

        var db = new MySQLConnection();

        try(Statement cursor = db.connect().createStatement()){
        ResultSet dbReturnedData = cursor.executeQuery(queryGetClientes);

        while(dbReturnedData.next()){
            int idCliente = dbReturnedData.getInt(1);
            Date clienteDesde = dbReturnedData.getDate(2);
            String nome = dbReturnedData.getString(3);
            Clientes cliente = new Clientes();
            cliente.setIdCliente(idCliente);
            cliente.setClienteDesde(clienteDesde);
            cliente.setNome(nome);

            clientesList.add(cliente);
        }

        } catch(SQLException e){
            System.out.println("Erro ao buscar clientes. Erro: " + e);
        }

        return(clientesList);
    }

    public void update(String newNome, int clientId) throws SQLException {
        MySQLConnection db = new MySQLConnection();
        String updateNameQuery = "UPDATE clientes SET nome=? WHERE id=?";

        try{
            PreparedStatement clienteStmt = db.connection.prepareStatement(updateNameQuery);
            clienteStmt.setString(1, newNome);
            clienteStmt.setInt(2, clientId);

            int rowsAffected = clienteStmt.executeUpdate();
            if(rowsAffected >= 1){
                System.out.println("Cliente atualizado com sucesso!");
            } else{
                System.out.println("Nenhum cliente encontrado com o nome fornecido.");
            }

        } catch(Exception e){
            System.out.println("Erro ao seu conectar ao banco de dados para atualização de cliente.");
        } finally {
            try{
                if(db.connection != null && !db.connection.isClosed()){
                    db.connection.close();
                }
            } catch (Exception e){
                System.out.println("Erro ao encerrar conexão com o banco de dados.");
            }
        }
    }

    public void delete(int clientId){
        String deleteQuery = "DELETE FROM clientes WHERE id = ?";
        String deleteQueryCompras = "DELETE FROM compra WHERE idCliente = ?";
        var db = new MySQLConnection();

        try{
            var clienteStmt = db.connection.prepareStatement(deleteQuery);
            var comprasStmt = db.connection.prepareStatement(deleteQueryCompras);

            comprasStmt.setInt(1, clientId);
            clienteStmt.setInt(1, clientId);

            var rowsAffectedCompras = comprasStmt.executeUpdate();
            var rowsAffected = clienteStmt.executeUpdate();
            if(rowsAffected >= 1 && rowsAffectedCompras >= 1){
                System.out.println("Cliente deletado com sucesso!");
            } else{
                System.out.println("Nenhum cliente encontrado com o ID fornecido.");
            }

        } catch (SQLException e){
            System.out.println("Erro ao deletar o produto: " + clientId + " - Erro: " + e);
        }
    }

    public void create(String name, Date clientSince){
        MySQLConnection db = new MySQLConnection();
        String insertQuery = "INSERT INTO clientes(nome, clienteDesde) VALUES (?,?)";

        int year = clientSince.getYear() + 1900;
        int month = clientSince.getMonth() + 1;
        int day = clientSince.getDate();

        String preparedDate = year + "-" +
                (month < 10 ? "0" + month : month) + "-" +
                (day < 10 ? "0" + day : day);

        try{
           PreparedStatement clientStmt = db.connection.prepareStatement(insertQuery);
           clientStmt.setString(1, name);
           clientStmt.setString(2, preparedDate);

           var rowsAffected = clientStmt.executeUpdate();
           if(rowsAffected >= 1){
               System.out.println("Cliente inserido com sucesso!");
           }

        } catch (SQLException e){
            System.out.println("Erro ao inserir o cliente no banco de dados. Erro: " + e);
        }
    }
}
