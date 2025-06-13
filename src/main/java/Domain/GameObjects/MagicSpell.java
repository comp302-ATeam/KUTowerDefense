package Domain.GameObjects;

import javafx.scene.image.ImageView;

public class MagicSpell extends Projectile {


    public MagicSpell(int xPos, int yPos, int damage, Enemy target) {
        super(xPos,yPos,damage,"MagicSpell",target, "/Assets/phase2/snow flake icon.png");

    }
}
