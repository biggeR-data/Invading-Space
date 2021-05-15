package Klassen;

public class Spieler {

    private String name;
    private int punkte;

    public Spieler(String name) {
        this.name = name;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public int getPunkte() {
        return punkte;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "," + punkte;
    }
}
