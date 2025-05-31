//Domain.GameFlow;
//
//import Domain.GameFlow.GameActionController;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class GameActionControllerTest {
//
//    GameActionController controller;
//
//    @BeforeEach
//    void setUp() {
//        controller = new GameActionController();
//        controller.isPaused = false;
//        controller.gameSpeed = 1.0;
//    }
//
//    @Test
//    void testSpeedUpWhenNotPaused() {
//        controller.speedUpGame(); // from 1.0 to 2.0
//        assertEquals(2.0, controller.gameSpeed);
//    }
//
//    @Test
//    void testSpeedUpWhenPaused() {
//        controller.isPaused = true;
//        controller.speedUpGame(); // should not change
//        assertEquals(1.0, controller.gameSpeed);
//    }
//
//    @Test
//    void testSpeedUpExceedsMaxSpeedResetsToDefault() {
//        controller.gameSpeed = 8.0; // Already at max
//        controller.speedUpGame(); // Should reset to default (1.0)
//        assertEquals(controller.defaultSpeed, controller.gameSpeed);
//    }
//}