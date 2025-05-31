package Domain;

import Domain.GameFlow.Vector2;
import Domain.GameFlow.Wave;
import Domain.GameObjects.Arrow;
import Domain.GameObjects.Enemy;
import Domain.GameObjects.MockEnemy;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class WaveTest extends ApplicationTest {
    private Wave wave;
    private Pane mockPane;
    private Vector2<Double>[] mockPath;



    @BeforeEach
    void setUp() {
        // Initialize with 2 knights, 2 goblins, and 2 groups
        mockPane = new Pane();
        mockPath = new Vector2[2];
        mockPath[0] = new Vector2<>(0.0, 0.0);
        mockPath[1] = new Vector2<>(100.0, 100.0);
        wave = new Wave(1, 2, 2, 2, 0, 0, mockPane, mockPath);
    }

    @Override
    public void start(javafx.stage.Stage stage) throws Exception {

    }


    /**
     * requires: wave is initialized
     * modifies: this.isWaveComplete, this.activeEnemies
     * effects: returns true if wave is marked complete AND no active enemies remain
     */
    @Test
    void isWaveComplete_ReturnsFalse_WhenWaveNotStarted() {
        // Given a fresh wave that hasn't started

        // Then wave should not be complete
        assertFalse(wave.isWaveComplete());
    }

    @Test
    void isWaveComplete_ReturnsFalse_WhenEnemiesStillActive() {
        // Given a wave with active enemies
        wave.startWave();
        wave.update(0.25); // Spawn first enemy

        // Then wave should not be complete
        assertFalse(wave.isWaveComplete());
    }

    @Test
    void isWaveComplete_ReturnsTrue_WhenWaveCompleteAndNoEnemies() {
        // Given a wave that has completed all groups
        wave.startWave();
        // Spawn and kill all enemies
        for (int i = 0; i < 8; i++) { // 4 enemies per group, 2 groups
            wave.update(0.25);
        }
        // Wait for group interval
        wave.update(45.0);
        // Kill all enemies
        for (Enemy enemy : wave.getActiveEnemies()) {
            enemy.takeDamage(new Arrow(0,0,1000,null));
        }
        wave.update(0.25); // Update to remove dead enemies

        // Then wave should be complete
        assertTrue(wave.isWaveComplete());
    }
}