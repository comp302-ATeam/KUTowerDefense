package Domain.GameObjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ArcherTower extends Tower {


    private static final String PATH = "/Assets/Towers/Tower_archer128.png";

    public ArcherTower(int x, int y, Pane mapPane) {
        // Archer tower has high fire rate but moderate damage
        super(x, y, 150, 10, 1.0, 100,mapPane);  // range=150, damage=10, fireRate=1.0, cost=100
        renderTower(PATH);
    }

    @Override
    public void attack(Enemy target) {
        if (target != null && canAttack()) {
            // Create a new arrow
            //Arrow arrow = new Arrow(this.getX(), this.getY(), 10, target);
            // here we should add arrow to the game
            // update time
            updateLastAttackTime();
        }
    }

    @Override
    public void update(double deltaTime) {

    }
} 