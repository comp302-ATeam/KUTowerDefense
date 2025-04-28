package Domain.GameObjects;

public class Enemy extends GameObject {

    String enemyType;
    int healthPoints;
    double speed;

    public Enemy(int xPos, int yPos, String enemyType, int healthPoints, double speed) {
        super(xPos, yPos);
        this.enemyType = enemyType;
        this.healthPoints = healthPoints;
        this.speed = speed;
    }

    @Override
    public void update(double deltaTime) {

    }
}
