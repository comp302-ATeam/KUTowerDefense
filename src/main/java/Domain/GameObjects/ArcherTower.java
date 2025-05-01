package Domain.GameObjects;

public class ArcherTower extends Tower {
    public ArcherTower(int x, int y) {
        // Archer tower has high fire rate but moderate damage
        super(x, y, 150, 10, 1.0, 100);  // range=150, damage=10, fireRate=1.0, cost=100
    }

    @Override
    public void attack(Enemy target) {
        if (target != null && canAttack()) {
            // Create a new arrow that will manage itself
            new Arrow(target.getX(), target.getY(), this.damage, "Arrow", target);
            updateLastAttackTime();
        }
    }
} 