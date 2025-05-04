import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.ImageCursor;

public class Main extends Application {
    public Stage rootStage;



    @Override
    public void start(Stage stage) throws Exception {
        //Set the background picture and the icon of the scene.
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
        //Initialized the starting scene

        //Initializing the cursor icon
        Image cursorImg = new Image(getClass().getResourceAsStream("/Assets/UI/01.png"));
        double hotspotX = cursorImg.getWidth()  / 2;
        double hotspotY = cursorImg.getHeight() / 2;
        scene.setCursor(new ImageCursor(cursorImg, hotspotX, hotspotY));
        //Cursor is initialized.

        rootStage = stage;
    }

    public static void main(String[] args) {
        launch();
    }
}
