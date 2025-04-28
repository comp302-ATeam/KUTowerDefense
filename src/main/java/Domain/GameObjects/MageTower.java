package Domain.GameObjects;

public class MageTower extends Tower {
    public MageTower(int x, int y) {
        // Mage tower has medium fire rate but high damage
        super(x, y, 180, 25, 1.0, 150);  // range=180, damage=25, fireRate=1.0, cost=150
    }

    @Override
    public void attack(Enemy target) {
        if (target != null && canAttack()) {
            // Create a new magic spell that will manage itself
            new MagicSpell(target.getX(), target.getY(),20,target);
            updateLastAttackTime();
        }
    }
} 