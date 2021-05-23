package Klassen.Handling;

import Klassen.Entities.*;
import Klassen.GUI.Spielbildschirmcontroller;
import javafx.application.Platform;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Die Klasse Spielablauf wird als Thread gestartet und läuft parallel zu der GUI.
 */
public class Spielablauf extends Thread {

    // Zugriff auf abhängige Elemente
    private Group root;
    private Spielbildschirmcontroller gui;
    private ObjektSteuerung objektSteuerung = new ObjektSteuerung();

    // Beweglichen Objekte
    private ArrayList<Gegner> listeGegner = new ArrayList<Gegner>();
    private Raumschiff raumschiff;

    // Spielverlauf
    private int welle = 1;
    private boolean gameover = false;

    // Zeit in Ticks
    private int zaehlerTicks = 0;
    private int gegnerGeschwindigkeit;

    // Zeit in Millisekunden
    // Raumschiff
    private long schussGeschwindigkeitSchiff;
    private long lastSchiffSchussMillis = 0;
    // Gegner
    private long naechsterSchussGegner;
    private long lastGegnerSchussMillis = 0;
    // Spielgeschwindigkeit
    private long lastTickMillis = 0;
    private int timePerTick = 40;

    private Map<String, Long> naechsterSchussZeitspanne = new HashMap<String, Long>();

    private boolean schussLoesenRaumschiff = false;

    /**
     * Anpassung an ausgewählten Modus
     *
     * @param modus ausgewählter Modus (Normal = 0; Hölle = 1)
     * @param gui   Spielbildschirmcontroller
     * @param root  Group der aktuellen Scene
     */
    public Spielablauf(int modus, Spielbildschirmcontroller gui, Group root) {
        // Zugriff auf GUI
        this.root = root;
        this.gui = gui;

        switch (modus) {
            case 0:
                // normal
                schussGeschwindigkeitSchiff = 750;
                gegnerGeschwindigkeit = 35;
                naechsterSchussGegner = 2000;
                naechsterSchussZeitspanne.put("min", 1600L);
                naechsterSchussZeitspanne.put("max", 2500L);
                break;

            case 1:
                // hoelle
                schussGeschwindigkeitSchiff = 700;
                gegnerGeschwindigkeit = 10;
                naechsterSchussGegner = 600;
                naechsterSchussZeitspanne.put("min", 700L);
                naechsterSchussZeitspanne.put("max", 1100L);
                break;
        }
    }

    /**
     * startet das Spiel und steuert den Spielablauf bis zum Gameover
     */
    @Override
    public void run() {
        // Zurücksetzen des Zählers
        lastTickMillis = System.currentTimeMillis();
        lastGegnerSchussMillis = System.currentTimeMillis();
        lastSchiffSchussMillis = System.currentTimeMillis();

        // Raumschiff und Monster erzeugen
        raumschiff = new Raumschiff(280, 638, root);
        gegnerGenerieren();

        // Spiele bis Gameover
        while (!gameover) {
            // Fixierung der zeitlichen Abstände zwischen den Ticks
            while ((lastTickMillis + timePerTick) >= System.currentTimeMillis()) {
                try {
                    this.sleep(0, 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            tick();

            lastTickMillis = System.currentTimeMillis();
        }

        gameover();
    }

    /**
     * Berechnung des nächsten Ticks
     */
    private void tick() {
        zaehlerTicks++;

        // Bewegung pro Tick
        bewegeSchuesse();

        // aktuelle Punkte auf der GUI ausgeben
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gui.setzePunktzahl(objektSteuerung.erhalteScore());
            }
        });

        // Wenn nötig Schuss lösen
        if (schussLoesenRaumschiff) {
            raumschiffSchiessen();
            schussLoesenRaumschiff = !schussLoesenRaumschiff;
        }

        if (lastGegnerSchussMillis + naechsterSchussGegner <= System.currentTimeMillis()) {
            gegnerSchiessen();
        }

        // Gegner mit eingestellter Geschwindigkeit bewegen
        if (zaehlerTicks % gegnerGeschwindigkeit == 0) {
            bewegeGegner();
        }

        // Veränderungen bei neuer Welle
        if (objektSteuerung.neueWelleNotwendig()) {
            welle++;

            // Popup einblenden
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.setzePopup("Welle " + welle + " erreicht!");
                }
            });

            // Dauer des Popups
            try {
                this.sleep(2000);
            } catch (InterruptedException ioEx) {
                ioEx.printStackTrace();
            }

            // Popup ausblenden
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.entfernePopup();
                }
            });

            gegnerGenerieren();

            // Warten während der Monster Generierung
            try {
                this.sleep(0, 500);
            } catch (InterruptedException ioEx) {
                ioEx.printStackTrace();
            }

            // Geschwindigkeit der Gegner erhöhen
            if (gegnerGeschwindigkeit > 8) {
                gegnerGeschwindigkeit -= 5;
            }
            if(naechsterSchussZeitspanne.get("min")>400) {
                naechsterSchussZeitspanne.put("min", naechsterSchussZeitspanne.get("min") - 300);
                naechsterSchussZeitspanne.put("max", naechsterSchussZeitspanne.get("max") - 300);
            }
        }
    }

    /**
     * bewege alle Schüsse von den Gegnern und dem Schiff -> Überprüfe Gameover
     */
    private void bewegeSchuesse() {
        // Schüsse vom Raumschiff bewegen
        objektSteuerung.bewegeSchussRaumschiff();

        // Schüsse von den Gegnern bewegen und gameover überprüfen
        if (objektSteuerung.bewegeSchussGegner(raumschiff)) {
            gameover = true;
        }
    }

    /**
     * Bewege die Gegner und überprüfe Gameover
     */
    private void bewegeGegner() {
        objektSteuerung.bewegeGegner();

        // überprüfe gameover
        if (objektSteuerung.gameOver()) {
            gameover = true;
        }
    }

    /**
     * lässt ein Gegner, abhängig von der Position des Raumschiffes, schießen
     * erzeugt eine zufällig Zeit (in einem Zeitrahmen) bis zum nächsten Schuss
     */
    private void gegnerSchiessen() {
        objektSteuerung.schiessenGegner(raumschiff.xKoor);
        naechsterSchussGegner = (long) (Math.random() * (naechsterSchussZeitspanne.get("max") - naechsterSchussZeitspanne.get("min")) + naechsterSchussZeitspanne.get("min"));
        lastGegnerSchussMillis = System.currentTimeMillis();
    }

    /**
     * lässt das Raumschiff schiessen
     */
    private void raumschiffSchiessen() {
        objektSteuerung.hinzufuegenSchussRaumschiff(raumschiff.schiessen());
    }

    /**
     * Initialisiert Ende des Spiels
     */
    private void gameover() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gui.wechselZuEndbildschirm();
            }
        });
    }

    /**
     * generiert alle Gegner neu
     */
    private void gegnerGenerieren() {
        // alte Objekte entfernen
        listeGegner.removeAll(listeGegner);

        // 50er Gegner
        for (int x = 0; x < 12; x++) {
            listeGegner.add(new GegnerFuenfzig(x * 40 + 30, 100, root));
        }

        // 20er Gegner
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 12; x++) {
                listeGegner.add(new GegnerZwanzig(x * 40 + 30, y * 50 + 150, root));
            }
        }

        // 10er Gegner
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 12; x++) {
                listeGegner.add(new GegnerZehn(x * 40 + 30, y * 50 + 250, root));
            }
        }

        objektSteuerung.neueWelle(listeGegner);
    }

    // Methoden für die GUI

    /**
     * Setzt bei Aufruf, wenn ausreichend Zeit vergangen, den Schuss für den nächsten Tick an
     */
    public void tasteHoch() {
        if ((lastSchiffSchussMillis + schussGeschwindigkeitSchiff) <= System.currentTimeMillis()) {
            schussLoesenRaumschiff = true;
            lastSchiffSchussMillis = System.currentTimeMillis();
        }
    }

    /**
     * bewegt das Raumschiff nach links
     */
    public void tasteLinks() {
        if (!raumschiff.pruefeKollisionLinks(objektSteuerung.erhalteRandLinks() - 5)) {
            raumschiff.bewegenLinks();
        }
    }

    /**
     * bewegt das Raumschiff nach rechts
     */
    public void tasteRechts() {
        if (!raumschiff.pruefeKollisionRechts(objektSteuerung.erhalteRandRechts() - 5)) {
            raumschiff.bewegenRechts();
        }
    }

}