package Klassen;

public class Spieler {

    private String name;
    private int punkte;

    public Spieler(String name) throws DelimException {
        if (name.contains(",")) {
            throw new DelimException("Der Spielername darf kein Komma beinhalten.");
        } else {
            this.name = name;
        }
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
