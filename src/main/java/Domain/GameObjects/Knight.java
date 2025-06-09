package Domain.GameObjects;

import javafx.scene.image.*;
import javafx.scene.Node;
import javafx.scene.Group;
import Domain.GameFlow.WaveManager;
import javafx.scene.paint.Color;

import java.util.List;

public class Knight extends Enemy{
    // knight class is slower than goblin class so it should have a smaller speed multiplier.
    double speedMultiplier = 0.90;
    private static final int   FRAMES   = 6;
    private static final int   COLS     = 6;
    private static final double FPS      = 8.0;
    private static final double NEARBY_DISTANCE = 100.0; // Distance threshold for speed buff
    private static final double SPEED_BUFF_MULTIPLIER = 1.25; // Speed increase when near goblins
    private double baseSpeed; // Store the original speed
    private ImageView speedBuffIcon; // Icon to show when speed boosted

    public Knight(int xPos, int yPos, String enemyType, int healthPoints, int speed, ImageView knightImage) {
        super(xPos, yPos, knightImage,enemyType, healthPoints, speed,FRAMES,COLS,FPS);
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

    }

    private boolean isNearGoblin() {
        WaveManager waveManager = WaveManager.getInstance();
        if (waveManager == null) return false;

        List<Enemy> activeEnemies = waveManager.getActiveEnemies();
        for (Enemy enemy : activeEnemies) {
            if (enemy instanceof Goblin && enemy.isAlive()) {
                double distance = this.distanceTo(enemy);
                if (distance <= NEARBY_DISTANCE) {
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
        if (isBoosted) {
            this.speed = baseSpeed * SPEED_BUFF_MULTIPLIER;
            this.speedBuffIcon.setVisible(true);
        } else {
            this.speed = baseSpeed;
            this.speedBuffIcon.setVisible(false);
        }
        
        // Update speed buff icon position
        this.speedBuffIcon.setTranslateX(x); // Center horizontally
        this.speedBuffIcon.setTranslateY(y + 40); // Position below the knight
        
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

}
