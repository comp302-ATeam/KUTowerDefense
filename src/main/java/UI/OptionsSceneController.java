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
import java.io.IOException;

public class OptionsSceneController {

    @FXML
    private Button backButton;
    @FXML
    private Button musicToggleButton;
    @FXML
    private Button soundEffectsToggleButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button restoreDefaultsButton;

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
        initializeDefaults();
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
        initIntegerSpinner(spinnerPlayerHP, 1, 500, 20);
        initIntegerSpinner(spinnerPlayerGold, 0, 1000, 100);
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/StartScene.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMusicToggle(ActionEvent event) {
        isMusicOn = !isMusicOn;
        if (isMusicOn) {
            System.out.println("Music turned ON");
            musicToggleButton.setText("Music: ON");
        } else {
            System.out.println("Music turned OFF");
            musicToggleButton.setText("Music: OFF");
        }
    }

    @FXML
    private void handleSfxToggle(ActionEvent event) {
        isSfxOn = !isSfxOn;
        if (isSfxOn) {
            System.out.println("Sound Effects turned ON");
            soundEffectsToggleButton.setText("SFX: ON");
        } else {
            System.out.println("Sound Effects turned OFF");
            soundEffectsToggleButton.setText("SFX: OFF");
        }
    }

    @FXML
    private void handleRestoreDefaults(ActionEvent event) {
        System.out.println("Restoring default settings...");
        initializeDefaults();
    }

    @FXML
    private void handleSaveButton(ActionEvent event) {
        System.out.println("Settings would be saved! (not implemented yet)");
        // Future work: Save to file
    }
}
