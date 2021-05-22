package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Startpunkt der Gui-Applikation
     * Auflösung: 600 * 768 Pixel
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Lade Hierarchie aus der FMXL
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Startbildschirm.fxml"));
            Parent root = loader.load();

            // Übergäbe an den Startbildschirmcontroller
            Startbildschirmcontroller startController = loader.getController();
            startController.aktiviereStartbildschirm();

            // Layout & Struktur Definition
            primaryStage.setTitle("Space Invader - aber besser");
            Scene startScreen = new Scene(root);
            primaryStage.setScene(startScreen);
            primaryStage.setResizable(false);
            primaryStage.show();

            // Beendigung durch rotes X
            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                beenden();
                primaryStage.close();
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void beenden() {
        if (Spielbildschirmcontroller.erhalteSpielThread() != null) {
            Spielbildschirmcontroller.erhalteSpielThread().stop();
        }
    }

}
