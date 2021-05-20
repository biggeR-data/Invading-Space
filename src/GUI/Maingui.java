package GUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

// 600 * 768
public class Maingui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Startbildschirm.fxml"));
            Parent root = loader.load();
            Startbildschirmcontroller startcontroller = loader.getController();
            startcontroller.setzeHighscorespieler();
            primaryStage.setTitle("Space Invader - aber besser");
            Scene startscreen = new Scene(root);
            primaryStage.setScene(startscreen);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception ex){ex.printStackTrace();};
    }
}
