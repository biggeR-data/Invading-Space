package Klassen;

import GUI.Maingui;

public class TestGame {
    public static void main(String[] args) {
        //Überschrieben da sich der Spielerkonstruktor geändert hat L.G Leon
        try {
            Game game = new Game(new Maingui(),new Spieler("asdfjö"),0);
            game.start();
        } catch (DelimException ex){
            ex.getMessage();
        }

    }
}
