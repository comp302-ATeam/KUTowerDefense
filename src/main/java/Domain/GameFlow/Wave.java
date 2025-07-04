package Domain.GameFlow;

import Domain.GameObjects.Enemy;
import Domain.GameObjects.Goblin;
import Domain.GameObjects.Knight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Wave {
    private final List<Enemy> activeEnemies;   // This list holds all enemies currently active in the game
    private final int knightCount;             // number of knight in a group
    private final int goblinCount;             // number of goblins in a group
    private final int groupCount;              // number of groups
    private final int xPos;
    private final int yPos;
    private int currentGroup;
    private boolean isWaveComplete;
    private int currentEnemyCount;
    private double enemySpawnTimer;
    private double groupWaitTimer;
    private boolean waitingForNextGroup;
    private static final double DEFAULT_ENEMY_SPAWN_INTERVAL = 0.25;
    private static final double DEFAULT_GROUP_SPAWN_INTERVAL = 45.0;
    private final double enemySpawnInterval;   // Configurable enemy spawn interval
    private final double groupSpawnInterval;   // Configurable group spawn interval
    private final Pane gamePane;
    private final Vector2<Double>[] mainPath;
    private final int waveIndex;
    private final GameSettings gameSettings;

    //Images
    private final Image goblinImg;
    private final ImageView goblinView;
    private final Image knightImg;
    private final ImageView knightView;

    public Wave(int waveIndex, int knightCount, int goblinCount, int groupCount, int xPos, int yPos, Pane gamePane, Vector2<Double>[] mainPath, GameSettings gameSettings) {
        this.waveIndex = waveIndex;
        this.knightCount = knightCount;
        this.goblinCount = goblinCount;
        this.groupCount = groupCount;
        this.xPos = xPos;
        this.yPos = yPos;
        this.gamePane = gamePane;
        this.mainPath = mainPath;
        this.gameSettings = gameSettings;
        this.currentGroup = 0;
        this.currentEnemyCount = 0;
        this.isWaveComplete = false;
        this.activeEnemies = new ArrayList<>();
        this.enemySpawnTimer = 0;
        this.groupWaitTimer = 0;
        this.waitingForNextGroup = false;

        // Set configurable timing intervals
        if (gameSettings != null && gameSettings.waves != null) {
            this.enemySpawnInterval = gameSettings.waves.delayBetweenEnemies;
            this.groupSpawnInterval = gameSettings.waves.delayBetweenGroups;
            System.out.println("⏱️ Wave " + waveIndex + ": Using delays - Enemy: " + enemySpawnInterval + "s, Group: " + groupSpawnInterval + "s");
        } else {
            this.enemySpawnInterval = DEFAULT_ENEMY_SPAWN_INTERVAL;
            this.groupSpawnInterval = DEFAULT_GROUP_SPAWN_INTERVAL;
            System.out.println("⚠️ Wave " + waveIndex + ": Using default delays");
        }

        // Initialize images
        this.goblinImg = new Image("Assets/enemies/Goblin_Red.png");
        this.goblinView = new ImageView(goblinImg);
        this.knightImg = new Image("Assets/enemies/Warrior_Blue.png");
        this.knightView = new ImageView(knightImg);
    }

    // Getter methods for testing
    public int getWaveIndex() { return waveIndex; }
    public int getKnightCount() { return knightCount; }
    public int getGoblinCount() { return goblinCount; }
    public int getGroupCount() { return groupCount; }
    public int getCurrentGroup() { return currentGroup; }
    public int getCurrentEnemyCount() { return currentEnemyCount; }
    public double getEnemySpawnTimer() { return enemySpawnTimer; }
    public double getGroupWaitTimer() { return groupWaitTimer; }

    public void startWave() {
        System.out.println("[Wave] startWave called for waveIndex=" + waveIndex + ", groupCount=" + groupCount);
        currentGroup = 0;
        currentEnemyCount = 0;
        isWaveComplete = false;
        enemySpawnTimer = 0;
        groupWaitTimer = 0;
        waitingForNextGroup = false;
    }

    // This is the core game loop for this wave:
    //It determines whether to spawn enemies or wait between groups.
    //Once all groups have spawned, it marks the wave as complete.
    //Also cleans up dead enemies from the screen and memory.
    public void update(double deltaTime) {
        // Scale deltaTime by game speed
        double scaledDeltaTime = deltaTime * GameActionController.getInstance().getGameSpeed();

        // First, sync our activeEnemies list with the static Enemy.activeEnemies list
        activeEnemies.removeIf(enemy -> !Enemy.activeEnemies.contains(enemy));

        // Only spawn new enemies if wave is not complete
        if (!isWaveComplete && currentGroup < groupCount) {
            if (!waitingForNextGroup) {
                if (currentEnemyCount < (knightCount + goblinCount)) {
                    enemySpawnTimer += scaledDeltaTime;
                    if (enemySpawnTimer >= enemySpawnInterval) {
                        spawnEnemy();
                        enemySpawnTimer = 0;
                    }
                } else {
                    // All enemies for this group spawned, start waiting for next group
                    System.out.println("[Wave] Group " + currentGroup + " finished in waveIndex=" + waveIndex);
                    waitingForNextGroup = true;
                    groupWaitTimer = 0;
                }
            } else {
                groupWaitTimer += scaledDeltaTime;
                if (groupWaitTimer >= groupSpawnInterval) {
                    currentGroup++;
                    if (currentGroup < groupCount) {
                        System.out.println("[Wave] Starting group " + currentGroup + " in waveIndex=" + waveIndex);
                    }
                    currentEnemyCount = 0;
                    enemySpawnTimer = 0;
                    waitingForNextGroup = false;
                }
            }
        }

        // Update existing enemies and remove dead ones
        Iterator<Enemy> iterator = activeEnemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (!enemy.isAlive() || enemy.hasReachedEnd()) {
                // Let Die() handle the gold pouch spawning first
                if (!enemy.isAlive() && !enemy.hasReachedEnd()) {
                    enemy.Die();
                }
                // Then remove from game pane if not already removed
                if (enemy.getView() != null && enemy.getView().getParent() != null) {
                    gamePane.getChildren().removeAll(enemy.getView(), enemy.getHealthBar());
                }
                // Remove from both lists
                iterator.remove();
                Enemy.activeEnemies.remove(enemy);
                System.out.println("[Wave] Removed enemy from wave " + waveIndex + (enemy.hasReachedEnd() ? " (reached end)" : " (dead)"));
            }
        }

        // Check if wave is complete
        if (!isWaveComplete && currentGroup >= groupCount && activeEnemies.isEmpty()) {
            isWaveComplete = true;
            System.out.println("[Wave] Wave " + waveIndex + " is complete");
        }
    }

    // Spawns a single goblin or knight depending on currentEnemyCount.
    private void spawnEnemy() {
        System.out.println("[Wave] spawnEnemy called for waveIndex=" + waveIndex + ", currentGroup=" + currentGroup + ", currentEnemyCount=" + currentEnemyCount);

        // Get enemy settings from GameSettings
        GameSettings.Enemy enemySettings = null;
        if (gameSettings != null && gameSettings.enemy != null) {
            enemySettings = gameSettings.enemy;
        }

        // Calculate HP multiplier based on wave number (20% increase per wave)
        double hpMultiplier = 1.0 + (waveIndex - 1) * 0.2;

        int offset = activeEnemies.size() * 65;
        if (currentEnemyCount < goblinCount) {
            // Use configurable goblin stats or defaults
            int baseGoblinHP = (enemySettings != null) ? enemySettings.goblinHP : 20;
            double goblinSpeed = (enemySettings != null) ? enemySettings.goblinSpeed * 100 : 100.0; // Convert to internal speed units
            int goblinGold = (enemySettings != null) ? enemySettings.goblinGold : 5;
            
            int goblinHP = (int)(baseGoblinHP * hpMultiplier);
            
            Goblin goblin = new Goblin(xPos + offset, yPos, "Goblin", goblinHP, goblinSpeed, new ImageView(goblinImg), goblinGold);
            activeEnemies.add(goblin);
            gamePane.getChildren().addAll(goblin.getView(), goblin.getHealthBar());
            goblin.getView().setPickOnBounds(true);
            goblin.getView().setOnMouseEntered(e -> goblin.getHealthBar().setVisible(true));
            goblin.getView().setOnMouseExited(e -> goblin.getHealthBar().setVisible(false));
            goblin.moveAlong(mainPath);
            currentEnemyCount++;
            
            System.out.println("Spawned Goblin - HP: " + goblinHP + " (base: " + baseGoblinHP + "), Speed: " + goblinSpeed + ", Gold: " + goblinGold);

        } else if (currentEnemyCount < (goblinCount + knightCount)) {
            // Use configurable knight stats or defaults
            int baseKnightHP = (enemySettings != null) ? enemySettings.knightHP : 40;
            double knightSpeed = (enemySettings != null) ? enemySettings.knightSpeed * 100 : 80.0; // Convert to internal speed units
            int knightGold = (enemySettings != null) ? enemySettings.knightGold : 10;
            
            int knightHP = (int)(baseKnightHP * hpMultiplier);
            
            Knight knight = new Knight(xPos + offset, yPos, "Knight", knightHP, knightSpeed, new ImageView(knightImg), knightGold);
            activeEnemies.add(knight);
            Node knightView = knight.getView();
            gamePane.getChildren().addAll(knightView, knight.getHealthBar());
            knightView.setPickOnBounds(true);
            knightView.setOnMouseEntered(e -> knight.getHealthBar().setVisible(true));
            knightView.setOnMouseExited(e -> knight.getHealthBar().setVisible(false));
            knight.moveAlong(mainPath);
            currentEnemyCount++;
            
            System.out.println("Spawned Knight - HP: " + knightHP + " (base: " + baseKnightHP + "), Speed: " + knightSpeed + ", Gold: " + knightGold);
        }
    }

    public List<Enemy> getActiveEnemies() {
        return activeEnemies;
    }

    public boolean isWaveComplete() {
        return isWaveComplete && activeEnemies.isEmpty();
    }

}