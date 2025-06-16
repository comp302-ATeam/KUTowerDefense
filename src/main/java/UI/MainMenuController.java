package UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private Button root;

    @FXML
    private void onNewGame() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/MapSelectionScene.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setScene(scene);

            Image cursorImg = new Image(getClass().getResourceAsStream("/Assets/UI/01.png"));
            double hotspotX = cursorImg.getWidth()  / 2;
            double hotspotY = cursorImg.getHeight() / 2;
            scene.setCursor(new ImageCursor(cursorImg, hotspotX, hotspotY));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 