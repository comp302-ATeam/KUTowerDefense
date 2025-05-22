package Domain.GameObjects;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MageTower extends Tower {

    private static final String PATH = "/Assets/Towers/Castle128.png";

    public MageTower(int x, int y, Pane mapPane) {
        // Mage tower has medium fire rate but high damage
        super(x, y, 180, 25, 1.0, 150,mapPane);  // range=180, damage=25, fireRate=1.0, cost=150
        renderTower(PATH);
    }

    @Override
    public void attack(Enemy target) {
        if (target != null && canAttack()) {
            // Create a new magic spell that will manage itself
            // spell is created by tower and it goes from tower to enemy, so we should say this.getX() etc. (tower's position)
            //MagicSpell spell = new MagicSpell(this.getX(), this.getY(),20,target);
            // here we should add spell to the game via a method..
            // then update time
            updateLastAttackTime();
        }
    }

    @Override
    public void update(double deltaTime) {

    }

} 