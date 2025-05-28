package Domain.GameObjects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class Goblin extends Enemy {
    private static final int   FRAMES   = 6;
    private static final int   COLS     = 6;
    private static final double FPS      = 8.0;

    // goblin class is faster than knight class so it should have a greater speed multiplier.
    double speedMultiplier = 1.25;
    private static final String IMAGE_PATH = "Assets/enemies/Goblin_Red.png";




    public Goblin(int xPos, int yPos, String enemyType, int healthPoints, int speed, ImageView goblinImg) {
        super(xPos, yPos, goblinImg, enemyType,healthPoints,speed,FRAMES,COLS,FPS);
        this.speed *= speedMultiplier;
        if (goblinImg != null) {
            setImageView(goblinImg);
        }

    }

    private void setImageView(ImageView view) {
        this.imageObject = view;
    }


    // this method is used for calculating the damage the goblin class takes based on the projectile
    // and returns the amount of damage taken.


    @Override
    protected int CalcDamage(Projectile projectile) {
        switch (projectile.type) {
            case "Arrow":
                return (int)(projectile.damage*1.5);
            case "Magic":
                return (int)(projectile.damage * 0.5);
            case "Artillery":
                return (projectile.damage);
            default:
                return (projectile.damage);
        }
    }


}
