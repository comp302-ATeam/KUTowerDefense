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
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.effect.Effect;
import java.util.List;

public class GameSceneController {
    // Use singleton instance of the game controller to handle pause, resume, and speed changes..
    private GameActionController gameActionController = GameActionController.getInstance();
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
    @FXML
    private ImageView pauseImageView;
    @FXML
    private ImageView resumeImageView;
    @FXML
    private ImageView speedUpImageView;

    private void setImageRed(ImageView imageView, boolean isRed) {
        if (isRed) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setHue(1.0);         // Full red hue
            colorAdjust.setSaturation(0.9);   // Very saturated
            colorAdjust.setBrightness(-0.2);  // Slightly darker
            colorAdjust.setContrast(0.3);     // More contrast
            imageView.setEffect(colorAdjust);
        } else {
            imageView.setEffect(null);  // Remove the effect to return to original color
        }
    }

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
        if (GameActionController.isPaused()) {
            setImageRed(speedUpImageView, true);  // Speed Up button should be when the game is paused
            setImageRed(pauseImageView, true);    // Pause button red so it can not be clicked while paused
            setImageRed(resumeImageView, false);  // Keep resume button normal
        }
        System.out.println("Game Paused");
    }

    @FXML
    private void handleResumeGame(ActionEvent event) {
        gameActionController.resumeGame();
        if (waveSpawner != null) {
            waveSpawner.resume();
        }
        if (!GameActionController.isPaused()) {
            setImageRed(speedUpImageView, false); // Reset speed up button to normal when game resumes
            setImageRed(pauseImageView, false);   // Reset pause button to normal
            setImageRed(resumeImageView, false);  // Keep resume button normal
        }
        System.out.println("Resumed Game");
    }

    @FXML
    private void handleSpeedUp(ActionEvent event) {
        gameActionController.speedUpGame();
        // No color changes when speed up is clicked
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
            System.out.println(mainPath[0]);
            int startingX = mainPath[0].x.intValue();
            int startingY = mainPath[0].y.intValue();

            // Initialize WaveSpawner with wave system
            waveSpawner = new WaveSpawner(startingX, startingY, gamePane, mainPath, this);
            waveSpawner.startGame();
            // No AnimationTimer here for enemy or wave updates!
        });
    }
}