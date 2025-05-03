package Domain.GameObjects;

import javafx.scene.image.ImageView;

public class Arrow extends Projectile {
    public Arrow(int xPos, int yPos, int damage, Enemy target, ImageView arrowImage) {
        super(xPos, yPos, damage,"arrow", target, arrowImage );
    }

}
