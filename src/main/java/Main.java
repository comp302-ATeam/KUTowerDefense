import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {
    public Stage rootStage;



    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartScene.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("KUTowerDefense");
        stage.show();

        rootStage = stage;
    }

    public static void main(String[] args) {
        launch();
    }
}
