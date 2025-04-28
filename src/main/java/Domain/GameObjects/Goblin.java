package Domain.GameObjects;

public class Goblin extends Enemy {
    // goblin class is faster than knight class so it should have a greater speed multiplier.
    double speedMultiplier = 1.25;
    public Goblin(int xPos, int yPos, String enemyType, int healthPoints, int speed) {
        super(xPos, yPos, enemyType, healthPoints, speed);
        this.speed *= speedMultiplier;
    }
    // this method is used for calculating the damage the goblin class takes based on the projectile
    // and returns the amount of damage taken.
    @Override
    protected int CalcDamage(Projectile projectile) {
        switch (projectile.type) {
            case "Arrow":
                return (int)(projectile.damage*1.25);
            case "Magic":
                return (int)(projectile.damage * 0.75);
            case "Artillery":
                return (projectile.damage);
            default:
                return (projectile.damage);
        }
    }
}
