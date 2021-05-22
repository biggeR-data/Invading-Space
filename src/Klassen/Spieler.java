package Klassen;

public class Spieler {

    private String name;
    private int punkte;

    public Spieler(String name) throws DelimException, EmptyException {
        if ("".equals(name) || name.equals(null)) {
            throw new EmptyException("Der Spielername darf nicht leer sein.");
        } else if (name.contains(",")) {
            throw new DelimException("Der Spielername darf kein Komma beinhalten.");
        } else {
            this.name = name;
        }
    }

    public void setzePunkte(int punkte) {
        this.punkte = punkte;
    }

    public int erhaltePunkte() {
        return punkte;
    }

    public String erhalteName() {
        return name;
    }

    /**
     * korrekte Formatierung für csv Speicherart in Spielerdaten Textdatei
     *
     * @return String
     */
    public String zuString() {
        return name + "," + punkte;
    }
}
