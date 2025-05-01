package Domain.GameObjects;

public class Projectile extends GameObject{
    int damage;
    String type;
    Enemy target;
    public Projectile(int xPos, int yPos, int damage, String type, Enemy target) {
        super(xPos, yPos);
        this.damage = damage;
        this.type = type;
        this.target = target;
    }


    @Override
    public void update(double deltaTime) {

    }
}
