package Domain;

import Domain.GameObjects.Enemy;
import Domain.GameObjects.MockEnemy;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class WaveTest {
    private Wave wave;
    private Pane mockPane;
    private Vector2<Double>[] mockPath;
    private static final double ENEMY_SPAWN_INTERVAL = 0.25;
    private static final double GROUP_SPAWN_INTERVAL = 45.0;

    @BeforeEach
    void setUp() {
        // Initialize with 2 knights, 2 goblins, and 2 groups
        mockPane = new Pane();
        mockPath = new Vector2[2];
        mockPath[0] = new Vector2<>(0.0, 0.0);
        mockPath[1] = new Vector2<>(100.0, 100.0);
        wave = new Wave(1, 2, 2, 2, 0, 0, mockPane, mockPath);
    }

    @Test
    void update_SpawnsEnemiesAtCorrectIntervals() {
        // Given a fresh wave
        wave.startWave();

        // When updating with time less than spawn interval
        wave.update(ENEMY_SPAWN_INTERVAL / 2);

        // Then no enemies should be spawned
        assertEquals(0, wave.getActiveEnemies().size());

        // When updating with full spawn interval
        wave.update(ENEMY_SPAWN_INTERVAL);

        // Then one enemy should be spawned
        assertEquals(1, wave.getActiveEnemies().size());
    }

    @Test
    void update_AdvancesToNextGroupAfterWait() {
        // Given a wave that has completed its first group
        wave.startWave();
        // Spawn all enemies in first group
        for (int i = 0; i < 4; i++) {
            wave.update(ENEMY_SPAWN_INTERVAL);
        }

        // When waiting for less than group interval
        wave.update(GROUP_SPAWN_INTERVAL / 2);

        // Then should still be in first group
        assertFalse(wave.isWaveComplete());

        // When waiting for full group interval
        wave.update(GROUP_SPAWN_INTERVAL);

        // Then should start spawning second group
        wave.update(ENEMY_SPAWN_INTERVAL);
        assertEquals(5, wave.getActiveEnemies().size());
    }

    @Test
    void update_RemovesDeadEnemies() {
        // Given a wave with some enemies
        wave.startWave();
        wave.update(ENEMY_SPAWN_INTERVAL);
        List<Enemy> enemies = wave.getActiveEnemies();
        assertEquals(1, enemies.size());

        // When an enemy dies
        MockEnemy mockEnemy = new MockEnemy(0, 0, "Mock", 100, 1);
        mockEnemy.takeDamage(100); // Kill the enemy

        // Then it should be removed on next update
        wave.update(ENEMY_SPAWN_INTERVAL);
        assertTrue(wave.getActiveEnemies().isEmpty());
    }
}