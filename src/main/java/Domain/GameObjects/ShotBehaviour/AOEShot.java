package Domain.GameObjects.ShotBehaviour;

import Domain.GameFlow.Vector2;
import Domain.GameFlow.AnimationPlayer;
import Domain.GameObjects.ArtilleryShell;
import Domain.GameObjects.Enemy;
import Domain.GameObjects.Projectile;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class AOEShot extends BaseBehaviour {
    private static final int EXPLOSION_FRAMES = 9;
    private static final int EXPLOSION_COLS = 9;
    private static final double EXPLOSION_FPS = 12.0;

    public AOEShot(ArtilleryShell projectile) {
        super(projectile);
    }

    public List<Enemy> getEnemyInArea(Enemy target){
        List<Enemy> enemyList= new ArrayList<>() ;
        double shortestDistance = Double.MAX_VALUE;

        for (Enemy enemy : Enemy.activeEnemies) {
            if (!enemy.isAlive() || enemy.hasReachedEnd()) continue;

            double distance = target.distanceTo(enemy, new Vector2<Double>((double) enemy.frameWidth / 2,(double) enemy.frameHeight / 2));
            if (distance <= ((ArtilleryShell)projectile).splashRadius) {
                shortestDistance = distance;
                enemyList.add(enemy);
            }
        }

        return enemyList;
    }

    @Override
    public boolean Shoot(Enemy target) {
        List<Enemy> enemyList = getEnemyInArea(target);

        // Play explosion animation at the target location
        if (projectile.getParent() != null) {
            Pane gamePane = (Pane) projectile.getParent();
            Image explosionSheet = new Image(getClass().getResourceAsStream("/Assets/Effects/Explosions.png"));
            AnimationPlayer explosionAnim = new AnimationPlayer(explosionSheet, EXPLOSION_FRAMES, EXPLOSION_COLS, EXPLOSION_FPS);
            
            // Position the explosion at the target's center using actual position
            double explosionX = target.getX() + target.frameWidth / 2;
            double explosionY = target.getY() + target.frameHeight / 2;
            
            // Set animation to play once and remove itself when done
            explosionAnim.setLooping(false);
            explosionAnim.setOnComplete(() -> {
                if (explosionAnim.isPlaying()) {
                    explosionAnim.stop();
                }
            });
            
            // Play the explosion animation
            explosionAnim.playAt(explosionX, explosionY, gamePane);
        }

        // Apply damage to all enemies in the area
        for(Enemy enemy : enemyList){
            enemy.takeDamage(projectile);
        }

        return false;
    }
}
