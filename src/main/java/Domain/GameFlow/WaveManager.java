package Domain.GameFlow;
import Domain.GameObjects.Enemy;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import UI.GameSceneController;
import Domain.GameFlow.Vector2;
import javafx.animation.AnimationTimer;

/**
 * WaveManager class manages multiple waves of enemies in the game.
 * It handles wave spawning, timing, and tracking of all active enemies.
 */

public class WaveManager {
    private static WaveManager instance;
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
    private static final double WAVE_INTERVAL = 15.0;
    private int nextWaveToStart = 0;

    private WaveManager(int xPos, int yPos, Pane gamePane, Vector2<Double>[] mainPath, GameSceneController gameSceneController) {
        this.waves = new ArrayList<>();
        this.currentWaveIndex = 0;
        this.xPos = xPos;
        this.yPos = yPos;
        this.gamePane = gamePane;
        this.mainPath = mainPath;
        this.isGameComplete = false;
        this.gameSceneController = gameSceneController;
        this.nextWaveToStart = 0;
        this.waveTimer = 0;
    }

    public static WaveManager getInstance() {
        return instance;
    }

    public static void initialize(int startX, int startY, Pane gamePane, Vector2<Double>[] mainPath, GameSceneController gameSceneController) {
        if (instance == null) {
            instance = new WaveManager(startX, startY, gamePane, mainPath, gameSceneController);
        }
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
        Wave wave = new Wave(waves.size(), knightCount, goblinCount, groupCount, xPos, yPos, gamePane, mainPath);
        waves.add(wave);
    }

    // Tracks time since last wave.
    //If the interval has passed, starts the next wave and resets the timer.
    //Updates all waves that have already been started.
    public void updateAndAdvanceWaves(GameSceneController controller, double deltaTime) {
        if (nextWaveToStart > 0) {
            waveTimer += deltaTime;
            if (nextWaveToStart < waves.size() && waveTimer >= WAVE_INTERVAL) {
                System.out.println("[WaveManager] Starting wave " + nextWaveToStart);
                waves.get(nextWaveToStart).startWave();
                controller.updateWave(nextWaveToStart + 1);
                nextWaveToStart++;
                waveTimer = 0;
            }
        }
        for (int i = 0; i < nextWaveToStart; i++) {
            waves.get(i).update(deltaTime);
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

}
