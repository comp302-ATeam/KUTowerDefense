import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public Stage rootStage;



    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartScene.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("KUTowerDefense");
        Image icon = new Image(
                getClass().getResourceAsStream("/Assets/KuTowerDefence1.jpg")
        );
        stage.getIcons().add(icon);
        stage.centerOnScreen();
        stage.show();

        rootStage = stage;
    }

    public static void main(String[] args) {
        launch();
    }
}
