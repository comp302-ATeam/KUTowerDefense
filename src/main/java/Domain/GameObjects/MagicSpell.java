package Domain.GameObjects;

import javafx.scene.image.ImageView;

public class MagicSpell extends Projectile {
    public MagicSpell(int xPos, int yPos, int damage, Enemy target, ImageView projectileImage) {
        super(xPos,yPos,damage,"MagicSpell",target,projectileImage);

    }
}
