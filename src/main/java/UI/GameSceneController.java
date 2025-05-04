package UI;

import Domain.GameFlow.GameActionController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;


public class GameSceneController {
    private GameActionController gameActionController = new GameActionController();

    @FXML
    private Button pauseButton;
    @FXML
    private Button resumeGame;
    @FXML
    private Button speedUp;


    @FXML
    private void handlePauseButton(ActionEvent event) {
        gameActionController.pauseGame();
        System.out.println("Game Paused");
    }
    @FXML
    private void handleResumeGame(ActionEvent event) {
        gameActionController.resumeGame();
        System.out.println("Resumed Game");
    }
    @FXML
    private void handleSpeedUp(ActionEvent event) {
        gameActionController.speedUpGame();
        System.out.println("Speed Up Game");
    }
}
