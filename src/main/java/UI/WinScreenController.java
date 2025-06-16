package UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WinScreenController {
    @FXML
    private AnchorPane gamePane;

    @FXML
    public void initialize() {
        System.out.println("WinScreenController initialized");
        System.out.println("gamePane is null: " + (gamePane == null));
    }

    @FXML
    private void handleOKButton(ActionEvent event) {
        System.out.println("OK button clicked"); // Debug log
        try {
            // Get the current stage from the button's scene
            javafx.scene.control.Button button = (javafx.scene.control.Button) event.getSource();
            Scene currentScene = button.getScene();
            System.out.println("Current scene found: " + (currentScene != null));
            
            if (currentScene == null) {
                System.out.println("Error: Could not get scene from button");
                return;
            }

            Stage stage = (Stage) currentScene.getWindow();
            System.out.println("Stage found: " + (stage != null));
            
            if (stage == null) {
                System.out.println("Error: Could not get window from scene");
                return;
            }

            // Load the home page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartScene.fxml"));
            Parent root = loader.load();
            System.out.println("Home page loaded"); // Debug log
            
            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            System.out.println("Home page shown"); // Debug log
        } catch (IOException e) {
            System.err.println("Error in handleOKButton: " + e.getMessage()); // Debug log
            e.printStackTrace();
        }
    }
} 