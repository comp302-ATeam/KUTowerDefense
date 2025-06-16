package Domain.GameObjects;

import Domain.GameObjects.ShotBehaviour.AOEShot;
import javafx.scene.image.ImageView;

import java.util.List;

public class ArtilleryShell extends Projectile {

    public int splashRadius;


    public ArtilleryShell(int xPos, int yPos, int damage, Enemy target, int splashRadius) {
        super(xPos, yPos, damage, "ArtilleryShell", target,"/Assets/Projectilşes/bomb.png");
        this.splashRadius = splashRadius;
        shotBehaviour = new AOEShot(this);

    }


}
