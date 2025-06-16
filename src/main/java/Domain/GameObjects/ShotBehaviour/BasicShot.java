package Domain.GameObjects.ShotBehaviour;

import Domain.GameObjects.Enemy;
import Domain.GameObjects.Projectile;

public class BasicShot extends BaseBehaviour {


    public BasicShot(Projectile projectile) {
        super(projectile);
    }



    @Override
    public boolean Shoot(Enemy target) {
        target.takeDamage(projectile);
        return false;
    }
}
