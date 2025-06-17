package Domain.GameObjects;


import javafx.scene.layout.Pane;

public class MageTower extends Tower {

    private static final String PATH = "/Assets/Towers/Tower_spell128.png";
    private static final String UPGRADEPATH = "/Assets/phase2/mage up.png";

    public MageTower(int x, int y, Pane mapPane) {
        // Mage tower has medium fire rate but high damage
        super(x, y, getSettingsRange(), getSettingsDamage(), getSettingsFireRate(), getSettingsCost(), mapPane);
        renderTower(PATH);
    }
    
    private static int getSettingsRange() {
        return Domain.GameFlow.GameSettingsManager.getInstance().getSettings().tower.mageRange;
    }
    
    private static int getSettingsDamage() {
        return Domain.GameFlow.GameSettingsManager.getInstance().getSettings().tower.magicDamage;
    }
    
    private static double getSettingsFireRate() {
        return Domain.GameFlow.GameSettingsManager.getInstance().getSettings().tower.mageFireRate;
    }
    
    private static int getSettingsCost() {
        return Domain.GameFlow.GameSettingsManager.getInstance().getSettings().tower.mageCost;
    }

    @Override
    public void upgrade() {
        setImage(UPGRADEPATH);
        currentLevel++;
        damage = (int)(damage * 1.5);  // Increase damage by 50%
        attackRange = (int)(attackRange * 1.2);  // Increase range by 20%
    }

    @Override
    public Projectile createProjectile(Enemy enemy){
        return new MagicSpell((int) x + 48, (int) y + 48, damage,enemy);
    }

    @Override
    public void update(double deltaTime) {
        attack();
    }

} 