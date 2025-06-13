package Domain.GameObjects;


import javafx.scene.layout.Pane;

public class MageTower extends Tower {

    private static final String PATH = "/Assets/Towers/Tower_spell128.png";

    public MageTower(int x, int y, Pane mapPane) {
        // Mage tower has medium fire rate but high damage
        super(x, y, 180, 25, 1.0, 150,mapPane);  // range=180, damage=25, fireRate=1.0, cost=150
        renderTower(PATH);
    }



    @Override
    public void update(double deltaTime) {
        attack();
    }

} 