package UI;

import Domain.GameFlow.TileSetLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapSelectionSceneController {
    @FXML
    private ListView<String> mapList;
    @FXML
    private Button selectButton;
    @FXML
    private Button backButton;
    @FXML
    private VBox root;

    private String selectedMap = null;
    private static final String SAVES_DIRECTORY = "saves";

    @FXML
    public void initialize() {
        loadMaps();
        
        mapList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedMap = newValue;
            selectButton.setDisable(newValue == null);
        });
    }

    private void loadMaps() {
        List<String> maps = new ArrayList<>();
        File directory = new File(SAVES_DIRECTORY);
        
        // Create saves directory if it doesn't exist
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // Load all .ser files from the saves directory
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".ser"));
        if (files != null) {
            for (File file : files) {
                maps.add(file.getName().replace(".ser", ""));
            }
        }
        
        mapList.getItems().clear();
        mapList.getItems().addAll(maps);
    }

    @FXML
    private void onSelectMap() {
        if (selectedMap != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameSceneController.fxml"));
                Parent root = loader.load();
                GameSceneController gameController = loader.getController();
                gameController.setMapName(selectedMap);
                
                Scene scene = new Scene(root);
                Stage stage = (Stage) mapList.getScene().getWindow();
                stage.setScene(scene);
                stage.setFullScreen(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/MainMenu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.root.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 