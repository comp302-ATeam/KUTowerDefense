package Domain.GameObjects;

public class Goblin extends Enemy {
    double speedMultiplier = 1.25;
    public Goblin(int xPos, int yPos, String enemyType, int healthPoints, int speed) {
        super(xPos, yPos, enemyType, healthPoints, speed);
        this.speed *= speedMultiplier;
    }

    @Override
    protected int CalcDamage(Projectile projectile) {
        switch (projectile.type) {
            case "Arrow":
                return (int)(projectile.damage*1.25);
            case "Magic":
                return (int)(projectile.damage * 0.75);
            case "Artillery":
                return (int)(projectile.damage);
            default:
                return (int)(projectile.damage);
        }
    }
}
