package UI;
import Domain.GameFlow.MapLoader;
import Domain.GameObjects.Goblin;
import Domain.GameObjects.GoldDrop;
import Domain.GameObjects.Knight;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.List;

public class InGameSceneController {

    @FXML
    private Pane gamePane;

    @FXML
    GridPane gameGrid;

    MapLoader mapLoader;



    @FXML
    public void initialize() {


        Platform.runLater(() -> {
            mapLoader = new MapLoader(gameGrid,gamePane);

        });




        Image goblinImg = new Image("Assets/enemies/Goblin_Red.png");
        ImageView goblinView = new ImageView(goblinImg);
        //Goblin goblin = new Goblin(-100,0,"Goblin",100,100,goblinView);

        Image knightImg = new Image("Assets/enemies/Warrior_Blue.png");
        ImageView knightView = new ImageView(knightImg);
        //Knight knight = new Knight(100,0,"Knight",100,100,knightView);

        Image goldImg = new Image("Assets/enemies/G_Spawn.png");
        ImageView goldView = new ImageView(goldImg);

        gamePane.getChildren().addAll(
                
        );

        // 4) Sync their initial transforms
        

        //Example list for goblin to move on.


// 4) In your AnimationTimer:
        new AnimationTimer() {
            private long lastTime = 0;
            @Override
            public void handle(long now) {
                if (lastTime > 0) {
                    double deltaSec = (now - lastTime) / 1_000_000_000.0;
                    
                }
                lastTime = now;
            }
        }.start();
    }

}
