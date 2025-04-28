package UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OptionsSceneController {

    @FXML
    private Button backButton;

    @FXML
    private Button musicToggleButton;

    @FXML
    private Button soundEffectsToggleButton;

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/StartScene.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMusicToggle(ActionEvent event) {
        // TODO: Add logic to toggle background music
        System.out.println("Music toggled!");
    }

    @FXML
    private void handleSfxToggle(ActionEvent event) {
        // TODO: Add logic to toggle sound effects
        System.out.println("SFX toggled!");
    }
}
