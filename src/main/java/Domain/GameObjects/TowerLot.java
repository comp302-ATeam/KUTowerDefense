package Domain.GameObjects;

import javafx.scene.layout.Pane;

public class TowerLot extends Tower{

    private static final String PATH = "/Assets/Towers/TowerSlotwithoutbackground128.png";


    @Override
    public void upgrade() {

    }

    @Override
    public Projectile createProjectile(Enemy e){
        return null;
    }

    @Override
    public void update(double deltaTime) {}

    public TowerLot(int x, int y , Pane mapPane){
        super(x, y, 0, 0,0.0 , 0,mapPane);
        renderTower(PATH);
    }
}
