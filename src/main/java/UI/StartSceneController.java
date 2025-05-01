package UI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Parent;

public class StartSceneController {

//    @FXML
//    private Button buttonStartNew;
//    @FXML
//    private Button buttonLoadGame;
//    @FXML
//    private Button buttonOptions;
//    @FXML
//    private Button buttonQuit;



    @FXML
    private void onActionStartNew() {
        System.out.println("StartNew");

    }

    @FXML
    private void onActionLoadGame() {
        System.out.println("LoadGame");

    }

    @FXML
    private void onActionOptions() {
        System.out.println("Options");

    }

    @FXML
    private void onActionOpenMapEditor(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/MapBuilderScene.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
        stage.setMaximized(true);


    }


    @FXML
    private void onActionQuit() {
        System.out.println("Quit");

    }
}
