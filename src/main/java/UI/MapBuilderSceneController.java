package UI;

import Domain.GameFlow.TileSetLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.scene.image.Image;


import java.io.IOException;

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
    public void onReturn(ActionEvent event)  {
        try {
            Parent startRoot = FXMLLoader.load(
                    getClass().getResource("/StartScene.fxml")
            );
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(startRoot));
            stage.centerOnScreen();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
