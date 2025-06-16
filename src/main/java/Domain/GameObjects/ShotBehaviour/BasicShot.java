package Domain.GameObjects.ShotBehaviour;

import Domain.GameFlow.AnimationPlayer;
import Domain.GameObjects.Enemy;
import Domain.GameObjects.Projectile;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class BasicShot extends BaseBehaviour {
    private static final int EXPLOSION_FRAMES = 7;
    private static final int EXPLOSION_COLS = 7;
    private static final double EXPLOSION_FPS = 12.0;

    public BasicShot(Projectile projectile) {
        super(projectile);
    }



    @Override
    public boolean Shoot(Enemy target) {
        target.takeDamage(projectile);
        if(projectile.getType()=="MagicSpell"){
            Pane gamePane = (Pane) projectile.getParent();
            Image explosionSheet = new Image(getClass().getResourceAsStream("/Assets/Effects/Fire.png"));
            AnimationPlayer explosionAnim = new AnimationPlayer(explosionSheet, EXPLOSION_FRAMES, EXPLOSION_COLS, EXPLOSION_FPS);
            explosionAnim.setLooping(false);
            double explosionX = target.getX() + target.frameWidth / 2;
            double explosionY = target.getY() + target.frameHeight / 2;

            explosionAnim.setOnComplete(() -> {
                if (explosionAnim.isPlaying()) {
                    explosionAnim.stop();
                }
            });

            // Play the explosion animation
            explosionAnim.playAt(explosionX, explosionY, gamePane);
        }
        return false;
    }
}
