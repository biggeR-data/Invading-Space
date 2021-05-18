package Klassen;

import GUI.Maingui;

public class TestGame {
    public static void main(String[] args) {
        Game game = new Game(new Maingui(),new Spieler("asdfö",2),0);
        game.start();
    }
}
