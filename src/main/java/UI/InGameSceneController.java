package UI;
import Domain.GameObjects.Goblin;
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
        Goblin goblin = new Goblin(100,100,"Goblin",100,1,goblinView);
        gamePane.getChildren().add(goblin.getView());
    }

}
