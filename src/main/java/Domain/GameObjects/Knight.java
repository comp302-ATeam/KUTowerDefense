package Domain.GameObjects;

import javafx.scene.image.*;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import Domain.GameFlow.GameActionController;
import Domain.GameFlow.WaveManager;
import javafx.scene.paint.Color;
import Domain.GameFlow.Tile;

import java.util.List;
import java.util.ArrayList;

public class Knight extends Enemy {
    // knight class is slower than goblin class so it should have a smaller speed multiplier.
    double speedMultiplier = 0.90;
    private static final int FRAMES = 4;
    private static final int COLS = 4;
    private static final int FPS = 8;
    private static final double NEARBY_DISTANCE = 100.0; // Distance threshold for speed buff
    private static final double SPEED_BUFF_MULTIPLIER = 1.25; // Speed increase when near goblins
    private double baseSpeed; // Store the original speed
    private double speedBoostEndTime = 0;
    private double slowEndTime = 0;
    private ImageView speedBuffIcon; // Icon to show when speed boosted
    private ImageView slowIcon; // Icon to show when slowed

    public Knight(int xPos, int yPos, String enemyType, int healthPoints, double speed, ImageView knightImage, int goldReward) {
        super(xPos, yPos, knightImage, enemyType, healthPoints, speed, FRAMES, COLS, FPS, goldReward);
        this.baseSpeed = speed * speedMultiplier;
        this.speed = baseSpeed;
        
        // Initialize speed buff icon
        Image thunderImage = new Image("Assets/phase2/thunder icon.png");
        Image thunderphoto = makeWhiteTransparent(thunderImage);
        this.speedBuffIcon = new ImageView(thunderphoto);
        this.speedBuffIcon.setFitWidth(20); // Adjust size as needed
        this.speedBuffIcon.setFitHeight(20);
        this.speedBuffIcon.setVisible(false);
        this.speedBuffIcon.setTranslateX(xPos + 40); // Center horizontally
        this.speedBuffIcon.setTranslateY(yPos + 40); // Position below the knight
        this.speedBuffIcon.setPreserveRatio(true);
        this.speedBuffIcon.setSmooth(true);

        // Initialize slow icon
        Image originalImage = new Image(getClass().getResourceAsStream("/assets/phase2/snow flake icon.png"));
        Image transparentImage = makeWhiteTransparent(originalImage);
        this.slowIcon = new ImageView(transparentImage);
        this.slowIcon.setFitWidth(20);
        this.slowIcon.setFitHeight(20);
        this.slowIcon.setVisible(false);
        GameActionController.getInstance().getGamePane().getChildren().add(slowIcon);
    }
    private boolean isNearGoblin() {
        for (Enemy enemy : activeEnemies) {
            if (enemy.enemyType.equals("Goblin") && enemy.isAlive()) {
                double dx = enemy.x - x;
                double dy = enemy.y - y;
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance <= 100) { // 100 pixels radius
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void update(double deltaTime) {

        // Check if near goblins and adjust speed accordingly
        boolean isBoosted = isNearGoblin();

        // Check if slow effect has ended
        if (slowEndTime > 0 && System.currentTimeMillis() > slowEndTime) {
            slowEndTime = 0;
            slowIcon.setVisible(false);
            // Reset speed based on whether we're near goblins
            if (isBoosted) {
                setSpeed(baseSpeed * SPEED_BUFF_MULTIPLIER);
            } else {
                setSpeed(baseSpeed);
            }
        }

        // Apply current speed based on effects
        if (slowEndTime > 0) {
            // If slowed, apply slow effect
        if (isBoosted) {
                setSpeed(baseSpeed * SPEED_BUFF_MULTIPLIER * 0.8);
            } else {
                setSpeed(baseSpeed * 0.8);
            }
        } else {
            // If not slowed, apply normal or boosted speed
            if (isBoosted) {
                setSpeed(baseSpeed * SPEED_BUFF_MULTIPLIER);
            } else {
                setSpeed(baseSpeed);
            }
        }

        // Update icon visibility and positions
        speedBuffIcon.setVisible(isBoosted);
        slowIcon.setVisible(slowEndTime > 0);
        speedBuffIcon.setTranslateX(x - 20);
        speedBuffIcon.setTranslateY(y - 20);
        slowIcon.setTranslateX(x + 20);
        slowIcon.setTranslateY(y + 20);

        super.update(deltaTime);
    }

    @Override
    public Node getView() {
        Group group = new Group();
        group.getChildren().addAll(super.getView(), speedBuffIcon);
        return group;
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

    @Override
    public void Die() {
        if (isAlive()) {
            activeEnemies.remove(this);
            GameActionController.getInstance().getGamePane().getChildren().remove(speedBuffIcon);
            GameActionController.getInstance().getGamePane().getChildren().remove(slowIcon);
            GameActionController.getInstance().getGamePane().getChildren().remove(this);
            super.Die();
        }
    }

    @Override
    public void takeDamage(Projectile projectile) {
        if (!isAlive()) return;
        
        // Calculate and apply damage
        int damageTaken = CalcDamage(projectile);
        healthPoints -= damageTaken;

        // Handle slow effect from magic damage
        if (projectile.type.equals("MagicSpell")) {
            slowEndTime = System.currentTimeMillis() + 2000; // Set slow effect to end in 2 seconds
            slowIcon.setVisible(true);
            
            // Apply slow effect immediately
            boolean isBoosted = isNearGoblin();
            if (isBoosted) {
                setSpeed(baseSpeed * SPEED_BUFF_MULTIPLIER * 0.8);
            } else {
                setSpeed(baseSpeed * 0.8);
            }
        }

        if (healthPoints <= 0) {
            Die();
        }
    }

}
