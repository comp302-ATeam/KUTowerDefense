// Test file for GameActionController using Singleton pattern
// Uncomment and ensure JUnit dependencies are available in your project
package java;

import Domain.GameFlow.GameActionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameActionControllerTest {

    GameActionController controller;

    @BeforeEach
    void setUp() {
        // Use singleton instance instead of creating new instance
        controller = GameActionController.getInstance();
        controller.setPaused(false);
        controller.setGameSpeed(1.0);
    }

    @Test
    void testSpeedUpWhenNotPaused() {
        controller.speedUpGame(); // from 1.0 to 2.0
        assertEquals(2.0, controller.getGameSpeed());
    }

    @Test
    void testSpeedUpWhenPaused() {
        controller.setPaused(true);
        controller.speedUpGame(); // should not change
        assertEquals(1.0, controller.getGameSpeed());
    }

    @Test
    void testSpeedUpExceedsMaxSpeedResetsToDefault() {
        controller.setGameSpeed(8.0); // Already at max
        controller.speedUpGame(); // Should reset to default (1.0)
        assertEquals(GameActionController.defaultSpeed, controller.getGameSpeed());
    }
}
