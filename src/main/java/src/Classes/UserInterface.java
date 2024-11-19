package src.Classes;
import java.util.Scanner;

public class UserInterface {
    public int option;

    public UserInterface(){
        this.showMenu();
    }

    public void showMenu(){
        System.out.println("===== BANCO DE DADOS =====");
        System.out.println("1- Adicionar novo produto");
        System.out.println("2- Listar produtos");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a opção desejada: ");
        this.option = scanner.nextInt();
    }
}

