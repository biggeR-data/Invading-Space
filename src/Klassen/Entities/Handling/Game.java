package Klassen.Entities.Handling;

import Klassen.GUI.Spielbildschirmcontroller;
import Klassen.Scores.Spieler;
import javafx.application.Platform;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// todo: Stages? GameOver? Score speichern?

public class Game extends Thread {
    // Attribute
    private Group root;

    private long zeahlerTakt = 0;
    private int monsterGeschwindigkeit; // in ticks
    private long schussGeschwindigkeitSchiff; // in millisekunden
    private long schussGeschwindigkeitMonster; // in millisekungen

    // Range für Random Schussgeschwindikeit der Gegner
    private Map<String, Long> rangeSchussGeschwindigkeitMonster = new HashMap<String, Long>();

    private int stage = 1;
    private boolean gameover = false;
    private boolean schussLoesenSchiff = false;

    private Koordinator koordinator = new Koordinator();
    private Spielbildschirmcontroller gui;
    private Spieler spieler;
    private ArrayList<Gegner> listGegner = new ArrayList<Gegner>();
    private Raumschiff schiff;

    private long lastSchiffSchussMillis = 0; // in millisekunden
    private long lastMonsterSchussMillis = 0; // in millisekunden
    private long lastTickMillis = 0; // in millisekunden
    private int timePerTick = 50; // in millisekunden

    public Game(Spielbildschirmcontroller gui, int mode, Group root) {
        this.root = root;
        this.spieler = spieler;
        this.gui = gui;

        switch (mode) {
            case 0:
                // normal
                schussGeschwindigkeitSchiff = 850;
                monsterGeschwindigkeit = 20;
                schussGeschwindigkeitMonster = 800;
                rangeSchussGeschwindigkeitMonster.put("min", 1000L);
                rangeSchussGeschwindigkeitMonster.put("max", 1800L);
                break;
            case 1:
                // schnell
                schussGeschwindigkeitSchiff = 850;
                monsterGeschwindigkeit = 4;
                schussGeschwindigkeitMonster = 600;
                rangeSchussGeschwindigkeitMonster.put("min", 700L);
                rangeSchussGeschwindigkeitMonster.put("max", 1200L);
                break;
        }
    }

    // run
    @Override
    public void run() {
        monsterGenerieren();
        lastTickMillis = System.currentTimeMillis();
        lastMonsterSchussMillis = System.currentTimeMillis();
        lastSchiffSchussMillis = System.currentTimeMillis();
        schiff = new Raumschiff(280, 638, root);
        // spiele bis gameover
        while (!gameover) {

            // warte bis Zeit vergangen
            while ((lastTickMillis + timePerTick) >= System.currentTimeMillis()) {
                try {
                    this.sleep(0, 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // mache nächsten tick
            //System.out.println("neuer tick");
            tick();
            lastTickMillis = System.currentTimeMillis();
        }

        gameover();
    }

    // gametick
    private void tick() {
        zeahlerTakt++;

        // jeder Takt
        bewegeSchuesse();

        // update score
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gui.setztePunktzahl(koordinator.erhalteScore());
            }
        });

        // Schiff loese neuen Schuss
        if (schussLoesenSchiff) {
            loeseNeuenSchuss();
            schussLoesenSchiff = !schussLoesenSchiff;
        }

        // Gegner schuss loesen
        if (lastMonsterSchussMillis + schussGeschwindigkeitMonster <= System.currentTimeMillis()) {
            //System.out.println("Gegner schießt");
            koordinator.schiessenMonster(schiff.xKoor);
            schussGeschwindigkeitMonster = (long) (Math.random() * (rangeSchussGeschwindigkeitMonster.get("max") - rangeSchussGeschwindigkeitMonster.get("min")) + rangeSchussGeschwindigkeitMonster.get("min"));
            //System.out.println("Nächster Schuss:" + schussGeschwindigkeitMonster);
            lastMonsterSchussMillis = System.currentTimeMillis();
        }

        // nicht jeden Takt ausführen (bei Gegner beschleunigung MonsterGeschwindigkeit ändern)
        if (zeahlerTakt % monsterGeschwindigkeit == 0) {
            bewegeMonster();
        }

        // neue Welle notwendig?
        if (koordinator.neueMonsterListeNotwendig()) {
            // stage erhöhen
            stage++;
            //System.out.println("Neue Stage erreicht: " + stage);

            // text ausgeben
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.setztePopup("Welle " + stage + " erreicht!");
                }
            });
            try {
                this.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gui.entfernePopup();
                }
            });

            // monster generieren
            monsterGenerieren();
            try {
                this.sleep(0, 500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // geschwindigkeit erhöhen
            if (monsterGeschwindigkeit > 8) {
                monsterGeschwindigkeit -= 2;
            }
        }

    }

    // private Operationen
    private void bewegeSchuesse() {
        //System.out.println("bewege Schüsse");
        // Schüsse vom Schiff
        koordinator.ueberpruefenMonsterUndBewegeSchuss();


        // Schüsse von den Monstern bewegen und gameover überprüfen
        if (koordinator.ueberpruefenRaumschiffUndBewegeSchuss(schiff)) {
            gameover = true;
        }
    }

    private void bewegeMonster() {
        //System.out.println("bewege Gegner");
        koordinator.ueberpruefenUndBewegenMonster();

        // überprüfe gameover
        if (koordinator.gameOver()) {
            gameover = true;
        }
    }

    private void loeseNeuenSchuss() {
        //System.out.println("schieße");
        koordinator.hinzufuegenSchussRaumschiff(schiff.schiessen());
    }

    private void gameover() {
        //System.out.println("Game over!");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gui.wechselZuEndbildschirm();
            }
        });
    }

    // key events
    public void keyUp() {
        //System.out.println("key up");
        if ((lastSchiffSchussMillis + schussGeschwindigkeitSchiff) <= System.currentTimeMillis()) {
            schussLoesenSchiff = true;
            //System.out.println("schuss freigegeben");
            lastSchiffSchussMillis = System.currentTimeMillis();
        }
    }

    public void keyLeft() {
        if (!schiff.pruefeKollisionLinks(koordinator.erhalteRandLinks() - 5)) {
            schiff.bewegenLinks();
        }
    }

    public void keyRight() {
        if (!schiff.pruefeKollisionRechts(koordinator.erhalteRandRechts())) {
            schiff.bewegenRechts();
        }
    }

    public void monsterGenerieren() {
        listGegner.removeAll(listGegner);
        // 50er Gegner
        for (int x = 0; x < 12; x++) {
            listGegner.add(new GegnerFuenfzig(x * 40 + 30, 100, root));
        }

        // 20er Gegner
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 12; x++) {
                listGegner.add(new GegnerZwanzig(x * 40 + 30, y * 50 + 150, root));
            }
        }

        // 10er Gegner
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 12; x++) {
                listGegner.add(new GegnerZehn(x * 40 + 30, y * 50 + 250, root));
            }
        }

        koordinator.neueMonsterListeUebergeben(listGegner);
    }
}
