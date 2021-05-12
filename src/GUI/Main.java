package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// 600 * 768
public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GameOverScreen.fxml"));
        primaryStage.setTitle("Space Invader - aber besser");
        Scene startscreen = new Scene(root);
        primaryStage.setScene(startscreen);
        primaryStage.show();
    }
}
