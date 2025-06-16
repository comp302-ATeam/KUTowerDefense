package Domain.GameObjects;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.List;

public class ArtilleryTower extends Tower {

    private static final String PATH = "/Assets/Towers/Tower_bomb128.png";
    private static final String UPGRADEPATH = "/Assets/phase2/artillery up.png";


    private int splashRadius;  // Radius of AOE damage
    private List<Enemy> allEnemies;  // Reference to all enemies for AOE damage


    public ArtilleryTower(int x, int y, Pane mapPane) {
        // Artillery tower has low fire rate but high damage and AOE
        super(x, y, 250, 40, 0.3, 200,mapPane);  // range=250, damage=40, fireRate=0.5, cost=200
        this.splashRadius = 100;  // AOE radius
        //this.allEnemies = allEnemies;
        renderTower(PATH);
    }

    @Override
    public Projectile createProjectile(Enemy enemy){
        return new ArtilleryShell((int) x + 48, (int) y + 48, damage,enemy , 250);
    }

    // Method to get splash radius
    public int getSplashRadius() {
        return splashRadius;
    }

    // Override upgrade to also increase splash radius
    @Override
    public void upgrade() {
        setImage(UPGRADEPATH);
        splashRadius = (int)(splashRadius * 1.2);  // 20% increase in splash radius
        damage = (int)(damage * 1.2);
    }

    @Override
    public void update(double deltaTime) {
        attack();
    }
}
