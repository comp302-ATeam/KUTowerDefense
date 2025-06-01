package Domain.GameFlow;

import Domain.GameObjects.Enemy;
import java.util.ArrayList;
import java.util.List;

public class MockWave {
    private final List<Enemy> activeEnemies;
    private final int knightCount;
    private final int goblinCount;
    private final int groupCount;
    private int currentGroup;
    private int currentEnemyCount;
    private boolean isWaveComplete;
    private double enemySpawnTimer;
    private double groupWaitTimer;
    private boolean waitingForNextGroup;
    private static final double ENEMY_SPAWN_INTERVAL = 0.25;
    private static final double GROUP_SPAWN_INTERVAL = 45.0;

    public MockWave(int waveIndex, int knightCount, int goblinCount, int groupCount, int xPos, int yPos, Vector2<Double>[] mainPath) {
        this.knightCount = knightCount;
        this.goblinCount = goblinCount;
        this.groupCount = groupCount;
        this.activeEnemies = new ArrayList<>();
        this.currentGroup = 0;
        this.currentEnemyCount = 0;
        this.isWaveComplete = false;
        this.enemySpawnTimer = 0;
        this.groupWaitTimer = 0;
        this.waitingForNextGroup = false;
    }

    // Getter methods for testing
    public List<Enemy> getActiveEnemies() { return activeEnemies; }
    public boolean isWaveComplete() { return isWaveComplete && activeEnemies.isEmpty(); }
    public int getCurrentGroup() { return currentGroup; }
    public int getGroupCount() { return groupCount; }
    public int getCurrentEnemyCount() { return currentEnemyCount; }
    public boolean isWaitingForNextGroup() { return waitingForNextGroup; }
    public double getEnemySpawnTimer() { return enemySpawnTimer; }
    public double getGroupWaitTimer() { return groupWaitTimer; }



}
