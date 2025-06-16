package UI;

import Domain.GameFlow.*;
import Domain.GameObjects.Enemy;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.geometry.Rectangle2D;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.effect.Effect;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.ImageCursor;
import javafx.scene.Cursor;

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
    @FXML
    private Button pauseResumeButton;
    @FXML
    private ImageView pauseResumeImageView;

    private String mapName = "mapSave";  // Default map name
    private GameSettings currentSettings; // Store current game settings

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

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

    public Label getLabelGold() {
        return labelGold;
    }

    public void updateLives(int lives) {
        labelLives.setText(String.valueOf(lives));
    }

    public void updateWave(int wave) {
        labelWave.setText(String.valueOf(Math.max(1, wave)));
    }
    @FXML
    private void handlePauseResumeButton(ActionEvent event) {
        // Always keep both controllers in sync
        boolean currentlyPaused = GameActionController.isPaused();
        if (currentlyPaused) {
            // Resume everything
            gameActionController.resumeGame();
            if (waveSpawner != null) {
                waveSpawner.resume();
            }
            setImageRed(speedUpImageView, false);
            setImageRed(pauseResumeImageView, false);
            // Show pause icon
            pauseResumeImageView.setViewport(new Rectangle2D(128, 64, 64, 64));
            System.out.println("Resumed Game");
        } else {
            // Pause everything
            gameActionController.pauseGame();
            if (waveSpawner != null) {
                waveSpawner.stop();
            }
            setImageRed(speedUpImageView, true);
            setImageRed(pauseResumeImageView, false);
            // Show resume icon
            pauseResumeImageView.setViewport(new Rectangle2D(0, 64, 64, 64));
            System.out.println("Game Paused");
        }
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
            resetGame();
            
            // Set custom cursor
            Image cursorImg = new Image(getClass().getResourceAsStream("/Assets/UI/01.png"));
            double hotspotX = cursorImg.getWidth() / 2;
            double hotspotY = cursorImg.getHeight() / 2;
            gamePane.getScene().setCursor(new ImageCursor(cursorImg, hotspotX, hotspotY));
        });
    }

    private void resetGame() {
        // Load current game settings from singleton
        currentSettings = GameSettingsManager.getInstance().getSettings();
        
        // Clear any existing game state
        if (waveSpawner != null) {
            waveSpawner.stop();
        }
        if (gamePane != null) {
            gamePane.getChildren().clear();
        }
        if (gameGrid != null) {
            gameGrid.getChildren().clear();
        }

        // Reset UI elements using settings from options menu
        updateGold(currentSettings.player.startingGold);  // Use configured starting gold
        updateLives(currentSettings.player.startingHP);   // Use configured starting lives
        updateWave(1);    // Reset to first wave

        // Initialize new game
        mapLoader = new MapLoader(gameGrid, gamePane, mapName);
        Vector2<Double>[] mainPath = mapLoader.getPath();
        int startingX = mainPath[0].x.intValue();
        int startingY = mainPath[0].y.intValue();

        // Reset WaveManager with settings
        WaveManager.reset();

        // Set game pane in GameActionController
        gameActionController.setGamePane(gamePane);

        // Initialize new WaveSpawner with settings
        waveSpawner = new WaveSpawner(startingX, startingY, gamePane, mainPath, this);
        waveSpawner.setGameSettings(currentSettings);  // Pass settings to wave spawner
        waveSpawner.startGame();

        // Ensure wave index is reset in UI
        Platform.runLater(() -> {
            updateWave(1);
        });
        
        System.out.println("🎮 Game initialized with singleton settings - Gold: " + currentSettings.player.startingGold + ", Lives: " + currentSettings.player.startingHP);
    }
}