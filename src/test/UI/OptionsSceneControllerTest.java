package UI;

import Domain.GameFlow.GameSettings;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for OptionsSceneController.applySettingsToUI method
 */
public class OptionsSceneControllerTest {

    private OptionsSceneController controller;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit for testing
        new JFXPanel();
    }

    @BeforeEach
    void setUp() throws Exception {
        controller = new OptionsSceneController();

        // Initialize all spinner controls with proper value factories
        initializeSpinners();

        // Initialize button controls
        initializeButtons();
    }

    private void initializeSpinners() throws Exception {
        // Wave Settings Spinners
        setSpinnerField("spinnerNumWaves", new Spinner<>(1, 100, 10));
        setSpinnerField("spinnerGroupsPerWave", new Spinner<>(1, 10, 3));
        setSpinnerField("spinnerEnemiesPerGroup", new Spinner<>(1, 20, 5));
        setSpinnerField("spinnerGoblinsPerGroup", new Spinner<>(0, 20, 3));
        setSpinnerField("spinnerKnightsPerGroup", new Spinner<>(0, 20, 2));
        setSpinnerField("spinnerDelayBetweenWaves", new Spinner<>(0.0, 30.0, 5.0, 0.1));
        setSpinnerField("spinnerDelayBetweenGroups", new Spinner<>(0.0, 30.0, 2.0, 0.1));
        setSpinnerField("spinnerDelayBetweenEnemies", new Spinner<>(0.0, 10.0, 0.5, 0.1));

        // Enemy Settings Spinners
        setSpinnerField("spinnerGoblinHP", new Spinner<>(1, 500, 20));
        setSpinnerField("spinnerKnightHP", new Spinner<>(1, 500, 40));
        setSpinnerField("spinnerGoblinGold", new Spinner<>(1, 100, 5));
        setSpinnerField("spinnerKnightGold", new Spinner<>(1, 100, 10));
        setSpinnerField("spinnerGoblinSpeed", new Spinner<>(0.1, 10.0, 1.0, 0.1));
        setSpinnerField("spinnerKnightSpeed", new Spinner<>(0.1, 10.0, 0.8, 0.1));

        // Tower Settings Spinners
        setSpinnerField("spinnerArcherCost", new Spinner<>(1, 500, 50));
        setSpinnerField("spinnerArtilleryCost", new Spinner<>(1, 500, 70));
        setSpinnerField("spinnerMageCost", new Spinner<>(1, 500, 60));
        setSpinnerField("spinnerArcherRange", new Spinner<>(10, 500, 150));
        setSpinnerField("spinnerArtilleryRange", new Spinner<>(10, 500, 120));
        setSpinnerField("spinnerMageRange", new Spinner<>(10, 500, 140));
        setSpinnerField("spinnerAoeRadius", new Spinner<>(10, 300, 50));
        setSpinnerField("spinnerArrowDamage", new Spinner<>(1, 100, 5));
        setSpinnerField("spinnerArtilleryDamage", new Spinner<>(1, 100, 10));
        setSpinnerField("spinnerMagicDamage", new Spinner<>(1, 100, 7));
        setSpinnerField("spinnerArcherFireRate", new Spinner<>(0.1, 5.0, 1.5, 0.1));
        setSpinnerField("spinnerArtilleryFireRate", new Spinner<>(0.1, 5.0, 2.0, 0.1));
        setSpinnerField("spinnerMageFireRate", new Spinner<>(0.1, 5.0, 1.8, 0.1));

        // Player Settings Spinners
        setSpinnerField("spinnerPlayerLives", new Spinner<>(1, 500, 100));
        setSpinnerField("spinnerPlayerGold", new Spinner<>(0, 1000, 100));
    }

    private void initializeButtons() throws Exception {
        setButtonField("musicToggleButton", new Button("Music: ON"));
        setButtonField("soundEffectsToggleButton", new Button("SFX: ON"));
    }

    private void setSpinnerField(String fieldName, Spinner<?> spinner) throws Exception {
        Field field = OptionsSceneController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(controller, spinner);
    }

    private void setButtonField(String fieldName, Button button) throws Exception {
        Field field = OptionsSceneController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(controller, button);
    }

    private GameSettings createTestSettings() {
        GameSettings settings = new GameSettings();
        settings.waves = new GameSettings.Waves();
        settings.enemy = new GameSettings.Enemy();
        settings.tower = new GameSettings.Tower();
        settings.player = new GameSettings.Player();
        return settings;
    }

    /**
     * Test Case 1: Normal values with music ON and SFX OFF
     * Tests that the method correctly applies typical game settings to all UI controls
     */
    @Test
    void testApplySettingsToUI_NormalValues() throws Exception {
        // Arrange
        GameSettings settings = createTestSettings();

        // Set wave settings
        settings.waves.numWaves = 15;
        settings.waves.groupsPerWave = 4;
        settings.waves.enemiesPerGroup = 8;
        settings.waves.goblinsPerGroup = 5;
        settings.waves.knightsPerGroup = 3;
        settings.waves.delayBetweenWaves = 7.5;
        settings.waves.delayBetweenGroups = 3.2;
        settings.waves.delayBetweenEnemies = 0.8;

        // Set enemy settings
        settings.enemy.goblinHP = 25;
        settings.enemy.knightHP = 50;
        settings.enemy.goblinGold = 8;
        settings.enemy.knightGold = 15;
        settings.enemy.goblinSpeed = 1.2;
        settings.enemy.knightSpeed = 0.9;

        // Set tower settings
        settings.tower.archerCost = 60;
        settings.tower.artilleryCost = 80;
        settings.tower.mageCost = 70;
        settings.tower.archerRange = 160;
        settings.tower.artilleryRange = 130;
        settings.tower.mageRange = 150;
        settings.tower.aoeRadius = 60;
        settings.tower.arrowDamage = 8;
        settings.tower.artilleryDamage = 12;
        settings.tower.magicDamage = 9;
        settings.tower.archerFireRate = 1.8;
        settings.tower.artilleryFireRate = 2.2;
        settings.tower.mageFireRate = 2.0;

        // Set player settings
        settings.player.startingHP = 120;
        settings.player.startingGold = 150;
        settings.player.musicOn = true;
        settings.player.sfxOn = false;

        // Act - Use reflection to call private method
        Platform.runLater(() -> {
            try {
                callApplySettingsToUI(settings);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Thread.sleep(100); // Wait for JavaFX thread

        // Assert - Wave settings
        assertEquals(15, getSpinnerValue("spinnerNumWaves"));
        assertEquals(4, getSpinnerValue("spinnerGroupsPerWave"));
        assertEquals(8, getSpinnerValue("spinnerEnemiesPerGroup"));
        assertEquals(5, getSpinnerValue("spinnerGoblinsPerGroup"));
        assertEquals(3, getSpinnerValue("spinnerKnightsPerGroup"));
        assertEquals(7.5, getSpinnerValue("spinnerDelayBetweenWaves"));
        assertEquals(3.2, getSpinnerValue("spinnerDelayBetweenGroups"));
        assertEquals(0.8, getSpinnerValue("spinnerDelayBetweenEnemies"));

        // Assert - Enemy settings
        assertEquals(25, getSpinnerValue("spinnerGoblinHP"));
        assertEquals(50, getSpinnerValue("spinnerKnightHP"));
        assertEquals(8, getSpinnerValue("spinnerGoblinGold"));
        assertEquals(15, getSpinnerValue("spinnerKnightGold"));
        assertEquals(1.2, getSpinnerValue("spinnerGoblinSpeed"));
        assertEquals(0.9, getSpinnerValue("spinnerKnightSpeed"));

        // Assert - Tower settings
        assertEquals(60, getSpinnerValue("spinnerArcherCost"));
        assertEquals(80, getSpinnerValue("spinnerArtilleryCost"));
        assertEquals(70, getSpinnerValue("spinnerMageCost"));
        assertEquals(160, getSpinnerValue("spinnerArcherRange"));
        assertEquals(130, getSpinnerValue("spinnerArtilleryRange"));
        assertEquals(150, getSpinnerValue("spinnerMageRange"));
        assertEquals(60, getSpinnerValue("spinnerAoeRadius"));
        assertEquals(8, getSpinnerValue("spinnerArrowDamage"));
        assertEquals(12, getSpinnerValue("spinnerArtilleryDamage"));
        assertEquals(9, getSpinnerValue("spinnerMagicDamage"));
        assertEquals(1.8, getSpinnerValue("spinnerArcherFireRate"));
        assertEquals(2.2, getSpinnerValue("spinnerArtilleryFireRate"));
        assertEquals(2.0, getSpinnerValue("spinnerMageFireRate"));

        // Assert - Player settings
        assertEquals(120, getSpinnerValue("spinnerPlayerLives"));
        assertEquals(150, getSpinnerValue("spinnerPlayerGold"));

        // Assert - Audio settings
        assertEquals("Music: ON", getButtonText("musicToggleButton"));
        assertEquals("SFX: OFF", getButtonText("soundEffectsToggleButton"));
        assertTrue(getBooleanField("isMusicOn"));
        assertFalse(getBooleanField("isSfxOn"));
    }

    /**
     * Test Case 2: Edge/boundary values with both audio settings OFF
     * Tests minimum and maximum allowed values for various settings
     */
    @Test
    void testApplySettingsToUI_EdgeValues() throws Exception {
        // Arrange
        GameSettings settings = createTestSettings();

        // Set edge values (minimums and maximums)
        settings.waves.numWaves = 1; // minimum
        settings.waves.groupsPerWave = 10; // maximum
        settings.waves.enemiesPerGroup = 1; // minimum
        settings.waves.goblinsPerGroup = 20; // maximum
        settings.waves.knightsPerGroup = 0; // minimum
        settings.waves.delayBetweenWaves = 30.0; // maximum
        settings.waves.delayBetweenGroups = 0.0; // minimum
        settings.waves.delayBetweenEnemies = 10.0; // maximum

        settings.enemy.goblinHP = 500; // maximum
        settings.enemy.knightHP = 1; // minimum
        settings.enemy.goblinGold = 100; // maximum
        settings.enemy.knightGold = 1; // minimum
        settings.enemy.goblinSpeed = 10.0; // maximum
        settings.enemy.knightSpeed = 0.1; // minimum

        settings.tower.archerCost = 1; // minimum
        settings.tower.artilleryCost = 500; // maximum
        settings.tower.mageCost = 1; // minimum
        settings.tower.archerRange = 500; // maximum
        settings.tower.artilleryRange = 10; // minimum
        settings.tower.mageRange = 500; // maximum
        settings.tower.aoeRadius = 10; // minimum
        settings.tower.arrowDamage = 100; // maximum
        settings.tower.artilleryDamage = 1; // minimum
        settings.tower.magicDamage = 100; // maximum
        settings.tower.archerFireRate = 0.1; // minimum
        settings.tower.artilleryFireRate = 5.0; // maximum
        settings.tower.mageFireRate = 0.1; // minimum

        settings.player.startingHP = 1; // minimum
        settings.player.startingGold = 1000; // maximum
        settings.player.musicOn = false;
        settings.player.sfxOn = false;

        // Act - Use reflection to call private method
        Platform.runLater(() -> {
            try {
                callApplySettingsToUI(settings);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Thread.sleep(100); // Wait for JavaFX thread

        // Assert - Key edge values
        assertEquals(1, getSpinnerValue("spinnerNumWaves"));
        assertEquals(10, getSpinnerValue("spinnerGroupsPerWave"));
        assertEquals(20, getSpinnerValue("spinnerGoblinsPerGroup"));
        assertEquals(0, getSpinnerValue("spinnerKnightsPerGroup"));
        assertEquals(30.0, getSpinnerValue("spinnerDelayBetweenWaves"));
        assertEquals(0.0, getSpinnerValue("spinnerDelayBetweenGroups"));
        assertEquals(10.0, getSpinnerValue("spinnerDelayBetweenEnemies"));

        assertEquals(500, getSpinnerValue("spinnerGoblinHP"));
        assertEquals(1, getSpinnerValue("spinnerKnightHP"));
        assertEquals(10.0, getSpinnerValue("spinnerGoblinSpeed"));
        assertEquals(0.1, getSpinnerValue("spinnerKnightSpeed"));

        assertEquals(1, getSpinnerValue("spinnerArcherCost"));
        assertEquals(500, getSpinnerValue("spinnerArtilleryCost"));
        assertEquals(500, getSpinnerValue("spinnerArcherRange"));
        assertEquals(10, getSpinnerValue("spinnerArtilleryRange"));
        assertEquals(100, getSpinnerValue("spinnerArrowDamage"));
        assertEquals(1, getSpinnerValue("spinnerArtilleryDamage"));
        assertEquals(0.1, getSpinnerValue("spinnerArcherFireRate"));
        assertEquals(5.0, getSpinnerValue("spinnerArtilleryFireRate"));

        assertEquals(1, getSpinnerValue("spinnerPlayerLives"));
        assertEquals(1000, getSpinnerValue("spinnerPlayerGold"));

        // Assert - Both audio settings OFF
        assertEquals("Music: OFF", getButtonText("musicToggleButton"));
        assertEquals("SFX: OFF", getButtonText("soundEffectsToggleButton"));
        assertFalse(getBooleanField("isMusicOn"));
        assertFalse(getBooleanField("isSfxOn"));
    }

    /**
     * Test Case 3: Default-like values with music OFF and SFX ON
     * Tests settings that are close to default values but with different audio configuration
     */
    @Test
    void testApplySettingsToUI_DefaultLikeValues() throws Exception {
        // Arrange
        GameSettings settings = createTestSettings();

        // Set default-like values
        settings.waves.numWaves = 10;
        settings.waves.groupsPerWave = 3;
        settings.waves.enemiesPerGroup = 5;
        settings.waves.goblinsPerGroup = 3;
        settings.waves.knightsPerGroup = 2;
        settings.waves.delayBetweenWaves = 5.0;
        settings.waves.delayBetweenGroups = 2.0;
        settings.waves.delayBetweenEnemies = 0.5;

        settings.enemy.goblinHP = 20;
        settings.enemy.knightHP = 40;
        settings.enemy.goblinGold = 5;
        settings.enemy.knightGold = 10;
        settings.enemy.goblinSpeed = 1.0;
        settings.enemy.knightSpeed = 0.8;

        settings.tower.archerCost = 50;
        settings.tower.artilleryCost = 70;
        settings.tower.mageCost = 60;
        settings.tower.archerRange = 150;
        settings.tower.artilleryRange = 120;
        settings.tower.mageRange = 140;
        settings.tower.aoeRadius = 50;
        settings.tower.arrowDamage = 5;
        settings.tower.artilleryDamage = 10;
        settings.tower.magicDamage = 7;
        settings.tower.archerFireRate = 1.5;
        settings.tower.artilleryFireRate = 2.0;
        settings.tower.mageFireRate = 1.8;

        settings.player.startingHP = 100;
        settings.player.startingGold = 100;
        settings.player.musicOn = false;
        settings.player.sfxOn = true;

        // Act - Use reflection to call private method
        Platform.runLater(() -> {
            try {
                callApplySettingsToUI(settings);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Thread.sleep(100); // Wait for JavaFX thread

        // Assert - Sample of default values
        assertEquals(10, getSpinnerValue("spinnerNumWaves"));
        assertEquals(3, getSpinnerValue("spinnerGroupsPerWave"));
        assertEquals(5, getSpinnerValue("spinnerEnemiesPerGroup"));
        assertEquals(20, getSpinnerValue("spinnerGoblinHP"));
        assertEquals(40, getSpinnerValue("spinnerKnightHP"));
        assertEquals(50, getSpinnerValue("spinnerArcherCost"));
        assertEquals(70, getSpinnerValue("spinnerArtilleryCost"));
        assertEquals(100, getSpinnerValue("spinnerPlayerLives"));
        assertEquals(100, getSpinnerValue("spinnerPlayerGold"));

        // Assert - Audio settings (music OFF, SFX ON)
        assertEquals("Music: OFF", getButtonText("musicToggleButton"));
        assertEquals("SFX: ON", getButtonText("soundEffectsToggleButton"));
        assertFalse(getBooleanField("isMusicOn"));
        assertTrue(getBooleanField("isSfxOn"));
    }

    // Helper methods for accessing private fields and getting values
    private Object getSpinnerValue(String fieldName) throws Exception {
        Field field = OptionsSceneController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        Spinner<?> spinner = (Spinner<?>) field.get(controller);
        return spinner.getValue();
    }

    private String getButtonText(String fieldName) throws Exception {
        Field field = OptionsSceneController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        Button button = (Button) field.get(controller);
        return button.getText();
    }

    private boolean getBooleanField(String fieldName) throws Exception {
        Field field = OptionsSceneController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (Boolean) field.get(controller);
    }

    // Helper method to call private applySettingsToUI method using reflection
    private void callApplySettingsToUI(GameSettings settings) throws Exception {
        java.lang.reflect.Method method = OptionsSceneController.class.getDeclaredMethod("applySettingsToUI", GameSettings.class);
        method.setAccessible(true);
        method.invoke(controller, settings);
    }
}