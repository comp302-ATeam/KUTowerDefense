package Domain.GameObjects;

public class Arrow extends Projectile {
    public Arrow(int xPos, int yPos, int damage, Enemy target) {
        super(xPos, yPos, damage, "Arrow", target);
    }

}
