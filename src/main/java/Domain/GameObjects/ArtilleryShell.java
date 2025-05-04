package Domain.GameObjects;

import javafx.scene.image.ImageView;

import java.util.List;

public class ArtilleryShell extends Projectile {
    private int splashRadius;  // Radius of splash damage
    private List<Enemy> allEnemies;  // List of all enemies for AOE damage

    public ArtilleryShell(int xPos, int yPos, int damage, Enemy target, int splashRadius, List<Enemy> allEnemies, ImageView projectileImage) {
        super(xPos, yPos, damage, "ArtilleryShell", target,projectileImage);
        this.splashRadius = splashRadius;
        this.allEnemies = allEnemies;
    }

    @Override
    public void update(double deltaTime) {
        // Once it reaches its destination or after a certain time, apply splash damage
        // let's assume the shell explodes once it reaches the target

        // Check for enemies within splash radius and apply damage
        for (Enemy enemy : allEnemies) {
            if (this.distanceTo(enemy) <= splashRadius) {
                enemy.takeDamage(this);  // Apply splash damage to enemies within radius,
                                                  // takeDamage takes projectile as an input
            }
        }

        // The shell "explodes" after damage is applied
        this.setAlive(false);  // Set shell to not alive once it explodes
    }
}
