package UI;

import Domain.GameFlow.MapLoader;
import Domain.GameFlow.TileSetLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class MapBuilderSceneController {


    @FXML private GridPane mainGrid;
    @FXML private GridPane selectGrid;


    @FXML private Button returnButton;
    @FXML private Button saveButton;

    private TileSetLoader mainTileSetLoader;

    @FXML
    public void initialize() {
        mainTileSetLoader = new TileSetLoader(mainGrid,15,9);
        //loader.addToGrid(5, 0, 0); // test with index 5

        TileSetLoader loader2 = new TileSetLoader(selectGrid,4,10,true);

    }

    @FXML
    public void onSave() {
        mainTileSetLoader.saveGrid();
    }

    @FXML
    public void onReturn() {
        showTemporaryWindow();
    }


    public void showTemporaryWindow() {
        // Create a new Stage (window)
        Stage tempStage = new Stage();
        tempStage.setTitle("Temporary Test Window");

        // Content for the window
        Label label = new Label("This is a test!");
        StackPane root = new StackPane(label);

        GridPane gridPane = new GridPane();


        root.getChildren().add(gridPane);

        MapLoader mapLoader = new MapLoader(gridPane);

        // Set scene
        Scene scene = new Scene(root, 300, 200);
        tempStage.setScene(scene);

        // Show the new window
        tempStage.show();




    }

}
