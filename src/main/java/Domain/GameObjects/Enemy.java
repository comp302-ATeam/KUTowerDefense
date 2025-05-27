package Domain.GameObjects;

import Domain.GameFlow.GameActionController;
import Domain.GameFlow.Tile;
import Domain.GameFlow.Vector2;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.*;

public abstract class Enemy extends GameObject {
    /* enemy has 4 attributes, it has its own enemy type (goblin or knight)
    *  its health points and its speed and also image of that enemy.
    * */
    String enemyType;
    int healthPoints;
    double speed;
    private final ImageView view;
    protected double targetX, targetY;
    protected boolean movingToTarget = false;
    private final Queue<Vector2<Double>> waypoints = new LinkedList<>();
    private final Rectangle healthBar;
    private final double maxHealthPoints;

    //region Animation Attributes
    private static final int FRAME_COLUMNS = 6;
    private static final int FRAME_COUNT   = 6;
    private static double FPS        = 8.0;
    private final int frameWidth;
    private final int frameHeight;
    private int currentFrame = 0;
    private final Timeline animation;
    private final int frameCount;
    private final int frameColumns;
    //endregion



    // constructor for the enemy superclass
    public Enemy(double xPos, double yPos, ImageView imageObject, String enemyType, int healthPoints, double speed, int frameCount, int frameColumns, double fps) {
        super(xPos, yPos,imageObject);
        this.enemyType = enemyType;
        this.healthPoints = healthPoints;
        this.maxHealthPoints = healthPoints;
        this.speed = speed;

        // Initialize health bar
        this.healthBar = new Rectangle(50, 5); // Width: 50, Height: 5
        this.healthBar.setFill(Color.RED);
        this.healthBar.setTranslateX(xPos);
        this.healthBar.setTranslateY(yPos - 10); // Position above the enemy

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
    public void setFPS(double FPS){this.FPS =FPS;}
    public double getSpeed() {return speed;}
    public void setSpeed(double speed) {this.speed = speed;}
    //region Animation AdvanceFrame Method
    private void advanceFrame() {
        currentFrame = (currentFrame + 1) % FRAME_COUNT;
        int xOffset = currentFrame * frameWidth;
        view.setViewport(new Rectangle2D(xOffset, 0, frameWidth, frameHeight));
    }
    //endregion


    public void moveTo(double x, double y) {
        this.targetX = x;
        this.targetY = y;
        this.movingToTarget = true;
    }
    public void moveAlong(Vector2<Double>[] points) {
        waypoints.clear();
        // Arrays.asList wraps the array into a List for easy bulk-add
        waypoints.addAll(Arrays.asList(points));
        movingToTarget = true;
        advanceWaypoint();
    }
    private void advanceWaypoint() {
        Vector2<Double> next = waypoints.poll();
        if (next != null) {
            // Double auto-unboxes to double
            moveTo(next.x, next.y);
        } else {
            movingToTarget = false;  // no more waypoints
        }
    }
    protected void onArrive() {
        advanceWaypoint();
    }

    @Override
    public void update(double deltaTime) {
        FPS = GameActionController.getFPS();
        if (GameActionController.isPaused()) {
            if (animation.getStatus() == Animation.Status.RUNNING) {
                animation.pause();
            }
        } else {
            if (animation.getStatus() != Animation.Status.RUNNING) {
                animation.play();
            }
        }
        if (movingToTarget && !GameActionController.isPaused()) {
            double dx = targetX - x, dy = targetY - y;
            double dist = Math.hypot(dx, dy), step = speed * deltaTime * GameActionController.getGameSpeed();

            if (dist <= step) {
                x = targetX;
                y = targetY;
                onArrive();   // automatically advances to the next waypoint
            } else {
                x += (dx / dist) * step;
                y += (dy / dist) * step;
            }
            updateViewTransform();
        }
        if (healthPoints <= 0 && isAlive()) {
            Die();
        }
        // 1) Update the health bar position
        healthBar.setTranslateX(x);
        healthBar.setTranslateY(y - 10); // Position above the enemy
        healthBar.setFill(Color.GREEN);
        healthBar.toFront();

        // 2) Update the health bar width based on the current health points
        double healthBarWidth = (healthPoints / maxHealthPoints) * 50; // Scale to the width of the health bar
        healthBar.setWidth(healthBarWidth);
        // 3) Push transforms to the view
        updateViewTransform();

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
        // Update the health bar width based on the current health points
        double healthBarWidth = (healthPoints / maxHealthPoints) * 50; // Scale to the width of the health bar
        healthBar.setWidth(healthBarWidth);


    }

    public Rectangle getHealthBar () {
        return healthBar;
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
