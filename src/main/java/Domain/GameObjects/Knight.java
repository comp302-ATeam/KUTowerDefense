package Domain.GameObjects;

public class Knight extends Enemy{
    double speedMultiplier = 0.75;
    public Knight(int xPos, int yPos, String enemyType, int healthPoints, int speed) {
        super(xPos, yPos, enemyType, healthPoints, speed);
        this.speed *= speedMultiplier;
    }

    @Override
    protected int CalcDamage(Projectile projectile) {
        switch (projectile.type) {
            case "Arrow":
                return (int) (projectile.damage*0.75);
            case "Magic":
                return (int) (projectile.damage * 1.25);
            case "Artillery":
                return (int) (projectile.damage);
            default:
                return (int) (projectile.damage);
        }
    }
}
