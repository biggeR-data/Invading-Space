package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Feste Größe des Spielfensters: 600 * 768 Pixel
public class MainGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Startbildschirm.fxml"));
            Parent root = loader.load();
            Startbildschirmcontroller startController = loader.getController();
            startController.setzeHighscoreSpieler();
            primaryStage.setTitle("Space Invader - aber besser");
            Scene startScreen = new Scene(root);
            primaryStage.setScene(startScreen);
            primaryStage.setResizable(false);
            primaryStage.show();
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
        try {
            Spielbildschirmcontroller.getSpielThread().stop();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
