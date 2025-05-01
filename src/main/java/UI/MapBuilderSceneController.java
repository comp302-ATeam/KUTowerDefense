package UI;

import Domain.GameFlow.TileSetLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

import java.awt.event.ActionEvent;

public class MapBuilderSceneController {


    @FXML private GridPane mainGrid;
    @FXML private GridPane selectGrid;


    @FXML private Button returnButton;
    @FXML private Button saveButton;

    @FXML
    public void initialize() {
        TileSetLoader loader = new TileSetLoader(mainGrid,15,9);
        //loader.addToGrid(5, 0, 0); // test with index 5

        TileSetLoader loader2 = new TileSetLoader(selectGrid,4,10,true);

    }

    @FXML
    public void onSave() {

    }

    @FXML
    public void onReturn() {

    }

}
