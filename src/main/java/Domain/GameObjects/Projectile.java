package Domain.GameObjects;

import Domain.GameFlow.GameActionController;
import Domain.GameFlow.Vector2;
import Domain.GameObjects.ShotBehaviour.BaseBehaviour;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class  Projectile extends ImageView{
    int damage;
    String type;
    Enemy target;
    public double basespeed = 600;
    public double speed;
    public boolean isActive = true;
    Vector2<Double> offset;

    protected BaseBehaviour shotBehaviour;


    protected double rotateOffset = 90.0;

    public Projectile(int xPos, int yPos, int damage, String type, Enemy target,String imagePath) {

        super(new Image(Projectile.class.getResource(imagePath).toExternalForm()));

        this.target = target;
        this.damage = damage;
        this.type = type;


        if (!type.equals("MagicSpell")){
            setFitWidth(32);
            setFitHeight(32);
        }
        else {
            setFitWidth(96);
            setFitHeight(96);
        }

        // Size the image
        //setFitWidth(16);
        //setFitHeight(16);

        // Position it initially
        setLayoutX(xPos - getFitWidth() / 2);
        setLayoutY(yPos - getFitHeight() / 2);

        offset = new Vector2<Double>(0.0,0.0);
        offset.x = (double) target.frameWidth / 2;
        offset.y = (double) target.frameHeight / 2;

        speed = basespeed;
    }

    public int getDamage() {
        return damage;
    }


    public void update(double deltaTime) {

        if (hasHitTarget()){
            //target.takeDamage(this);
            shotBehaviour.Shoot(target);
            this.destroy();
            return;
        }



        double centerX = getLayoutX() + getFitWidth() / 2;
        double centerY = getLayoutY() + getFitHeight() / 2;

        double dx = target.x - centerX + offset.x;
        double dy = target.y - centerY + offset.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < 1e-5) return;

        double vx = dx / distance * speed * deltaTime;
        double vy = dy / distance * speed * deltaTime;

        setLayoutX(getLayoutX() + vx);
        setLayoutY(getLayoutY() + vy);

        if (!type.equals("ArtilleryShell")){
            double angle = Math.toDegrees(Math.atan2(dy, dx));
            setRotate(angle - rotateOffset);
        }

    }

    public boolean hasHitTarget() {
        if (target == null || !target.isAlive()) return true;

        double centerX = getLayoutX() + getFitWidth() / 2;
        double centerY = getLayoutY() + getFitHeight() / 2;

        double dx = target.x - centerX + offset.x;
        double dy = target.y - centerY + offset.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < 15) {

            if (target.isAlive()) target.takeDamage(this);

            // let the enemy apply its own logic
            return true;
        }
        return false;
    }

    public void destroy() {
        // Remove from pane
        isActive = false;
        if (this.getParent() != null) {
            ((Pane) this.getParent()).getChildren().remove(this);
        }

        
    }

    public Enemy getTarget() {
        return target;
    }

}

