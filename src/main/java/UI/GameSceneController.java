package UI;

import Domain.GameFlow.GameActionController;
import Domain.GameFlow.MapLoader;
import Domain.GameFlow.Vector2;
import Domain.GameObjects.Goblin;
import Domain.GameObjects.Knight;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.List;


public class GameSceneController {
    private GameActionController gameActionController = new GameActionController();

    @FXML
    private Button pauseButton;
    @FXML
    private Button resumeGame;
    @FXML
    private Button speedUp;
    @FXML
    private Pane gamePane;

    @FXML
    GridPane gameGrid;

    MapLoader mapLoader;

    @FXML private Label labelGold;
    @FXML private Label labelLives;
    @FXML private Label labelWave;

    public void updateGold(int gold) {
        labelGold.setText(String.valueOf(gold));
    }

    public void updateLives(int lives) {
        labelLives.setText(String.valueOf(lives));
    }

    public void updateWave(int wave) {
        labelWave.setText(String.valueOf(wave));
    }



    @FXML
    private void handlePauseButton(ActionEvent event) {
        gameActionController.pauseGame();
        System.out.println("Game Paused");
    }
    @FXML
    private void handleResumeGame(ActionEvent event) {
        gameActionController.resumeGame();
        System.out.println("Resumed Game");
    }
    @FXML
    private void handleSpeedUp(ActionEvent event) {
        gameActionController.speedUpGame();
        System.out.println("Speed Up Game");
    }


    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            mapLoader = new MapLoader(gameGrid);
            Vector2<Double>[] mainPath = mapLoader.getPath();
            int startingX = mainPath[0].x.intValue();
            int startingY = mainPath[0].y.intValue();
            Image goblinImg = new Image("Assets/enemies/Goblin_Red.png");
            ImageView goblinView = new ImageView(goblinImg);
            Goblin goblin = new Goblin(startingX,startingY,"Goblin",100,100,goblinView);
            Image knightImg = new Image("Assets/enemies/Warrior_Blue.png");
            ImageView knightView = new ImageView(knightImg);
            Knight knight = new Knight(startingX,startingY,"Knight",100,100,knightView);

            Node goblinHealth   = goblin.getHealthBar();
            goblinHealth.setVisible(false);
            goblinView.setPickOnBounds(true);
            goblinView.setOnMouseEntered(e -> goblinHealth.setVisible(true));
            goblinView.setOnMouseExited(e -> goblinHealth.setVisible(false));

            Node knightHealth = knight.getHealthBar();
            knightHealth.setVisible(false);
            knightView.setPickOnBounds(true);
            knightView.setOnMouseEntered(e -> knightHealth.setVisible(true));
            knightView.setOnMouseExited(e -> knightHealth.setVisible(false));



            // Add enemies and their health bars to the gamePane
            gamePane.getChildren().addAll(
                    goblin.getView(), goblin.getHealthBar(),
                    knight.getView(), knight.getHealthBar()
            );

            // Sync their initial transforms
            goblin.updateViewTransform();
            knight.updateViewTransform();


            goblin.moveAlong(mainPath);
            knight.moveAlong(mainPath);

            // AnimationTimer to update enemies and health bars
            new AnimationTimer() {
                private long lastTime = 0;

                @Override
                public void handle(long now) {
                    if (lastTime > 0) {
                        double deltaSec = (now - lastTime) / 1_000_000_000.0;
                        goblin.update(deltaSec);
                        knight.update(deltaSec);
                    }
                    lastTime = now;
                }
            }.start();
        });
    }
}
