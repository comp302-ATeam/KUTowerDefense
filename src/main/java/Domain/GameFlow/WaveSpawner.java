package Domain.GameFlow;

import Domain.GameObjects.Enemy;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import java.util.List;

/**
 * WaveSpawner class manages the game loop and wave spawning process.
 * It coordinates between the game UI and wave management system.
 */
public class WaveSpawner {
    private final WaveManager waveManager; // Manages wave creation and timing
    private final Pane gamePane;           // JavaFX pane where game elements are displayed
    private AnimationTimer gameLoop;       // Controls the game loop timing and updates
    private final Vector2<Double>[] mainPath;
    private final UI.GameSceneController gameSceneController;

    public WaveSpawner(int startX, int startY, Pane gamePane, Vector2<Double>[] mainPath, UI.GameSceneController gameSceneController) {
        this.waveManager = new WaveManager(startX, startY, gamePane, mainPath, gameSceneController);
        this.gamePane = gamePane;
        this.mainPath = mainPath;
        this.gameSceneController = gameSceneController;
        setupGameLoop();
    }

    public void startGame() {
        // Add some waves with different configurations
        waveManager.addWave(3, 3, 1);  // 3 knights 3 goblins 1 group
        waveManager.addWave(3, 2, 3);
        waveManager.addWave(4, 3, 2);

        waveManager.startWaves();   // Start wave spawning
        gameLoop.start();           // Start game loop
    }

    /**
     * Sets up the game loop using JavaFX AnimationTimer
     * Calculates delta time between frames for smooth updates
     */
    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime > 0) {
                    double deltaTime = (now - lastTime) / 1_000_000_000.0;
                    update(deltaTime);
                }
                lastTime = now;
            }
        };
    }

    //Updates game state every frame
    private void update(double deltaTime) {
        // Update wave manager state
        waveManager.update(deltaTime);
        // Update all active enemies
        List<Enemy> activeEnemies = waveManager.getActiveEnemies();
        for (Enemy enemy : activeEnemies) {
            enemy.update(deltaTime);
        }
        // Check if game is complete
        if (waveManager.isGameComplete()) {
            gameLoop.stop();
            // Handle game completion
        }
    }
    // Gets all currently active enemies in the game
    public List<Enemy> getActiveEnemies() {
        return waveManager.getActiveEnemies();
    }

    /**
     * Stops the game loop
     * Called when game is paused or ended
     */
    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        waveManager.pauseAllWaves();
    }

    public void resume() {
        waveManager.resumeAllWaves();
        if (gameLoop != null) {
            gameLoop.start();
        }
    }

    public int getCurrentWave() {
        return waveManager.getCurrentWave();
    }
}
