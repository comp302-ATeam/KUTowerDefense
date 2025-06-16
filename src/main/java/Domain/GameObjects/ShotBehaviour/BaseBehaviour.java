package Domain.GameObjects.ShotBehaviour;

import Domain.GameObjects.Enemy;
import Domain.GameObjects.Projectile;

public abstract class BaseBehaviour {


    protected Projectile projectile;

    BaseBehaviour(Projectile projectile) {
        this.projectile = projectile;
    }

    public abstract boolean Shoot(Enemy target);

}
