package UI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Parent;
import Domain.GameFlow.GameActionController;

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
    private Button buttonOptions;

    @FXML
    private void onActionStartNew(ActionEvent event) throws Exception {
        System.out.println("StartNew");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        // Reset game state
        GameActionController.getInstance().resetGame();
        
        // Load new game scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameSceneController.fxml"));
        Parent root = loader.load();
        
        // Get the controller and ensure it's initialized
        GameSceneController controller = loader.getController();
        if (controller != null) {
            System.out.println("Game scene controller loaded successfully");
        } else {
            System.out.println("Warning: Game scene controller is null");
        }
        
        // Set up the new scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    @FXML
    private void onActionLoadGame(ActionEvent event) throws Exception {
        System.out.println("LoadGame");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/LoadGameScreen.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

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
        System.exit(0);
    }
}
