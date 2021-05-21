package Klassen;

import GUI.Maingui;
import GUI.Spielbildschirmcontroller;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.Collection;

// todo: Stages? GameOver? Score speichern?

public class Game extends Thread{
    // Attribute
    private Group root;

    private long zeahlerTakt = 0;
    private int monsterGeschwindigkeit; // in millisekunden
    private int schussGeschwindigkeit; // in millisekunden

    private int score;
    private boolean gameover = false;
    private boolean schussloesen = false;

    private Koordinator koordinator = new Koordinator();
    private Spielbildschirmcontroller gui;
    private Spieler spieler;
    private ArrayList<Monster> listMonster = new ArrayList<Monster>();
    private ArrayList<Schuss> listSchuesse = new ArrayList<Schuss>();
    private Raumschiff schiff;

    private long lastSchussMillis = 0; // in millisekunden
    private long lastSchiffSchussMillis = 0;
    private long lastTickMillis = 0; // in millisekunden
    private int timePerTick = 50; // in millisekunden

    public Game(Spielbildschirmcontroller gui, int mode, Group root){
        this.root = root;
        this.spieler = spieler;
        this.gui = gui;

        switch(mode){
            case 0:
                // normal
                schussGeschwindigkeit = 10;
                monsterGeschwindigkeit = 20;
                break;
            case 1:
                // schnell
                schussGeschwindigkeit = 10;
                monsterGeschwindigkeit = 5;
                break;
        }
    }

    // run
    @Override
    public void run(){
        monsterGenerieren();
        lastTickMillis = System.currentTimeMillis();
        schiff = new Raumschiff(280,638, root);
        // spiele bis gameover
        while(!gameover){

            // warte bis Zeit vergangen
            while((lastTickMillis + timePerTick) >= System.currentTimeMillis()){
                try {
                    this.sleep(0,100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // mache nächsten tick
            System.out.println("neuer tick");
            tick();
            lastTickMillis = System.currentTimeMillis();
        }

        gameover();
    }

    // gametick
    private void tick(){
        zeahlerTakt ++;

        // jeder Takt
        bewegeSchuesse();
        checkMonsterGetroffen();

        // anzeigen aktualisieren
        System.out.println("Punktzahl");
        Platform.runLater(new Runnable() {
            @Override public void run() {
                gui.setztePunktzahl(score++);
            }
        });


        // loese neuen Schuss

        if(schussloesen){
            loeseNeuenSchuss();
        }

        // nicht jeden Takt ausführen (bei Monster beschleunigung MonsterGeschwindigkeit ändern)
        if(zeahlerTakt % monsterGeschwindigkeit == 0){
            bewegeMonster();
        }

    }

    // private Operationen
    private void checkMonsterGetroffen(){
        System.out.println("checke Monster getroffen");
        // wenn Monster getroffen - entfernen
        // Punktzahl aktualisieren
    }

    private void bewegeSchuesse(){
        System.out.println("bewege Schüsse");
        koordinator.ueberpruefenUndBewegenSchuss();
    }

    private void bewegeMonster(){
        System.out.println("bewege Monster");
        koordinator.ueberprüfenUndBewegenMonster();
        gameover = koordinator.gameOver();
    }

    private void loeseNeuenSchuss() {
        System.out.println("schieße");
        koordinator.hinzufuegenSchuss(schiff.schiessen());
    }

    private void gameover(){
        System.out.println("Game over!");
        Platform.runLater(new Runnable() {
            @Override public void run() {
                gui.wechselZuGameover();
            }
        });
    }

    // key events
    public void keyUp(){
        if((lastSchiffSchussMillis + schussGeschwindigkeit) >= System.currentTimeMillis()){
            schussloesen = true;
            System.out.println("schuss freigegeben");
        }
    }

    public void keyLeft(){
        if (!schiff.pruefeKollisionLinks(koordinator.erhalteRandLinks() - 5)) {
            schiff.bewegenLinks();
        }
    }

    public void keyRight(){
        if (!schiff.pruefeKollisionRechts(koordinator.erhalteRandRechts())) {
            schiff.bewegenRechts();
        }
    }

    public void monsterGenerieren(){
        listMonster.removeAll(listMonster);
        // 50er Monster
        for(int x = 0;x< 12;x++){
            listMonster.add(new MonsterFuenfzig(x * 40 + 30, 100, root));
        }
        // 20er Monster
        for(int y = 0;y< 2;y++){
            for (int x = 0; x < 12; x++) {
                listMonster.add(new MonsterZwanzig(x * 40 + 30, y * 50 + 150, root));
            }
        }
        // 10er Monster
        for(int y = 0;y<2;y++){
            for (int x = 0; x < 12; x++) {
                listMonster.add(new MonsterZehn(x * 40 + 30, y * 50 + 250, root));
            }
        }



        koordinator.neueMonsterListeUebergeben(listMonster);

    }
}
