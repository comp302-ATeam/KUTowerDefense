package Domain.GameFlow;

import Domain.GameObjects.Enemy;
import Domain.GameObjects.Goblin;
import Domain.GameObjects.Knight;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

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
    private final Timeline groupSpawner;        // Timer that spawns groups
    private final Timeline enemySpawner;        // timer that spawns enemies

    //Images
    private final Image goblinImg;
    private final ImageView goblinView;
    private final Image knightImg;
    private final ImageView knightView;

    public Wave(int knightCount, int goblinCount, int groupCount, int xPos, int yPos) {
        this.knightCount = knightCount;
        this.goblinCount = goblinCount;
        this.groupCount = groupCount;
        this.xPos = xPos;
        this.yPos = yPos;
        this.currentGroup = 0;
        this.isWaveComplete = false;
        this.activeEnemies = new ArrayList<>();

        // Initialize images
        this.goblinImg = new Image("Assets/enemies/Goblin_Red.png");
        this.goblinView = new ImageView(goblinImg);
        this.knightImg = new Image("Assets/enemies/Warrior_Blue.png");
        this.knightView = new ImageView(knightImg);

        // Create timeline for spawning groups
        this.groupSpawner = new Timeline(
                new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        spawnGroup();
                    }
                }),
                new KeyFrame(Duration.seconds(2.0), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //empty but necessary
                    }
                })
        );
        this.groupSpawner.setCycleCount(groupCount);

        // Create timeline for spawning enemies within a group
        this.enemySpawner = new Timeline(
                new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        spawnEnemy();
                    }
                }),
                new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // empty but necessary
                    }
                })
        );
        this.enemySpawner.setCycleCount(knightCount + goblinCount);
    }

    public void startWave() {
        currentGroup = 0;
        isWaveComplete = false;
        groupSpawner.play();
    }
    // At second 0: calls spawnGroup() method (starts a new group)
    //Wait 2 seconds
    //Repeats this loop for groupCount
    private void spawnGroup() {
        if (currentGroup < groupCount) {
            enemySpawner.play();
            currentGroup++;
        } else {
            isWaveComplete = true;
        }
    }

    // create enemies
    // At 0 seconds: calls spawnEnemy() method (creates a new enemy)
    //Wait 0.5 seconds
    //Repeats this loop for the total number of enemies
    private void spawnEnemy() {
        // Spawn goblins first
        if (activeEnemies.size() < goblinCount) {
            Goblin goblin = new Goblin(xPos, yPos, "Goblin", 100, 1, new ImageView(goblinImg));
            activeEnemies.add(goblin);
        }
        // Then spawn knights
        else if (activeEnemies.size() < (goblinCount + knightCount)) {
            Knight knight = new Knight(xPos, yPos, "Knight", 100, 1, new ImageView(knightImg));
            activeEnemies.add(knight);
        }
    }
    // Returns the list of all currently active enemies
    public List<Enemy> getActiveEnemies() {
        return activeEnemies;
    }
    // isWaveComplete: All groups have been spawned
    //activeEnemies.isEmpty(): All enemies have been defeated
    //Only returns true when BOTH conditions are met
    public boolean isWaveComplete() {
        return isWaveComplete && activeEnemies.isEmpty();
    }
    // Takes a deltaTime parameter (time since last update)
    //Uses an Iterator to safely remove dead enemies
    //Checks each enemy's status
    //Removes any enemy that is no longer alive
    public void update(double deltaTime) {
        Iterator<Enemy> iterator = activeEnemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (!enemy.isAlive()) {
                iterator.remove();
            }
        }
    }

}