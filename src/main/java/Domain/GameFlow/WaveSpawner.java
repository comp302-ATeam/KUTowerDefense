package Domain.GameFlow;

import Domain.GameObjects.Enemy;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import java.util.List;
import Domain.GameFlow.GameSettingsManager;

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
    private long lastTime = 0;
    private GameSettings gameSettings;     // Store game settings
    private double gracePeriod = 4.0;     // 4 seconds grace period
    private double gracePeriodTimer = 0;   // Timer for grace period
    private boolean inGracePeriod = true;  // Whether we're in grace period

    public WaveSpawner(int startX, int startY, Pane gamePane, Vector2<Double>[] mainPath, UI.GameSceneController gameSceneController) {
        WaveManager.initialize(startX, startY, gamePane, mainPath, gameSceneController);
        this.waveManager = WaveManager.getInstance();
        this.gamePane = gamePane;
        this.mainPath = mainPath;
        this.gameSceneController = gameSceneController;
        setupGameLoop();
        
        // Set up GoldPouch with GameSceneController
        Domain.GameObjects.GoldPouch.setGameSceneController(gameSceneController);
    }

    /**
     * Sets the game settings to use for enemy stats, tower costs, etc.
     * @param settings GameSettings object containing all configuration
     */
    public void setGameSettings(GameSettings settings) {
        this.gameSettings = settings;
        // Pass settings to wave manager
        if (waveManager != null) {
            waveManager.setGameSettings(settings);
        }
        System.out.println("🔧 WaveSpawner: Applied game settings");
    }

    /**
     * Gets current game settings from singleton manager.
     * @return Current game settings
     */
    private GameSettings getCurrentSettings() {
        if (gameSettings == null) {
            gameSettings = GameSettingsManager.getInstance().getSettings();
        }
        return gameSettings;
    }
    
    // Adds configurable waves based on game settings.
    // Starts the wave logic.
    // Sets isPaused to false and starts the animation timer
    public void startGame() {
        GameSettings settings = getCurrentSettings();
        
        if (settings != null && settings.waves != null) {
            // Use configurable wave settings
            GameSettings.Waves waveConfig = settings.waves;
            
            System.out.println("🌊 Creating " + waveConfig.numWaves + " waves with singleton settings:");
            System.out.println("   - Groups per wave: " + waveConfig.groupsPerWave);
            System.out.println("   - Goblins per group: " + waveConfig.goblinsPerGroup);
            System.out.println("   - Knights per group: " + waveConfig.knightsPerGroup);
            
            // Create the configured number of waves
            for (int i = 0; i < waveConfig.numWaves; i++) {
                // Add wave with configured enemy counts and groups
                waveManager.addWave(waveConfig.knightsPerGroup, waveConfig.goblinsPerGroup, waveConfig.groupsPerWave);
                System.out.println("   Wave " + (i + 1) + ": " + waveConfig.knightsPerGroup + " knights, " + 
                                 waveConfig.goblinsPerGroup + " goblins, " + waveConfig.groupsPerWave + " groups");
            }
        } else {
            // Fallback to default waves if no settings available
            System.out.println("⚠️ No wave settings found, using defaults");
            waveManager.addWave(3, 3, 1);  // 3 knights 3 goblins 1 group
            waveManager.addWave(3, 2, 3);
            waveManager.addWave(4, 3, 2);
            waveManager.addWave(1, 0, 1);
            waveManager.addWave(0, 1, 1);
        }

        // Reset grace period
        inGracePeriod = true;
        gracePeriodTimer = 0;
        
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
        if (inGracePeriod) {
            gracePeriodTimer += deltaTime;
            if (gracePeriodTimer >= gracePeriod) {
                inGracePeriod = false;
                gracePeriodTimer = 0;
                waveManager.startWaves();
            }
            return;
        }

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
        lastTime = 0;
        if (gameLoop != null) {
            gameLoop.start();
        }
    }

    public int getCurrentWave() {
        return waveManager.getCurrentWave();
    }
}
