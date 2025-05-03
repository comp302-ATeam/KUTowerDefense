package UI;
import Domain.GameObjects.Goblin;
import Domain.GameObjects.Knight;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class InGameSceneController {

    @FXML
    private Pane gamePane;

    @FXML
    public void initialize() {
        Image goblinImg = new Image("Assets/enemies/Goblin_Red.png");
        ImageView goblinView = new ImageView(goblinImg);
        Goblin goblin = new Goblin(-100,0,"Goblin",100,1,goblinView);
        Image knightImg = new Image("Assets/enemies/Warrior_Blue.png");
        ImageView knightView = new ImageView(knightImg);
        Knight knight = new Knight(100,0,"Knight",100,1,knightView);

        gamePane.getChildren().add(goblin.getView());
        goblin.getView().setLayoutX(goblin.getX());
        goblin.getView().setLayoutY(goblin.getY());

        gamePane.getChildren().add(knight.getView());
        knight.getView().setLayoutX(knight.getX());
        knight.getView().setLayoutY(knight.getY());
    }

}
