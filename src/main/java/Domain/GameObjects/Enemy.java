package Domain.GameObjects;

import Domain.GameFlow.Tile;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class Enemy extends GameObject {
    /* enemy has 4 attributes, it has its own enemy type (goblin or knight)
    *  its health points and its speed and also image of that enemy.
    * */
    String enemyType;
    int healthPoints;
    double speed;
    private final ImageView view;

    //region Animation Attributes
    private static final int FRAME_COLUMNS = 6;
    private static final int FRAME_COUNT   = 6;
    private static final double FPS        = 8.0;
    private final int frameWidth;
    private final int frameHeight;
    private int currentFrame = 0;
    private final Timeline animation;
    private final int frameCount;
    private final int frameColumns;
    //endregion



    // constructor for the enemy superclass
    public Enemy(int xPos, int yPos, ImageView imageObject, String enemyType, int healthPoints, double speed, int frameCount, int frameColumns, double fps) {
        super(xPos, yPos,imageObject);
        this.enemyType = enemyType;
        this.healthPoints = healthPoints;
        this.speed = speed;

        //region Animation Setup
        Image spriteSheet = imageObject.getImage();

        this.frameCount   = frameCount;
        this.frameColumns = frameColumns;
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
        //endregion
    }
    //region Animation AdvanceFrame Method
    private void advanceFrame() {
        currentFrame = (currentFrame + 1) % FRAME_COUNT;
        int xOffset = currentFrame * frameWidth;
        view.setViewport(new Rectangle2D(xOffset, 0, frameWidth, frameHeight));
    }
    //endregion


    @Override
    public void update(double deltaTime) {

    }
    // calculate damage method is an abstract method which will be used accordingly for each class goblin or knight
    // this abstract method is used for calculating the damage took by
    // enemy based on its type (goblins take less magic damage)
    protected abstract int CalcDamage(Projectile projectile);

    // this method takes the calculated damage based on the subclass (goblin or knight) and deducts that
    // amount of damage from the health point of that enemy object
    // if the damage is lethal, then the enemy object calls the method Die()
    public void takeDamage(Projectile projectile) {
        int damageTaken = CalcDamage(projectile);
        healthPoints -= damageTaken;

        if( healthPoints <= 0 ) {
            Die();
        }
    }
    // to be implemented, if we are going to recycle the object created rather than destroy and create
    // this method should make this object to go back to its starting state.
    public void Die(){}

    public double getPathProgress()
    {
        // TO BE IMPLEMENTED
        return 0;
    }
}
