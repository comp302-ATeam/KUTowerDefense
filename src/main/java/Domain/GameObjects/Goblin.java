package Domain.GameObjects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class Goblin extends Enemy {
    private static final int FRAME_COLUMNS = 6;
    private static final int FRAME_COUNT   = 6;
    private static final double FPS        = 8.0;
    private static final double SPEED_MULTIPLIER = 1.25;

    private final ImageView view;
    private final int frameWidth;
    private final int frameHeight;
    private int currentFrame = 0;
    private final Timeline animation;

    
    // goblin class is faster than knight class so it should have a greater speed multiplier.
    double speedMultiplier = 1.25;
    public Goblin(int xPos, int yPos, String enemyType, int healthPoints, int speed, ImageView imageObject) {
        super(xPos, yPos, imageObject, enemyType,healthPoints,speed);
        this.speed *= speedMultiplier;

        Image spriteSheet = imageObject.getImage();

        // 2) Compute frame size (now that image is loaded)
        this.frameWidth  = (int)(spriteSheet.getWidth()  / FRAME_COLUMNS);
        this.frameHeight = (int)(spriteSheet.getHeight());  // single-row

        // 3) Create the ImageView and set its initial viewport
        this.view = imageObject;

        view.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));
        animation = new Timeline(
                new KeyFrame(Duration.seconds(1.0 / FPS), e -> advanceFrame())
        );
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

    }
    private void advanceFrame() {
        currentFrame = (currentFrame + 1) % FRAME_COUNT;
        int xOffset = currentFrame * frameWidth;
        view.setViewport(new Rectangle2D(xOffset, 0, frameWidth, frameHeight));
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
