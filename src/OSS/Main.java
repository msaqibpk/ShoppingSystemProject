package OSS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.Objects;

public class Main extends Application {

    private Label imaconLogo;
    Stage window;
    Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("test.fxml")));
        primaryStage.setTitle("IMACON.com");
        mainScene = new Scene(root, 800.0, 500.0);
        primaryStage.setScene(mainScene);
        // Show the window.
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
