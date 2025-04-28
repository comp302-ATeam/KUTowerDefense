package Domain.GameObjects;

public class MagicSpell extends Projectile {
    public MagicSpell(int xPos, int yPos, int damage, Enemy target) {
        super(xPos,yPos,damage,"MagicSpell",target);

    }
}
