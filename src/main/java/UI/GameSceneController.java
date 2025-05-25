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
    private GameActionController gameActionController = new GameActionController();
    private WaveSpawner waveSpawner;

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
    @FXML private Label waveMessageLabel;

    public void updateGold(int gold) {
        labelGold.setText(String.valueOf(gold));
    }

    public void updateLives(int lives) {
        labelLives.setText(String.valueOf(lives));
    }

    public void updateWave(int wave) {
        labelWave.setText(String.valueOf(Math.max(1, wave)));
    }

    public void showWaveMessage(int waveIndex) {
        waveMessageLabel.setText("Wave " + (waveIndex + 1) + " Başladı!");
        waveMessageLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: orange; -fx-background-color: rgba(0,0,0,0.5); -fx-padding: 10px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        waveMessageLabel.setVisible(true);
        new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(javafx.util.Duration.seconds(2), e -> waveMessageLabel.setVisible(false))
        ).play();
    }

    @FXML
    private void handlePauseButton(ActionEvent event) {
        gameActionController.pauseGame();
        if (waveSpawner != null) {
            waveSpawner.stop();
        }
        System.out.println("Game Paused");
    }
    @FXML
    private void handleResumeGame(ActionEvent event) {
        gameActionController.resumeGame();
        if (waveSpawner != null) {
            waveSpawner.resume();
        }
        System.out.println("Resumed Game");
    }
    @FXML
    private void handleSpeedUp(ActionEvent event) {
        gameActionController.speedUpGame();
        System.out.println("Speed Up Game");
    }

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

            // AnimationTimer to update all active enemies
            new AnimationTimer() {
                private long lastTime = 0;
                @Override
                public void handle(long now) {
                    if (lastTime > 0) {
                        double deltaSec = (now - lastTime) / 1_000_000_000.0;
                        List<Enemy> activeEnemies = waveSpawner.getActiveEnemies();
                        for (Enemy enemy : activeEnemies) {
                            enemy.update(deltaSec);
                        }
                        // Update wave label every frame
                        updateWave(waveSpawner.getCurrentWave());
                    }
                    lastTime = now;
                }
            }.start();
        });
    }
}
