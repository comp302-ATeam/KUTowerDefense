package Domain;
import Domain.GameFlow.MockWave;
import Domain.GameFlow.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WaveTest {
    private MockWave wave;
    private Vector2<Double>[] mockPath;

    @BeforeEach
    void setUp() {
        // Initialize with 2 knights, 2 goblins, and 2 groups
        mockPath = new Vector2[2];
        mockPath[0] = new Vector2<>(0.0, 0.0);
        mockPath[1] = new Vector2<>(100.0, 100.0);
        wave = new MockWave(1, 2, 2, 2, 0, 0, mockPath);
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
    void isWaveComplete_ReturnsTrue_WhenManuallySetCompleteAndNoEnemies() {
        // Given a wave that is started
        wave.startWave();

        // Manually set wave as complete and clear all enemies
        wave.setWaveComplete(true);
        wave.clearEnemies();

        // Then wave should be complete
        assertTrue(wave.isWaveComplete());
    }
}
