package UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;


public class StartSceneController {

//    @FXML
//    private Button buttonStartNew;
//    @FXML
//    private Button buttonLoadGame;
//    @FXML
@FXML
private Button buttonOptions;
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/OptionsScene.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) buttonOptions).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onActionQuit() {
        System.out.println("Quit");

    }
}
