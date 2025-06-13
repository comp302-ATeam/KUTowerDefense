package Domain.GameObjects;

import javafx.scene.image.ImageView;

import java.util.List;

public class ArtilleryShell extends Projectile {


    public ArtilleryShell(int xPos, int yPos, int damage, Enemy target, int splashRadius, List<Enemy> allEnemies) {
        super(xPos, yPos, damage, "ArtilleryShell", target,"Assets/phase2/snow flake icon.png");

    }


}
