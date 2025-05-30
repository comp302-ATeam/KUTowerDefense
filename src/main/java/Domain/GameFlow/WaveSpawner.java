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
    public final WaveManager waveManager; // Manages wave creation and timing
    private final Pane gamePane;           // JavaFX pane where game elements are displayed
    private AnimationTimer gameLoop;       // Controls the game loop timing and updates
    private boolean isPaused = false;
    private final Vector2<Double>[] mainPath;
    private final UI.GameSceneController gameSceneController;

    public WaveSpawner(int startX, int startY, Pane gamePane, Vector2<Double>[] mainPath, UI.GameSceneController gameSceneController) {
        this.waveManager = new WaveManager(startX, startY, gamePane, mainPath, gameSceneController);
        this.gamePane = gamePane;
        this.mainPath = mainPath;
        this.gameSceneController = gameSceneController;
        setupGameLoop();
    }
    // Adds predefined waves to the manager.
    //Starts the wave logic.
    //Sets isPaused to false and starts the animation timer
    public void startGame() {
        // Add some waves with different configurations
        waveManager.addWave(3, 3, 1);  // 3 knights 3 goblins 1 group
        waveManager.addWave(3, 2, 3);
        waveManager.addWave(4, 3, 2);

        waveManager.startWaves();   // Start wave spawning
        isPaused = false;
        if (gameLoop != null) {
            gameLoop.start();
        }
    }

    //Uses AnimationTimer to call the handle() method every frame.
    //Calculates deltaTime (time since last frame) in seconds.
    //Calls update(deltaTime) to process game logic like moving enemies.
    //Skips update if the game is paused.
    private void setupGameLoop() {
        gameLoop = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (isPaused) return;
                if (lastTime > 0) {
                    double deltaTime = (now - lastTime) / 1_000_000_000.0;
                    update(deltaTime);
                }
                lastTime = now;
            }
        };
    }

    //Updates game state every frame
    //Tells waveManager to update and potentially spawn the next wave.
    // If all waves are done and enemies are defeated, stop the loop.
    private void update(double deltaTime) {
        waveManager.updateAndAdvanceWaves(gameSceneController, deltaTime);
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
        isPaused = true;
        if (gameLoop != null) {
            gameLoop.stop();
        }

    }

    public void resume() {
        isPaused = false;
        if (gameLoop != null) {
            gameLoop.start();
        }
    }

    public int getCurrentWave() {
        return waveManager.getCurrentWave();
    }
}
