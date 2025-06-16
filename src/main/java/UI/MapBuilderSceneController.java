package UI;

import Domain.GameFlow.MapLoader;
import Domain.GameFlow.TileSetLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;

public class MapBuilderSceneController {


    @FXML private GridPane mainGrid;
    @FXML private GridPane selectGrid;


    @FXML private Button returnButton;
    @FXML private Button saveButton;
    @FXML private TextField mapNameField;

    public Scene currentScene;

    private TileSetLoader mainTileSetLoader;

    @FXML
    public void initialize() {
        mainTileSetLoader = new TileSetLoader(mainGrid,15,9);
        //loader.addToGrid(5, 0, 0); // test with index 5

        TileSetLoader loader2 = new TileSetLoader(selectGrid,4,10,true);

        mainGrid.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.T) {
                        mainTileSetLoader.changePathMode();
                    }
                });
            }
        });



//        currentScene.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.W) {
//                System.out.println("W key pressed");
//            }
//        });
    }

    @FXML
    public void onSave() {
        String mapName = mapNameField.getText().trim();
        if (mapName.isEmpty()) {
            mapName = "mapSave";  // Default name if none provided
        }
        mainTileSetLoader.saveGrid(mapName);
    }

    @FXML
    public void onReturn(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/StartScene.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        //stage.setMaximized(true);

    }

//
//    public void showTemporaryWindow() {
//        // Create a new Stage (window)
//        Stage tempStage = new Stage();
//        tempStage.setTitle("Temporary Test Window");
//
//        // Content for the window
//        Label label = new Label("This is a test!");
//        StackPane root = new StackPane(label);
//
//        GridPane gridPane = new GridPane();
//
//
//        root.getChildren().add(gridPane);
//
//        MapLoader mapLoader = new MapLoader(gridPane);
//
//        // Set scene
//        Scene scene = new Scene(root, 300, 200);
//        tempStage.setScene(scene);
//
//        // Show the new window
//        tempStage.show();
//
//
//
//
//    }

}
