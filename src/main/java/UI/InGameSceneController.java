package UI;
import Domain.GameObjects.Goblin;
import Domain.GameObjects.Knight;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.List;

public class InGameSceneController {

    @FXML
    private Pane gamePane;

    @FXML
    public void initialize() {
        Image goblinImg = new Image("Assets/enemies/Goblin_Red.png");
        ImageView goblinView = new ImageView(goblinImg);
        Goblin goblin = new Goblin(-100,0,"Goblin",100,100,goblinView);
        Image knightImg = new Image("Assets/enemies/Warrior_Blue.png");
        ImageView knightView = new ImageView(knightImg);
        Knight knight = new Knight(100,0,"Knight",100,100,knightView);

        gamePane.getChildren().addAll(
                goblin.getView(),
                knight.getView()
        );

        // 4) Sync their initial transforms
        goblin.updateViewTransform();
        knight.updateViewTransform();

        //Example list for goblin to move on.

        List<Point2D> route = List.of(
                new Point2D(100, 0),
                new Point2D(100, -50),
                new Point2D(250,-100 )
        );
        List<Point2D> route2 = List.of(
                new Point2D(150, 0),
                new Point2D(150, 50),
                new Point2D(200,100 )
        );

        goblin.moveAlong(route);
        knight.moveAlong(route2);
// 4) In your AnimationTimer:
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
    }

}
