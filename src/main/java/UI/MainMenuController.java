package UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 