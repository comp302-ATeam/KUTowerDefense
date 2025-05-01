package Domain.GameObjects;

public class Arrow extends Projectile {
    public Arrow(int xPos, int yPos, int damage, String type, Enemy target) {
        super(xPos, yPos, damage, type, target);
    }
}
