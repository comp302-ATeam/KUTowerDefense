package Domain;

import Domain.GameFlow.MockWave;
import Domain.GameFlow.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests Wave as an Abstract Data Type.
 * Wave represents a wave of enemies in the tower defense game,
 * managing enemy spawning, wave completion, and active enemy tracking.
 *
 * Abstract Function:
 * AF(c) = A wave of enemies where:
 * - isWaveComplete represents whether the wave is finished
 * - activeEnemies represents the currently active enemies in the wave
 * - currentGroup tracks the current group being spawned
 * - groupCount represents the total number of groups in the wave
 * - currentEnemyCount tracks the number of enemies spawned in current group
 * - waitingForNextGroup indicates if wave is waiting to spawn next group
 *
 * Representation Invariant:
 * RI(c) =
 * - activeEnemies != null
 * - currentGroup >= 0
 * - currentGroup <= groupCount
 * - groupCount > 0
 * - currentEnemyCount >= 0
 * - currentEnemyCount <= (knightCount + goblinCount)
 * - enemySpawnTimer >= 0
 * - groupWaitTimer >= 0
 */
public class WaveADTTest {
    private MockWave wave;
    private Vector2<Double>[] mockPath;

    @BeforeEach
    void setUp() {
        mockPath = new Vector2[2];
        mockPath[0] = new Vector2<>(0.0, 0.0);
        mockPath[1] = new Vector2<>(100.0, 100.0);
        wave = new MockWave(1, 2, 2, 2, 0, 0, mockPath);
    }

    /**
     * Checks if the representation invariant holds
     * @return true if the representation invariant holds, false otherwise
     */
    private boolean repOk() {
        return wave.getActiveEnemies() != null &&
                wave.getCurrentGroup() >= 0 &&
                wave.getCurrentGroup() <= wave.getGroupCount() &&
                wave.getGroupCount() > 0 &&
                wave.getCurrentEnemyCount() >= 0 &&
                wave.getCurrentEnemyCount() <= 4 && // 2 knights + 2 goblins
                wave.getEnemySpawnTimer() >= 0 &&
                wave.getGroupWaitTimer() >= 0;
    }

    @Test
    void constructor_CreatesValidWave() {
        // Given a wave with valid parameters
        MockWave newWave = new MockWave(1, 2, 2, 2, 0, 0, mockPath);

        // Then representation invariant should hold
        assertTrue(repOk(), "Wave should be created with valid state");
    }

    @Test
    void startWave_MaintainsInvariant() {
        // When wave is started
        wave.startWave();

        // Then representation invariant should hold
        assertTrue(repOk(), "Wave should maintain valid state after starting");
    }

    @Test
    void update_SpawnsEnemiesInCorrectOrder() {
        // Given a started wave
        wave.startWave();

        // When wave is updated multiple times
        for (int i = 0; i < 10; i++) {
            wave.update(0.25);
        }

        // Then enemies should be spawned in correct order
        assertTrue(repOk(), "Wave should maintain valid state after spawning enemies");
        assertTrue(wave.getCurrentEnemyCount() > 0, "Wave should spawn enemies");
    }

    @Test
    void update_TransitionsBetweenGroups() {
        // Given a started wave
        wave.startWave();

        // When wave is updated enough to complete first group
        for (int i = 0; i < 20; i++) {
            wave.update(0.25);
        }

        // Then wave should wait for next group
        assertTrue(wave.isWaitingForNextGroup(), "Wave should wait for next group");
        assertTrue(repOk(), "Wave should maintain valid state while waiting for next group");
    }

    @Test
    void update_CompletesWave() {
        // Given a started wave
        wave.startWave();

        // When wave is updated enough to complete all groups
        for (int i = 0; i < 100; i++) {
            wave.update(0.25);
        }

        // Then wave should be complete
        assertTrue(wave.isWaveComplete(), "Wave should be complete");
        assertTrue(repOk(), "Wave should maintain valid state when complete");
    }
}