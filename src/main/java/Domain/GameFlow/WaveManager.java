package Domain.GameFlow;
import Domain.GameObjects.Enemy;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import UI.GameSceneController;
import Domain.GameFlow.Vector2;
import javafx.animation.AnimationTimer;
import javafx.scene.control.ProgressBar;

/**
 * WaveManager class manages multiple waves of enemies in the game.
 * It handles wave spawning, timing, and tracking of all active enemies.
 */

public class WaveManager {

    // Private static instance of the singleton
    private static WaveManager instance;

    private boolean waveOver;
    private final List<Wave> waves;     // List to store all waves in the game
    private int currentWaveIndex;       // Tracks which wave is currently active
    private final int xPos;
    private final int yPos;
    private boolean isGameComplete;     // Flag to track if all waves are complete
    private final Pane gamePane;
    private final Vector2<Double>[] mainPath;
    private final GameSceneController gameSceneController;
    private boolean waveJustStarted = false;
    private double waveTimer = 0;
    private static final double DEFAULT_WAVE_INTERVAL = 10.0;  // Default fallback
    private double currentWaveInterval = DEFAULT_WAVE_INTERVAL; // Configurable interval
    private int nextWaveToStart = 0;
    private int playerLives = 10;  // Default starting lives
    private boolean gameOver = false;  // Flag to track if game is over
    private GameSettings gameSettings; // Store game settings
    private boolean GameLost = false;
    private double gracePeriod = 4.0; // 4 seconds grace period before first wave
    private boolean inGracePeriod = true;
    private double gracePeriodTimer = 0;
    private double waveStartTime = 0;
    private double waveEndTime = 0;
    private double waveDuration = 0;
    private double waveProgress = 0;
    private ProgressBar waveProgressBar = new ProgressBar(0);
    private boolean isWaveActive = false;
    private int currentWave = 0;

    private WaveManager(int xPos, int yPos, Pane gamePane, Vector2<Double>[] mainPath, GameSceneController gameSceneController) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.gamePane = gamePane;
        this.mainPath = mainPath;
        this.gameSceneController = gameSceneController;
        this.currentWave = 0;
        this.waves = new ArrayList<>();
        this.isWaveActive = false;
        this.waveTimer = 0;
        this.gracePeriod = 4.0; // 4 seconds grace period before first wave
        this.inGracePeriod = true;
        this.gracePeriodTimer = 0;

        // Set the path in GameActionController
        GameActionController.getInstance().setMainPath(mainPath);

        // Initialize waves

        // Start the game loop
    }

    /**
     * Sets the game settings and applies player configuration.
     * @param settings GameSettings object containing all configuration
     */
    public void setGameSettings(GameSettings settings) {
        this.gameSettings = settings;
        if (settings != null) {
            if (settings.player != null) {
                this.playerLives = settings.player.startingHP;  // Use configured starting lives
                System.out.println("🎯 WaveManager: Applied player settings - Lives set to: " + playerLives);
            }
            if (settings.waves != null) {
                this.currentWaveInterval = settings.waves.delayBetweenWaves;  // Use configured wave delay
                System.out.println("🎯 WaveManager: Applied wave settings - Wave interval set to: " + currentWaveInterval + " seconds");
            }
        }
    }

    /**
     * Gets current settings from singleton manager.
     * @return Current game settings
     */
    private GameSettings getCurrentSettings() {
        if (gameSettings == null) {
            gameSettings = GameSettingsManager.getInstance().getSettings();
        }
        return gameSettings;
    }

    // Public static method to get the singleton instance
    public static synchronized WaveManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("WaveManager must be initialized before use. Call initialize() first.");
        }
        return instance;
    }

    // Public static method to initialize the singleton
    public static synchronized void initialize(int startX, int startY, Pane gamePane, Vector2<Double>[] mainPath, GameSceneController gameSceneController) {
        if (instance == null) {
            instance = new WaveManager(startX, startY, gamePane, mainPath, gameSceneController);
        } else {
            throw new IllegalStateException("WaveManager is already initialized.");
        }
    }

    // Public static method to reset the singleton
    public static synchronized void reset() {
        if (instance != null) {
            // Reset all wave-related state
            instance.waveTimer = 0;
            instance.nextWaveToStart = 0;
            instance.currentWaveIndex = 0;
            instance.waveJustStarted = false;
            instance.isGameComplete = false;
            instance.gameOver = false;
            instance.playerLives = 10;  // Reset to starting lives
            instance.waves.clear();     // Clear all waves
            
            // Clear any remaining enemies
            Enemy.activeEnemies.clear();
            
            // Reset game settings
            instance.gameSettings = GameSettingsManager.getInstance().getSettings();
            if (instance.gameSettings != null && instance.gameSettings.player != null) {
                instance.playerLives = instance.gameSettings.player.startingHP;
            }
            
            System.out.println("[WaveManager] Reset complete - All waves and enemies cleared");
        }
        instance = null;
    }

    // Starts the wave sequence:
    //Begins with wave 0.
    //Calls startWave() on it.
    //Updates UI to show Wave 1.
    //Increments nextWaveToStart.
    public void startWaves() {
        nextWaveToStart = 0;
        waveTimer = 0;
        if (!waves.isEmpty()) {
            System.out.println("[WaveManager] Starting wave 0");
            waves.get(0).startWave();
            gameSceneController.updateWave(1);
            nextWaveToStart = 1;

        }
    }

    // Adds a new wave with given numbers of knights, goblins, and groups.
    //Wave index is inferred by the size of the list.
    public void addWave(int knightCount, int goblinCount, int groupCount) {
        GameSettings settings = getCurrentSettings();
        Wave wave = new Wave(waves.size(), knightCount, goblinCount, groupCount, xPos, yPos, gamePane, mainPath, settings);
        waves.add(wave);
    }

    // Tracks time since last wave.
    //If the interval has passed, starts the next wave and resets the timer.
    //Updates all waves that have already been started.
    public void updateAndAdvanceWaves(GameSceneController controller, double deltaTime) {
        if (gameOver) return;  // Don't update waves if game is over

        // Scale deltaTime by game speed
        double scaledDeltaTime = deltaTime * GameActionController.getInstance().getGameSpeed();

        // Update wave timer and start next wave if ready
        if (nextWaveToStart > 0) {
            waveTimer += scaledDeltaTime;
            if (nextWaveToStart < waves.size() && waveTimer >= currentWaveInterval) {
                System.out.println("[WaveManager] Starting wave " + nextWaveToStart);
                waves.get(nextWaveToStart).startWave();
                controller.updateWave(nextWaveToStart + 1);
                nextWaveToStart++;
                waveTimer = 0;
            }
        }

        // Update all active waves
        boolean allWavesComplete = true;
        boolean anyEnemiesRemaining = false;

        for (int i = 0; i < nextWaveToStart; i++) {
            Wave wave = waves.get(i);
            wave.update(scaledDeltaTime);
            
            // Check if wave is complete and has no enemies
            if (!wave.isWaveComplete() || !wave.getActiveEnemies().isEmpty()) {
                allWavesComplete = false;
            }
            if (!wave.getActiveEnemies().isEmpty()) {
                anyEnemiesRemaining = true;
            }
        }

        // Check for win condition
        if (allWavesComplete && !anyEnemiesRemaining && nextWaveToStart >= waves.size() &&!GameLost) {
            System.out.println("[WaveManager] Win condition met! All waves complete and no enemies remaining.");
            reset();
            showWinScreen();
            gameOver = true;  // Set game over flag to prevent further updates
            GameActionController.getInstance().pauseGame();  // Pause the game
        }
    }

    private void showWinScreen() {
        if (gameOver) return;  // Don't show win screen if game is already over

        gameOver = true;  // Set game over flag
        // Stop the game
        GameActionController.getInstance().pauseGame();

        // Show win screen
        try {
            javafx.application.Platform.runLater(() -> {
                try {
                    // Create a container for the game content
                    Pane gameContent = new Pane();
                    gameContent.setPrefSize(gamePane.getWidth(), gamePane.getHeight());

                    // Move all existing children to the game content pane
                    while (!gamePane.getChildren().isEmpty()) {
                        gameContent.getChildren().add(gamePane.getChildren().get(0));
                    }

                    // Add the game content pane to the main game pane
                    gamePane.getChildren().add(gameContent);

                    // Add blur effect to the game content
                    javafx.scene.effect.GaussianBlur blur = new javafx.scene.effect.GaussianBlur(10);
                    gameContent.setEffect(blur);

                    // Load the win screen
                    javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(UI.WinScreenController.class.getResource("/WinScreen.fxml"));
                    javafx.scene.Parent root = loader.load();

                    // Get the controller and ensure it's initialized
                    UI.WinScreenController controller = loader.getController();
                    if (controller != null) {
                        System.out.println("Win screen controller loaded successfully");
                    } else {
                        System.out.println("Warning: Win screen controller is null");
                    }

                    // Add the win screen as an overlay to the game pane
                    gamePane.getChildren().add(root);

                    // Center the win screen
                    root.setLayoutX((gamePane.getWidth() - root.prefWidth(-1)) / 2);
                    root.setLayoutY((gamePane.getHeight() - root.prefHeight(-1)) / 2);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Enemy> getActiveEnemies() {
        List<Enemy> allActiveEnemies = new ArrayList<>();
        for (int i = 0; i < nextWaveToStart; i++) {
            allActiveEnemies.addAll(waves.get(i).getActiveEnemies());
        }
        return allActiveEnemies;
    }

    public boolean isGameComplete() {
        return isGameComplete;
    }

    public int getCurrentWave() {
        return currentWaveIndex;
    }

    public void enemyReachedEnd() {
        if (gameOver) return;  // Don't process if game is already over


        // Check if game over
        if (playerLives <= 1) {
            gameOver = true;  // Set game over flag
            GameLost = true;
            // Stop the game
            GameActionController.getInstance().pauseGame();
            // Show game over screen
            try {
                javafx.application.Platform.runLater(() -> {
                    try {
                        // Create a container for the game content
                        Pane gameContent = new Pane();
                        gameContent.setPrefSize(gamePane.getWidth(), gamePane.getHeight());

                        // Move all existing children to the game content pane
                        while (!gamePane.getChildren().isEmpty()) {
                            gameContent.getChildren().add(gamePane.getChildren().get(0));
                        }

                        // Add the game content pane to the main game pane
                        gamePane.getChildren().add(gameContent);

                        // Add blur effect to the game content
                        javafx.scene.effect.GaussianBlur blur = new javafx.scene.effect.GaussianBlur(10);
                        gameContent.setEffect(blur);

                        // Load the game over screen
                        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(UI.GameOverScreenController.class.getResource("/GameOverScreen.fxml"));
                        javafx.scene.Parent root = loader.load();

                        // Get the controller and ensure it's initialized
                        UI.GameOverScreenController controller = loader.getController();
                        if (controller != null) {
                            System.out.println("Game over screen controller loaded successfully");
                        } else {
                            System.out.println("Warning: Game over screen controller is null");
                        }

                        // Add the game over screen as an overlay to the game pane

                        gamePane.getChildren().add(root);

                        // Center the game over screen
                        root.setLayoutX((gamePane.getWidth() - root.prefWidth(-1)) / 2);
                        root.setLayoutY((gamePane.getHeight() - root.prefHeight(-1)) / 2);
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        playerLives--;
        gameSceneController.updateLives(playerLives);

        // Remove all enemies that have reached the end
        List<Enemy> activeEnemies = getActiveEnemies();
        for (Enemy enemy : activeEnemies) {
            if (enemy.hasReachedEnd()) {
                enemy.Die();  // This will remove the enemy from the game pane
            }
        }
    }

    public void update(double deltaTime) {
        if (inGracePeriod) {
            gracePeriodTimer += deltaTime;
            if (gracePeriodTimer >= gracePeriod) {
                inGracePeriod = false;
                gracePeriodTimer = 0;
                startWave();
            }
            return;
        }

        if (isWaveActive) {
            waveTimer += deltaTime;
            if (waveTimer >= waveDuration) {
                endWave();
            }
        }
    }

    private void startWave() {
        if (currentWave < waves.size()) {
            isWaveActive = true;
            waveTimer = 0;
            waves.get(currentWave).startWave();
        }
    }

    private void endWave() {
        if (isWaveActive) {
            isWaveActive = false;
            currentWave++;
            if (currentWave < waves.size()) {
                startWave();
            }
        }
    }

    private void initializeWaves() {
        // Create waves with increasing difficulty
        for (int i = 0; i < 10; i++) {
            // Each wave gets more enemies and groups
            int knightCount = 2 + i;
            int goblinCount = 3 + i;
            int groupCount = 2 + (i / 2);
            Wave wave = new Wave(i + 1, knightCount, goblinCount, groupCount, xPos, yPos, gamePane, mainPath, gameSettings);
            waves.add(wave);
        }
    }

    private void startGameLoop() {
        new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double deltaTime = (now - lastTime) / 1_000_000_000.0; // convert to seconds
                lastTime = now;

                update(deltaTime);
            }
        }.start();
    }

    public void startGame() {
        // Reset wave state
        currentWave = 0;
        isWaveActive = false;
        waveTimer = 0;
        inGracePeriod = true;
        gracePeriodTimer = 0;
    }
}
