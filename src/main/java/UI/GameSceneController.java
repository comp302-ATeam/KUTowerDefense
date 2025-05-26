package UI;

import Domain.GameFlow.GameActionController;
import Domain.GameFlow.MapLoader;
import Domain.GameFlow.Vector2;
import Domain.GameFlow.WaveSpawner;
import Domain.GameObjects.Enemy;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.util.List;

public class GameSceneController {
    // Creates an instance of the game controller to handle pause, resume, and speed changes..
    private GameActionController gameActionController = new GameActionController();
    private WaveSpawner waveSpawner;

    // Will manage the spawning of enemy waves
    @FXML
    private Button pauseButton;
    @FXML
    private Button resumeGame;
    @FXML
    private Button speedUp;
    @FXML
    private Pane gamePane;

    @FXML
    GridPane gameGrid;

    MapLoader mapLoader;

    @FXML private Label labelGold;
    @FXML private Label labelLives;
    @FXML private Label labelWave;

    public void updateGold(int gold) {
        labelGold.setText(String.valueOf(gold));
    }

    public void updateLives(int lives) {
        labelLives.setText(String.valueOf(lives));
    }

    public void updateWave(int wave) {
        labelWave.setText(String.valueOf(Math.max(1, wave)));
    }
    // Pauses the game via gameActionController. Also stops the waveSpawner.
    @FXML
    private void handlePauseButton(ActionEvent event) {
        gameActionController.pauseGame();
        if (waveSpawner != null) {
            waveSpawner.stop();
        }
        System.out.println("Game Paused");
    }
    // Resumes the game and resumes spawning waves.
    @FXML
    private void handleResumeGame(ActionEvent event) {
        gameActionController.resumeGame();
        if (waveSpawner != null) {
            waveSpawner.resume();
        }
        System.out.println("Resumed Game");
    }
    // Speeds up the game
    @FXML
    private void handleSpeedUp(ActionEvent event) {
        gameActionController.speedUpGame();
        System.out.println("Speed Up Game");
    }
    // Initializes MapLoader with the grid and pane to generate or load the map.
    // Gets the path enemies will follow.
    // Retrieves the starting point for the wave spawner from the first point of the path.
    // Creates a new WaveSpawner with game data and UI references.
    // Starts the wave spawning process.
    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            mapLoader = new MapLoader(gameGrid, gamePane);
            Vector2<Double>[] mainPath = mapLoader.getPath();
            int startingX = mainPath[0].x.intValue();
            int startingY = mainPath[0].y.intValue();

            // Initialize WaveSpawner with wave system
            waveSpawner = new WaveSpawner(startingX, startingY, gamePane, mainPath, this);
            waveSpawner.startGame();
            // No AnimationTimer here for enemy or wave updates!
        });
    }
}
