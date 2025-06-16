package Domain.GameObjects.ShotBehaviour;

import Domain.GameFlow.Vector2;
import Domain.GameObjects.ArtilleryShell;
import Domain.GameObjects.Enemy;
import Domain.GameObjects.Projectile;

import java.util.ArrayList;
import java.util.List;

public class AOEShot extends BaseBehaviour {

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

        for(Enemy enemy : enemyList){
            enemy.takeDamage(projectile);
        }


        return false;
    }
}
