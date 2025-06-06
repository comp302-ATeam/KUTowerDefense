package Domain.GameObjects;

import UI.TowerMenu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.List;

public abstract class Tower extends GameObject {

    private static final String PATH = "images/tower.png";
    protected int attackRange;      // Attack range, distance
    protected int cost;             // cost of the tower
    protected int currentLevel;     // we can upgrade the tower so we should keep the current level of it.
    protected int damage;           // it is how much we damage the enemy
    protected double fireRate;      // Attacks per second
    protected double sellRatio;     // Ratio of cost returned when selling
    protected long lastAttackTime;  // Time of last attack in milliseconds
    public ImageView towerImage;

    public Pane mapPane;

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
        this.fireRate = fireRate;
        this.cost = cost;

        // Initialize common values that are same for all towers
        this.currentLevel = 1;        // All towers start at level 1
        this.sellRatio = 0.4;       // All towers return 40% of cost when sold
        this.lastAttackTime = 0;    // All towers start with no attack cooldown

        this.mapPane = mapPane;

        //renderTower(PATH);

        //TowerMenu towerMenu = new TowerMenu(this);


    }


    // Abstract method for attacking enemies
    public abstract void attack(Enemy target);

    // Render Method
    protected void renderTower(String path){
        System.out.println(path);
        Image image = new Image(getClass().getResource(path).toExternalForm());
        towerImage = new ImageView(image);

        System.out.println("x=" + x + ", y=" + y);

        towerImage.setFitWidth(96);
        towerImage.setFitHeight(96);
        towerImage.setPreserveRatio(true);

        towerImage.setLayoutX(x);
        towerImage.setLayoutY(y);

        mapPane.getChildren().add(towerImage);
    }

    // Method to upgrade the tower
    public void upgrade() {
        currentLevel++;
        damage = (int)(damage * 1.2);  // 20% damage increase
        attackRange = (int)(attackRange * 1.2);    // 20% range increase
        fireRate *= 1.2;               // 20% fire rate increase
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

    // Method to check if tower can attack based on fire rate
    public boolean canAttack() {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastAttack = currentTime - lastAttackTime;
        return timeSinceLastAttack >= (1000 / fireRate);  // Convert fire rate to milliseconds
    }

    // Method to update last attack time
    protected void updateLastAttackTime() {
        lastAttackTime = System.currentTimeMillis();
    }
}