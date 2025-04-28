package Domain.GameObjects;

public abstract class Enemy extends GameObject {

    String enemyType;
    int healthPoints;
    double speed;

    public Enemy(int xPos, int yPos, String enemyType, int healthPoints, double speed) {
        super(xPos, yPos);
        this.enemyType = enemyType;
        this.healthPoints = healthPoints;
        this.speed = speed;
    }

    @Override
    public void update(double deltaTime) {

    }
    protected abstract int CalcDamage(Projectile projectile);


    public void takeDamage(Projectile projectile) {
        int damageTaken = CalcDamage(projectile);
        healthPoints -= damageTaken;

        if( healthPoints <= 0 ) {
            Die();
        }
    }
    public void Die(){};
}
