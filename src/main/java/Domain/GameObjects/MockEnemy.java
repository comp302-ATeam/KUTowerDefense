package Domain.GameObjects;

import javafx.scene.image.ImageView;

public abstract class MockEnemy extends GameObject {
    String enemyType;
    int healthPoints;
    double speed;
    public MockEnemy(int x, int y, String type, int healthPoints, double speed) {
        super(x, y, null); // pass dummy values
        this.healthPoints = healthPoints;
        this.speed = speed;
        this.enemyType = type;
    }
    public void takeDamage(Projectile projectile) {
        if (this.isAlive) {
            int damageTaken = CalcDamage(projectile);
            healthPoints -= damageTaken;

            if (healthPoints <= 0) {
                Die();
            }
        }
    }

    public String getEnemyType() {
        return enemyType;
    }
    public int getHealthPoints() {
        return healthPoints;
    }

    public double getSpeed() {
        return speed;
    }

    // Override update to skip animation logic
    @Override
    public void update(double deltaTime) {
        // no-op for tests
    }
    protected abstract int CalcDamage(Projectile projectile);

    // Override Die to avoid accessing any JavaFX properties
    public void Die() {
        this.isAlive = false;
        this.healthPoints = 0;
    }

    public int getHealth() {
        return this.healthPoints;
    }
}