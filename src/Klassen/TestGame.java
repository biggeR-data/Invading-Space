package Klassen;

import GUI.Maingui;
import GUI.Spielbildschirmcontroller;

public class TestGame {
    public static void main(String[] args) {
        Game game = new Game(new Spielbildschirmcontroller(),new Spieler("Jan",2),0);
        game.start();
    }
}
