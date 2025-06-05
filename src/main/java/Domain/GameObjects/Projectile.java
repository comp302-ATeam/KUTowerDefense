package Domain.GameObjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Projectile extends GameObject{
    int damage;
    String type;
    Enemy target;
    public Projectile(int xPos, int yPos, int damage, String type, Enemy target, ImageView image) {
        super(xPos, yPos,image);
        this.damage = damage;
        this.type = type;
        this.target = target;
    }

    public int getDamage() {
        return damage;
    }


    @Override
    public void update(double deltaTime) {

    }
}
