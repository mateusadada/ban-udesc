package src;

import src.Classes.UserInterface;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.showMenu();
        ui.inputScanner.close();
    }
}