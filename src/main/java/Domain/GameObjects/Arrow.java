package Domain.GameObjects;

import Domain.GameObjects.ShotBehaviour.AOEShot;
import Domain.GameObjects.ShotBehaviour.BasicShot;
import javafx.scene.image.ImageView;

public class Arrow extends Projectile {
    public Arrow(int xPos, int yPos, int damage, Enemy target) {
        super(xPos, yPos, damage,"arrow", target,"/Assets/Projectilşes/arrow.png");
        rotateOffset = -45.0;
        shotBehaviour = new BasicShot(this);
    }

}
