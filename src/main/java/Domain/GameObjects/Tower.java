package Domain.GameObjects;

import Domain.GameFlow.GameActionController;

import Domain.GameFlow.Vector2;
import Domain.GameFlow.WaveManager;
import Domain.GameObjects.GameObject;
import UI.TowerMenu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;

public abstract class Tower extends GameObject {

    private static final String PATH = "images/tower.png";
    protected int attackRange;      // Attack range, distance
    protected int cost;             // cost of the tower
    protected int currentLevel;     // we can upgrade the tower so we should keep the current level of it.
    protected int damage;           // it is how much we damage the enemy
    public double baseFireRate;
    public double fireRate;      // Attacks per second
    protected double sellRatio;     // Ratio of cost returned when selling
    protected long lastAttackTime;  // Time of last attack in milliseconds
    protected int level = 1;

    public ImageView towerImage;
    public Pane mapPane;
    private Circle rangeCircle;

    /**
     * Constructor for Tower class.
     * Parameters are only the values that can be different for each tower type.
     * Other values that are same for all towers are initialized inside the constructor.
     *
     * @param x X coordinate of the tower
     * @param y Y coordinate of the tower
     * @param attackRange Attack range of the tower
     * @param damage Base damage of the tower
     * @param fireRate Attacks per second
     * @param cost Construction cost
     */
    public Tower(int x, int y, int attackRange, int damage, double fireRate, int cost , Pane mapPane) {
        super(x, y, new ImageView());
        this.attackRange = attackRange;
        this.damage = damage;
        this.baseFireRate = fireRate;
        this.fireRate = baseFireRate;
        this.cost = cost;

        // Initialize common values that are same for all towers
        this.currentLevel = 1;        // All towers start at level 1
        this.sellRatio = 0.4;       // All towers return 40% of cost when sold
        this.lastAttackTime = 0;    // All towers start with no attack cooldown

        this.mapPane = mapPane;

        GameActionController.towerList.add(this);

    }

    // Abstract method for attacking enemies
    public void attack(){
        if (!canAttack()){
            return;
        }

        Enemy target = getClosestEnemy();

//        Enemy target = findBestTarget(GameActionController.getInstance().enemyList); // assuming you add this list
        if (target != null) {

            Projectile p = createProjectile(target);// Adjust center offset
            mapPane.getChildren().add(p);
            GameActionController.projectileList.add(p);
            updateLastAttackTime();
        }



    }

    public void drawDebugRange() {
        if (rangeCircle == null) {
            rangeCircle = new Circle(attackRange);
            rangeCircle.setStroke(Color.RED);        // Outline color
            rangeCircle.setFill(Color.TRANSPARENT);  // No fill
            rangeCircle.setStrokeWidth(2);
            rangeCircle.setMouseTransparent(true);   // Ignore clicks

            // Center the circle around the tower's center using the tower's actual position
            double towerCenterX = towerImage.getLayoutX() + towerImage.getFitWidth() / 2 -2;
            double towerCenterY = towerImage.getLayoutY() + towerImage.getFitHeight() / 2;
            rangeCircle.setCenterX(towerCenterX);
            rangeCircle.setCenterY(towerCenterY);
        }
    }

    public void showRange() {
        if (rangeCircle != null && !mapPane.getChildren().contains(rangeCircle)) {
            mapPane.getChildren().add(rangeCircle);
        }
    }

    public void hideRange() {
        if (rangeCircle != null && mapPane.getChildren().contains(rangeCircle)) {
            mapPane.getChildren().remove(rangeCircle);
        }
    }

    // Render Method
    public void renderTower(String path){
        System.out.println(path);
        Image image = new Image(getClass().getResource(path).toExternalForm());
        towerImage = new ImageView(image);

        System.out.println("x=" + x + ", y=" + y);

        towerImage.setFitWidth(96);
        towerImage.setFitHeight(96);
        towerImage.setPreserveRatio(true);

        towerImage.setLayoutX(x);
        towerImage.setLayoutY(y);

//        // Add hover effects for range display
//        towerImage.setOnMouseEntered(e -> {
//            drawDebugRange();
//            showRange();
//        });
//        towerImage.setOnMouseExited(e -> hideRange());
//
//        // Ensure the tower image is on top of other elements
//        towerImage.setMouseTransparent(false);
//        towerImage.toFront();
//
        mapPane.getChildren().add(towerImage);
        TowerMenu menu = new TowerMenu(this);
        menu.toFront();
        drawDebugRange();
        hideRange();
    }

    // Method to upgrade the tower
    public abstract void upgrade();

    protected void setImage(String path) {
        towerImage.setImage(new Image(getClass().getResource(path).toExternalForm()));
    }

    // Method to calculate refund amount when selling the tower
    /**
     * Calculates how much gold the player will receive when selling this tower.
     * The refund amount is a percentage (sellRatio) of the tower's original cost.
     * For example, if a tower costs 100 gold and sellRatio is 0.7,
     * the player will receive 70 gold when selling the tower.
     *
     * @return The amount of gold the player will receive when selling this tower
     */
    public int calculateRefundAmount() {
        return (int)(cost * sellRatio);
    }

    // Getters
    public int getAttackRange() { return attackRange; }
    public int getDamage() { return damage; }
    public double getFireRate() { return fireRate; }
    public int getCurrentLevel() { return currentLevel; }
    public int getCost() { return cost; }

    public void Destroy(){
        GameActionController.towerList.remove(this);
        mapPane.getChildren().remove(towerImage);
    }

    // Method to check if an enemy is in range
    // we already defined distanceTo method in GameObject class.
    public boolean isInRange(Enemy enemy) {
        return distanceTo(enemy) <= attackRange;
    }

    // Method to find the best target among available enemies
    /**
     * Finds the enemy that is closest to the exit among those in range.
     * The pathProgress value represents how far the enemy has progressed along the path:
     * - 0.0 means the enemy is at the start
     * - 1.0 means the enemy has reached the exit
     *
     * @param enemies List of active enemies on the map
     * @return The enemy closest to the exit that is within range, or null if no enemies in range
     */
    public Enemy findBestTarget(List<Enemy> enemies) {
        Enemy bestTarget = null;
        double maxProgress = -1;

        for (Enemy enemy : enemies) {
            if (isInRange(enemy)) {  // Only check if enemy is in range
                double progress = enemy.getPathProgress();
                if (progress > maxProgress) {
                    maxProgress = progress;
                    bestTarget = enemy;
                }
            }
        }

        return bestTarget;
    }



    protected Enemy getClosestEnemy(){
        Enemy closest = null;
        double shortestDistance = Double.MAX_VALUE;

        for (Enemy enemy : Enemy.activeEnemies) {
            if (!enemy.isAlive() || enemy.hasReachedEnd()) continue;

            double distance = distanceTo(enemy, new Vector2<Double>((double) enemy.frameWidth / 2,(double) enemy.frameHeight / 2));
            if (distance <= attackRange && distance < shortestDistance) {
                shortestDistance = distance;
                closest = enemy;
            }
        }

        return closest;
    }

    // Method to check if tower can attack based on fire rate
    public boolean canAttack() {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastAttack = currentTime - lastAttackTime;
        return timeSinceLastAttack >= (1000 / fireRate);  // Convert fire rate to milliseconds
    }

    // Method to update last attack time
    public void updateLastAttackTime() {
        lastAttackTime = System.currentTimeMillis();
    }

    public abstract Projectile createProjectile(Enemy enemy);

    public abstract void update(double deltaTime);
}