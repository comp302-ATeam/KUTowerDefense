package Domain.GameFlow;
import Domain.GameObjects.Enemy;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
/**
 * WaveManager class manages multiple waves of enemies in the game.
 * It handles wave spawning, timing, and tracking of all active enemies.
 */

public class WaveManager {
    private final List<Wave> waves;     // List to store all waves in the game
    private int currentWaveIndex;       // Tracks which wave is currently active
    private final int xPos;
    private final int yPos;
    private boolean isGameComplete;     // Flag to track if all waves are complete

    public WaveManager(int xPos, int yPos) {
        this.waves = new ArrayList<>();
        this.currentWaveIndex = 0;
        this.xPos = xPos;
        this.yPos = yPos;
        this.isGameComplete = false;

        // Create timeline for spawning waves
        this.waveSpawner = new Timeline(
                new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        startNextWave();
                    }
                }),
                new KeyFrame(Duration.seconds(5.0), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // empty but necessary for delay
                    }
                })
        );

    }
    // Starts the wave spawning process
    public void startWaves() {

    }
    // Adds a new wave to the game
    public void addWave(int knightCount, int goblinCount, int groupCount) {
        Wave wave = new Wave(knightCount, goblinCount, groupCount, xPos, yPos);
        waves.add(wave);
    }
    // Starts the next wave in sequence, Called by the wave spawner timeline
    private void startNextWave() {
        if (currentWaveIndex < waves.size()) {
            waves.get(currentWaveIndex).startWave();
            currentWaveIndex++;
        } else {
            isGameComplete = true;
        }
    }
    // Gets all currently active enemies from all waves
    public List<Enemy> getActiveEnemies() {
        List<Enemy> allActiveEnemies = new ArrayList<>();
        for (Wave wave : waves) {
            allActiveEnemies.addAll(wave.getActiveEnemies());
        }
        return allActiveEnemies;
    }

    // Checks if the game is complete.
    // Game is complete when all waves are finished and all enemies are defeated
    public boolean isGameComplete() {
        if (isGameComplete) {
            for (Wave wave : waves) {
                if (!wave.isWaveComplete()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    // Updates all waves and their enemies
    public void update(double deltaTime) {
        for (Wave wave : waves) {
            wave.update(deltaTime); // update each wave
        }
    }

}
