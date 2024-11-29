package src.Classes;

import java.util.Date;

public class Compras {
    protected int idCliente;
    protected int idProduto;
    protected int quantidade;
    protected Date data;

    public void Compras(int idCliente, int idProduto, int quantidade, Date data){
        this.idCliente = idCliente;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.data = data;
    }
}
