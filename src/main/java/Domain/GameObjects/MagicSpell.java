package Domain.GameObjects;

import Domain.GameObjects.ShotBehaviour.BasicShot;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;

public class MagicSpell extends Projectile {

    public MagicSpell(int xPos, int yPos, int damage, Enemy target) {
        super(xPos,yPos,damage,"MagicSpell",target, "/Assets/Projectilşes/fireball.png");
        rotateOffset = 180;
        shotBehaviour = new BasicShot(this);
        
        // Add blue color effect to the fireball
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(0.7); // Blue hue
        colorAdjust.setSaturation(0.8); // Increase saturation
        colorAdjust.setBrightness(0.2); // Slightly increase brightness
        this.setEffect(colorAdjust);
    }
}
