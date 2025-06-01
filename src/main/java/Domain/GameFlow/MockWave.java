package Domain.GameFlow;

import Domain.GameObjects.Enemy;
import Domain.GameObjects.MockEnemy;
import Domain.GameObjects.MockGoblin;

import java.util.ArrayList;
import java.util.List;

public class MockWave {
    private final List<MockEnemy> activeEnemies;
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
    public List<MockEnemy> getActiveEnemies() { return activeEnemies; }
    public boolean isWaveComplete() { return isWaveComplete && activeEnemies.isEmpty(); }
    public int getCurrentGroup() { return currentGroup; }
    public int getGroupCount() { return groupCount; }
    public int getCurrentEnemyCount() { return currentEnemyCount; }
    public boolean isWaitingForNextGroup() { return waitingForNextGroup; }
    public double getEnemySpawnTimer() { return enemySpawnTimer; }
    public double getGroupWaitTimer() { return groupWaitTimer; }

    public void startWave() {
        currentGroup = 0;
        currentEnemyCount = 0;
        isWaveComplete = false;
        enemySpawnTimer = 0;
        groupWaitTimer = 0;
        waitingForNextGroup = false;
        activeEnemies.clear();
    }

    public void update(double deltaTime) {
        if (currentGroup < groupCount) {
            if (!waitingForNextGroup) {
                if (currentEnemyCount < (knightCount + goblinCount)) {
                    enemySpawnTimer += deltaTime;
                    if (enemySpawnTimer >= ENEMY_SPAWN_INTERVAL) {
                        spawnEnemy();
                        enemySpawnTimer = 0;
                    }
                } else {
                    waitingForNextGroup = true;
                    groupWaitTimer = 0;
                }
            } else {
                groupWaitTimer += deltaTime;
                if (groupWaitTimer >= GROUP_SPAWN_INTERVAL) {
                    currentGroup++;
                    if (currentGroup < groupCount) {
                        currentEnemyCount = 0;
                        enemySpawnTimer = 0;
                        waitingForNextGroup = false;
                    }
                }
            }
        } else {
            isWaveComplete = true;
        }
    }

    private void spawnEnemy() {
        // For testing purposes, we'll just add a simple enemy
        // The actual damage calculation will be handled by the MockEnemy class
        for (int i = 0; i < 5; i++) {
            activeEnemies.add(new MockGoblin(0, 0, "Goblin", 100, 5));
            currentEnemyCount++;
        }
    }


    public void clearEnemies() {
        activeEnemies.clear();
    }

    public void setWaveComplete(boolean complete) {
        this.isWaveComplete = complete;
    }
}