package Domain.GameObjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ArcherTower extends Tower {


    private static final String PATH = "/Assets/Towers/Tower_archer128.png";
    private static final String UPGRADEPATH = "/Assets/phase2/archer up.png";

    public ArcherTower(int x, int y, Pane mapPane) {
        // Archer tower has high fire rate but moderate damage
        super(x, y, 150, 10, 1.0, 100,mapPane);  // range=150, damage=10, fireRate=1.0, cost=100
        renderTower(PATH);
    }

    @Override
    public void upgrade() {
        setImage(UPGRADEPATH);
        attackRange *= 1.5;
        fireRate *= 2;
    }

    @Override
    public Projectile createProjectile(Enemy enemy){
        return new Arrow((int) x + 48, (int) y + 48, damage,enemy);
    }

    @Override
    public void update(double deltaTime) {
        attack();
    }
} 