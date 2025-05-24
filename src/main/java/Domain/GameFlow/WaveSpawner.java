package Domain.GameFlow;

import Domain.GameObjects.Enemy;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import java.util.List;

public class WaveSpawner {
    private final WaveManager waveManager;
    private final Pane gamePane;
    private AnimationTimer gameLoop;

    public WaveSpawner(int startX, int startY, Pane gamePane) {
        this.waveManager = new WaveManager(startX, startY);
        this.gamePane = gamePane;
        setupGameLoop();
    }

    public void startGame() {
        // Add some waves with different configurations
        waveManager.addWave(3, 3, 1);  // 3 knights 3 goblins 1 group
        waveManager.addWave(3, 2, 3);
        waveManager.addWave(4, 3, 2);

        waveManager.startWaves();
        gameLoop.start();
    }




}
