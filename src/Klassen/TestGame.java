package Klassen;

import GUI.Main;

public class TestGame {
    public static void main(String[] args) {
        Game game = new Game(new Main(),new Spieler(),0);
        game.start();
    }
}
