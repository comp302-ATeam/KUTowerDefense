package Domain.GameObjects;

import Domain.GameFlow.GameActionController;
import Domain.GameFlow.Tile;
import Domain.GameFlow.Vector2;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.Random;

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
    private boolean hasReachedEnd = false;  // Track if enemy has reached the end
    private boolean isAlive = true;
    private static final Random random = new Random();
    protected static java.util.List<Enemy> activeEnemies = new java.util.ArrayList<>();
    protected double baseSpeed;
    protected double slowEndTime = 0; // Time when slow effect ends
    protected ImageView slowIcon; // Snowflake icon for slow effect
    protected boolean isSlowing = false;

    //region Animation Attributes
    private static final int FRAME_COLUMNS = 6;
    private static final int FRAME_COUNT   = 6;
    private static double FPS        = 8.0;
    public final int frameWidth;
    public final int frameHeight;
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
        activeEnemies.add(this);

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

        // Initialize slow icon with transparent background
        Image originalImage = new Image(getClass().getResourceAsStream("/assets/phase2/snow flake icon.png"));
        Image transparentImage = makeWhiteTransparent(originalImage);
        this.slowIcon = new ImageView(transparentImage);
        this.slowIcon.setFitWidth(20);
        this.slowIcon.setFitHeight(20);
        this.slowIcon.setVisible(false);
        GameActionController.getInstance().getGamePane().getChildren().add(slowIcon);
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
        if (waypoints.isEmpty()) {
            // Enemy has reached the last waypoint
            if (!hasReachedEnd) {
                hasReachedEnd = true;
                // Notify game controller to deduct player health
                Domain.GameFlow.WaveManager.getInstance().enemyReachedEnd();
                // Stop the enemy's movement
                movingToTarget = false;
                // Remove the enemy from the game
                Die();
            }
        } else {
        advanceWaypoint();
        }
    }

    @Override
    public void update(double deltaTime) {
        if (!isAlive || hasReachedEnd) return;

        // Check if slow effect has ended
        if (slowEndTime > 0 && System.currentTimeMillis() > slowEndTime) {
            slowEndTime = 0;
            slowIcon.setVisible(false);
            setSpeed(baseSpeed);
            // Reset speed based on whether we're near goblins
        }

        GameActionController gameActionController = GameActionController.getInstance();

        // Handle animation based on pause state
        if (gameActionController.isPaused()) {
            if (animation != null) {
                animation.pause();
            }
            return;  // Skip all other updates when paused
        } else {
            if (animation != null && animation.getStatus() == javafx.animation.Animation.Status.PAUSED) {
                animation.play();
            }
        }

        // Update slow icon position
        slowIcon.setTranslateX(x + 20); // Position to the right
        slowIcon.setTranslateY(y + 20); // Position below

        if (movingToTarget) {
            double dx = targetX - x, dy = targetY - y;
            double dist = Math.hypot(dx, dy), step = speed * deltaTime * gameActionController.getGameSpeed();

            if (dist <= step) {
                x = targetX;
                y = targetY;
                onArrive();   // automatically advances to the next waypoint
            } else {
                x += (dx / dist) * step;
                y += (dy / dist) * step;
            }
            super.x = x;
            super.y = y;
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
        if (!isAlive) return;
        int damageTaken = CalcDamage(projectile);
        healthPoints -= damageTaken;
        if( healthPoints <= 0 ) {
            Die();
        }
        if (projectile.type.equals("MagicSpell") && !isSlowing) {
            //slowEndTime = System.currentTimeMillis() + 2000; // Set slow effect to end in 2 seconds
            slowIcon.setVisible(true);
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
    public void Die() {
        if (isAlive) {
            isAlive = false;
            // Stop the animation
            if (animation != null) {
                animation.stop();
            }

            // Remove from active enemies list
            if (activeEnemies != null) {
                activeEnemies.remove(this);
            }

            // Store parent reference before removing anything
            javafx.scene.Parent parent = view != null ? view.getParent() : null;

            // Remove the enemy and its UI elements from the game pane
            if (view != null && parent != null) {
                if (parent instanceof javafx.scene.layout.Pane) {
                    javafx.scene.layout.Pane pane = (javafx.scene.layout.Pane) parent;
                    // Remove all UI elements
                    pane.getChildren().remove(view);
                    
                    // Remove health bar
                    if (healthBar != null) {
                        healthBar.setDisable(true);
                        healthBar.setMouseTransparent(true);
                        healthBar.setVisible(false);
                        healthBar.setOpacity(0);
                        pane.getChildren().remove(healthBar);
                    }

                    // Remove speed up icon if it exists - using a larger tolerance
                    for (javafx.scene.Node node : new ArrayList<>(pane.getChildren())) {
                        if (node instanceof javafx.scene.image.ImageView &&
                            node != view &&
                            Math.abs(node.getTranslateX() - x) < 30 && // Increased tolerance
                            Math.abs(node.getTranslateY() - y) < 30) { // Increased tolerance
                            node.setDisable(true);
                            node.setMouseTransparent(true);
                            node.setVisible(false);
                            node.setOpacity(0);
                            pane.getChildren().remove(node);
                        }
                    }
                    pane.requestLayout();
                } else if (parent instanceof javafx.scene.Group) {
                    javafx.scene.Group group = (javafx.scene.Group) parent;
                    // Remove all UI elements
                    group.getChildren().remove(view);
                    
                    // Remove health bar
                    if (healthBar != null) {
                        healthBar.setDisable(true);
                        healthBar.setMouseTransparent(true);
                        healthBar.setVisible(false);
                        healthBar.setOpacity(0);
                        group.getChildren().remove(healthBar);
                    }

                    // Remove speed up icon if it exists - using a larger tolerance
                    for (javafx.scene.Node node : new ArrayList<>(group.getChildren())) {
                        if (node instanceof javafx.scene.image.ImageView &&
                            node != view &&
                            Math.abs(node.getTranslateX() - x) < 30 && // Increased tolerance
                            Math.abs(node.getTranslateY() - y) < 30) { // Increased tolerance
                            node.setDisable(true);
                            node.setMouseTransparent(true);
                            node.setVisible(false);
                            node.setOpacity(0);
                            group.getChildren().remove(node);
                        }
                    }
                }
            }

            // Clear any remaining waypoints
            waypoints.clear();
            movingToTarget = false;

            // Only spawn gold pouch if enemy died on the path (not at the end) and with 50% chance
            if (!hasReachedEnd && parent != null && random.nextDouble() < 0.5) {
                // Spawn gold pouch at the enemy's death position with a small random offset
                double offsetX = (random.nextDouble() - 0.5) * 20; // ±10 pixels
                double offsetY = (random.nextDouble() - 0.5) * 20; // ±10 pixels
                
                double pouchX = x + offsetX;
                double pouchY = y + offsetY;

                GoldPouch goldPouch = GoldPouch.spawnAt(pouchX, pouchY);

                if (parent instanceof javafx.scene.layout.Pane) {
                    ((javafx.scene.layout.Pane) parent).getChildren().add(goldPouch.getView());
                } else if (parent instanceof javafx.scene.Group) {
                    ((javafx.scene.Group) parent).getChildren().add(goldPouch.getView());
                }
            }

            // Remove slow icon
            GameActionController.getInstance().getGamePane().getChildren().remove(slowIcon);
        }
    }

    public double getPathProgress()
    {
        // TO BE IMPLEMENTED
        return 0;
    }

    public boolean hasReachedEnd() {
        return hasReachedEnd;
    }
    public Image makeWhiteTransparent(Image inputImage) {
        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();

        WritableImage outputImage = new WritableImage(width, height);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);

                // Make nearly-white pixels fully transparent
                if (color.getRed() > 0.95 && color.getGreen() > 0.95 && color.getBlue() > 0.95) {
                    writer.setColor(x, y, new Color(1, 1, 1, 0)); // Transparent
                } else {
                    writer.setColor(x, y, color);
                }
            }
        }

        return outputImage;
    }

}
