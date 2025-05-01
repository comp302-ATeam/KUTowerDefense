package UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;


public class GameSceneController {
    @FXML
    private Button pauseButton;
    @FXML
    private Button resumeGame;
    @FXML
    private Button speedUp;

    @FXML
    private void handlePauseButton(ActionEvent event) {

        System.out.println("Game Paused");
    }
    @FXML
    private void handleResumeButton(ActionEvent event) {
        System.out.println("Resumed Game");
    }
    @FXML
    private void handleSpeedUpButton(ActionEvent event) {
        System.out.println("Speed Up Game");
    }
}
