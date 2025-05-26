package UI;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.SpinnerValueFactory;
import java.io.*;
import Domain.GameFlow.GameSettings;

public class OptionsSceneController {

    @FXML private Button backButton;
    @FXML private Button musicToggleButton;
    @FXML private Button soundEffectsToggleButton;
    @FXML private Button saveButton;
    @FXML private Button restoreDefaultsButton;

    // Wave Settings
    @FXML private Spinner<Integer> spinnerNumWaves;
    @FXML private Spinner<Integer> spinnerGroupsPerWave;
    @FXML private Spinner<Integer> spinnerEnemiesPerGroup;
    @FXML private Spinner<Integer> spinnerGoblinsPerGroup;
    @FXML private Spinner<Integer> spinnerKnightsPerGroup;
    @FXML private Spinner<Double> spinnerDelayBetweenWaves;
    @FXML private Spinner<Double> spinnerDelayBetweenGroups;
    @FXML private Spinner<Double> spinnerDelayBetweenEnemies;

    // Enemy Settings
    @FXML private Spinner<Integer> spinnerGoblinHP;
    @FXML private Spinner<Integer> spinnerKnightHP;
    @FXML private Spinner<Integer> spinnerGoblinGold;
    @FXML private Spinner<Integer> spinnerKnightGold;
    @FXML private Spinner<Double> spinnerGoblinSpeed;
    @FXML private Spinner<Double> spinnerKnightSpeed;

    // Tower Settings
    @FXML private Spinner<Integer> spinnerArcherCost;
    @FXML private Spinner<Integer> spinnerArtilleryCost;
    @FXML private Spinner<Integer> spinnerMageCost;
    @FXML private Spinner<Integer> spinnerArcherRange;
    @FXML private Spinner<Integer> spinnerArtilleryRange;
    @FXML private Spinner<Integer> spinnerMageRange;
    @FXML private Spinner<Integer> spinnerAoeRadius;
    @FXML private Spinner<Integer> spinnerArrowDamage;
    @FXML private Spinner<Integer> spinnerArtilleryDamage;
    @FXML private Spinner<Integer> spinnerMagicDamage;
    @FXML private Spinner<Double> spinnerArcherFireRate;
    @FXML private Spinner<Double> spinnerArtilleryFireRate;
    @FXML private Spinner<Double> spinnerMageFireRate;

    // Player Settings
    @FXML private Spinner<Integer> spinnerPlayerHP;
    @FXML private Spinner<Integer> spinnerPlayerGold;

    private boolean isMusicOn = true;
    private boolean isSfxOn = true;

    @FXML
    private void initialize() {
        initializeDefaults();  // Always set up value factories first

        GameSettings settings = loadSettingsFromDisk();
        if (settings != null) {
            applySettingsToUI(settings);  // Now safe to set values
        }
    }


    private void initIntegerSpinner(Spinner<Integer> spinner, int min, int max, int initial) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initial));
    }

    private void initDoubleSpinner(Spinner<Double> spinner, double min, double max, double initial, double step) {
        spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(min, max, initial, step));
    }

    private void initializeDefaults() {
        // Wave Settings
        initIntegerSpinner(spinnerNumWaves, 1, 100, 10);
        initIntegerSpinner(spinnerGroupsPerWave, 1, 10, 3);
        initIntegerSpinner(spinnerEnemiesPerGroup, 1, 20, 5);
        initIntegerSpinner(spinnerGoblinsPerGroup, 0, 20, 3);
        initIntegerSpinner(spinnerKnightsPerGroup, 0, 20, 2);
        initDoubleSpinner(spinnerDelayBetweenWaves, 0.0, 30.0, 5.0, 0.1);
        initDoubleSpinner(spinnerDelayBetweenGroups, 0.0, 30.0, 2.0, 0.1);
        initDoubleSpinner(spinnerDelayBetweenEnemies, 0.0, 10.0, 0.5, 0.1);

        // Enemy Settings
        initIntegerSpinner(spinnerGoblinHP, 1, 500, 20);
        initIntegerSpinner(spinnerKnightHP, 1, 500, 40);
        initIntegerSpinner(spinnerGoblinGold, 1, 100, 5);
        initIntegerSpinner(spinnerKnightGold, 1, 100, 10);
        initDoubleSpinner(spinnerGoblinSpeed, 0.1, 10.0, 1.0, 0.1);
        initDoubleSpinner(spinnerKnightSpeed, 0.1, 10.0, 0.8, 0.1);

        // Tower Settings
        initIntegerSpinner(spinnerArcherCost, 1, 500, 50);
        initIntegerSpinner(spinnerArtilleryCost, 1, 500, 70);
        initIntegerSpinner(spinnerMageCost, 1, 500, 60);
        initIntegerSpinner(spinnerArcherRange, 10, 500, 150);
        initIntegerSpinner(spinnerArtilleryRange, 10, 500, 120);
        initIntegerSpinner(spinnerMageRange, 10, 500, 140);
        initIntegerSpinner(spinnerAoeRadius, 10, 300, 50);
        initIntegerSpinner(spinnerArrowDamage, 1, 100, 5);
        initIntegerSpinner(spinnerArtilleryDamage, 1, 100, 10);
        initIntegerSpinner(spinnerMagicDamage, 1, 100, 7);
        initDoubleSpinner(spinnerArcherFireRate, 0.1, 5.0, 1.5, 0.1);
        initDoubleSpinner(spinnerArtilleryFireRate, 0.1, 5.0, 2.0, 0.1);
        initDoubleSpinner(spinnerMageFireRate, 0.1, 5.0, 1.8, 0.1);

        // Player Settings
        initIntegerSpinner(spinnerPlayerHP, 1, 500, 100);
        initIntegerSpinner(spinnerPlayerGold, 0, 1000, 100);
    }

    private GameSettings gatherSettingsFromUI() {
        GameSettings s = new GameSettings();
        s.waves = new GameSettings.Waves();
        s.enemy = new GameSettings.Enemy();
        s.tower = new GameSettings.Tower();
        s.player = new GameSettings.Player();

        s.waves.numWaves = spinnerNumWaves.getValue();
        s.waves.groupsPerWave = spinnerGroupsPerWave.getValue();
        s.waves.enemiesPerGroup = spinnerEnemiesPerGroup.getValue();
        s.waves.goblinsPerGroup = spinnerGoblinsPerGroup.getValue();
        s.waves.knightsPerGroup = spinnerKnightsPerGroup.getValue();
        s.waves.delayBetweenWaves = spinnerDelayBetweenWaves.getValue();
        s.waves.delayBetweenGroups = spinnerDelayBetweenGroups.getValue();
        s.waves.delayBetweenEnemies = spinnerDelayBetweenEnemies.getValue();

        s.enemy.goblinHP = spinnerGoblinHP.getValue();
        s.enemy.knightHP = spinnerKnightHP.getValue();
        s.enemy.goblinGold = spinnerGoblinGold.getValue();
        s.enemy.knightGold = spinnerKnightGold.getValue();
        s.enemy.goblinSpeed = spinnerGoblinSpeed.getValue();
        s.enemy.knightSpeed = spinnerKnightSpeed.getValue();

        s.tower.archerCost = spinnerArcherCost.getValue();
        s.tower.artilleryCost = spinnerArtilleryCost.getValue();
        s.tower.mageCost = spinnerMageCost.getValue();
        s.tower.archerRange = spinnerArcherRange.getValue();
        s.tower.artilleryRange = spinnerArtilleryRange.getValue();
        s.tower.mageRange = spinnerMageRange.getValue();
        s.tower.aoeRadius = spinnerAoeRadius.getValue();
        s.tower.arrowDamage = spinnerArrowDamage.getValue();
        s.tower.artilleryDamage = spinnerArtilleryDamage.getValue();
        s.tower.magicDamage = spinnerMagicDamage.getValue();
        s.tower.archerFireRate = spinnerArcherFireRate.getValue();
        s.tower.artilleryFireRate = spinnerArtilleryFireRate.getValue();
        s.tower.mageFireRate = spinnerMageFireRate.getValue();

        s.player.startingHP = spinnerPlayerHP.getValue();
        s.player.startingGold = spinnerPlayerGold.getValue();
        s.player.musicOn = isMusicOn;
        s.player.sfxOn = isSfxOn;

        return s;
    }

    /**
     * Requires: s is not null and all fields in s (waves, enemy, tower, player) are not null.
     * Modifies: The values of all Spinner controls in the UI, the text of musicToggleButton and soundEffectsToggleButton, and the fields isMusicOn and isSfxOn.
     * Effects: Updates all UI controls to reflect the values in the provided GameSettings object s. Sets the toggle buttons' text to match the music and SFX state.
     */

    private void applySettingsToUI(GameSettings s) {
        spinnerNumWaves.getValueFactory().setValue(s.waves.numWaves);
        spinnerGroupsPerWave.getValueFactory().setValue(s.waves.groupsPerWave);
        spinnerEnemiesPerGroup.getValueFactory().setValue(s.waves.enemiesPerGroup);
        spinnerGoblinsPerGroup.getValueFactory().setValue(s.waves.goblinsPerGroup);
        spinnerKnightsPerGroup.getValueFactory().setValue(s.waves.knightsPerGroup);
        spinnerDelayBetweenWaves.getValueFactory().setValue(s.waves.delayBetweenWaves);
        spinnerDelayBetweenGroups.getValueFactory().setValue(s.waves.delayBetweenGroups);
        spinnerDelayBetweenEnemies.getValueFactory().setValue(s.waves.delayBetweenEnemies);

        spinnerGoblinHP.getValueFactory().setValue(s.enemy.goblinHP);
        spinnerKnightHP.getValueFactory().setValue(s.enemy.knightHP);
        spinnerGoblinGold.getValueFactory().setValue(s.enemy.goblinGold);
        spinnerKnightGold.getValueFactory().setValue(s.enemy.knightGold);
        spinnerGoblinSpeed.getValueFactory().setValue(s.enemy.goblinSpeed);
        spinnerKnightSpeed.getValueFactory().setValue(s.enemy.knightSpeed);

        spinnerArcherCost.getValueFactory().setValue(s.tower.archerCost);
        spinnerArtilleryCost.getValueFactory().setValue(s.tower.artilleryCost);
        spinnerMageCost.getValueFactory().setValue(s.tower.mageCost);
        spinnerArcherRange.getValueFactory().setValue(s.tower.archerRange);
        spinnerArtilleryRange.getValueFactory().setValue(s.tower.artilleryRange);
        spinnerMageRange.getValueFactory().setValue(s.tower.mageRange);
        spinnerAoeRadius.getValueFactory().setValue(s.tower.aoeRadius);
        spinnerArrowDamage.getValueFactory().setValue(s.tower.arrowDamage);
        spinnerArtilleryDamage.getValueFactory().setValue(s.tower.artilleryDamage);
        spinnerMagicDamage.getValueFactory().setValue(s.tower.magicDamage);
        spinnerArcherFireRate.getValueFactory().setValue(s.tower.archerFireRate);
        spinnerArtilleryFireRate.getValueFactory().setValue(s.tower.artilleryFireRate);
        spinnerMageFireRate.getValueFactory().setValue(s.tower.mageFireRate);

        spinnerPlayerHP.getValueFactory().setValue(s.player.startingHP);
        spinnerPlayerGold.getValueFactory().setValue(s.player.startingGold);
        isMusicOn = s.player.musicOn;
        isSfxOn = s.player.sfxOn;
        musicToggleButton.setText(isMusicOn ? "Music: ON" : "Music: OFF");
        soundEffectsToggleButton.setText(isSfxOn ? "SFX: ON" : "SFX: OFF");
    }

    @FXML
    private void handleRestoreDefaults(ActionEvent event) {
        System.out.println("Restoring default settings...");
        initializeDefaults();

        isMusicOn = true;
        isSfxOn = true;
        musicToggleButton.setText("Music: ON");
        soundEffectsToggleButton.setText("SFX: ON");

        // 🔐 Save the restored state
        GameSettings defaults = gatherSettingsFromUI();
        defaults.player.musicOn = isMusicOn;
        defaults.player.sfxOn = isSfxOn;
        saveSettingsToDisk(defaults);
        System.out.println("Defaults saved.");
    }

    @FXML
    private void handleSaveButton(ActionEvent event) {
        GameSettings settings = gatherSettingsFromUI();
        saveSettingsToDisk(settings);
        System.out.println("Settings saved.");
    }


    private GameSettings loadSettingsFromDisk() {
        File file = new File("src/data/OptionsSettings.dat");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            System.out.println("✅ Loaded settings from: " + file.getAbsolutePath());
            return (GameSettings) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Could not load settings: " + e.getMessage());
            return null;
        }
    }

    private void saveSettingsToDisk(GameSettings settings) {
        File file = new File("src/data/OptionsSettings.dat");
        try {
            file.getParentFile().mkdirs(); // Ensure directory exists
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(settings);
                System.out.println("✅ Saved settings to: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("❌ Could not save settings: " + e.getMessage());
        }
    }


    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/StartScene.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMusicToggle(ActionEvent event) {
        isMusicOn = !isMusicOn;
        musicToggleButton.setText(isMusicOn ? "Music: ON" : "Music: OFF");
    }

    @FXML
    private void handleSfxToggle(ActionEvent event) {
        isSfxOn = !isSfxOn;
        soundEffectsToggleButton.setText(isSfxOn ? "SFX: ON" : "SFX: OFF");
    }
}
