package Domain.GameObjects;

public class Enemy extends GameObject {

    public Enemy(int xPos, int yPos, EnemyType enemyType) {
        super(xPos, yPos);
        this.enemyType = enemyType;
    }

    @Override
    public void update(double deltaTime) {

    }
}
