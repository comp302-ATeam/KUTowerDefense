package Domain.GameObjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ArcherTower extends Tower {


    private static final String PATH = "/Assets/Towers/Tower_archer128.png";
    private static final String UPGRADEPATH = "/Assets/phase2/archer up.png";

    public ArcherTower(int x, int y, Pane mapPane) {
        // Archer tower has high fire rate but moderate damage
        super(x, y, getSettingsRange(), getSettingsDamage(), getSettingsFireRate(), getSettingsCost(), mapPane);
        renderTower(PATH);
    }
    
    private static int getSettingsRange() {
        return Domain.GameFlow.GameSettingsManager.getInstance().getSettings().tower.archerRange;
    }
    
    private static int getSettingsDamage() {
        return Domain.GameFlow.GameSettingsManager.getInstance().getSettings().tower.arrowDamage;
    }
    
    private static double getSettingsFireRate() {
        return Domain.GameFlow.GameSettingsManager.getInstance().getSettings().tower.archerFireRate;
    }
    
    private static int getSettingsCost() {
        return Domain.GameFlow.GameSettingsManager.getInstance().getSettings().tower.archerCost;
    }

    @Override
    public void upgrade() {
        setImage(UPGRADEPATH);
        currentLevel++;
        attackRange = (int)(attackRange * 1.5);
        fireRate *= 2;
        damage = (int)(damage * 1.3);  // Increase damage by 30%
    }

    @Override
    public Projectile createProjectile(Enemy enemy){
        return new Arrow((int) x + 48, (int) y + 48, damage,enemy);
    }

    @Override
    public void update(double deltaTime) {
        attack();
    }
} 