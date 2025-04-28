package Domain.GameObjects;

public class Enemy extends GameObject {
    String enemyType;
    int healthPoints;
    


    public Enemy(int xPos, int yPos, String enemyType, int healthPoints) {
        super(xPos, yPos);
        this.enemyType = enemyType;
        this.healthPoints = healthPoints;
    }

    @Override
    public void update(double deltaTime) {

    }
}
