package Domain.GameObjects;

import javafx.scene.image.ImageView;

public class Knight extends Enemy{
    // knight class is slower than goblin class so it should have a smaller speed multiplier.
    double speedMultiplier = 0.75;
    private static final int   FRAMES   = 6;
    private static final int   COLS     = 6;
    private static final double FPS      = 8.0;
    public Knight(int xPos, int yPos, String enemyType, int healthPoints, int speed, ImageView knightImage) {
        super(xPos, yPos, knightImage,enemyType, healthPoints, speed,FRAMES,COLS,FPS);
        this.speed *= speedMultiplier;
    }
    // this method is used for calculating the damage the knight class takes based on the projectile
    // and returns the amount of damage taken.
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
