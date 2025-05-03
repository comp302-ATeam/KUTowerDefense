package Domain.GameObjects;

import java.util.List;

public class ArtilleryTower extends Tower {
    private int splashRadius;  // Radius of AOE damage
    private List<Enemy> allEnemies;  // Reference to all enemies for AOE damage

    public ArtilleryTower(int x, int y, List<Enemy> allEnemies) {
        // Artillery tower has low fire rate but high damage and AOE
        super(x, y, 250, 40, 0.5, 200);  // range=250, damage=40, fireRate=0.5, cost=200
        this.splashRadius = 100;  // AOE radius
        this.allEnemies = allEnemies;
    }

    @Override
    public void attack(Enemy target) {
        if (target != null && canAttack()) {
            // Create a new artillery shell that will manage itself
            //new ArtilleryShell(this.x, y, target, this, splashRadius, allEnemies);
            updateLastAttackTime();
        }
    }

    // Method to get splash radius
    public int getSplashRadius() {
        return splashRadius;
    }

    // Override upgrade to also increase splash radius
    @Override
    public void upgrade() {
        super.upgrade();
        splashRadius = (int)(splashRadius * 1.2);  // 20% increase in splash radius
    }

    @Override
    public void update(double deltaTime) {

    }
}