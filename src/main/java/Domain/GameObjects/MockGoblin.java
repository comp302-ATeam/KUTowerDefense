package Domain.GameObjects;

public class MockGoblin extends MockEnemy{

    public MockGoblin(int x, int y, String type, int healthPoints, double speed) {
        super(x, y, type, healthPoints, speed);
    }

    @Override
    protected int CalcDamage(Projectile projectile) {
        switch (projectile.type) {
            case "Arrow":
                return (int)(projectile.damage*1.5);
            case "Magic":
                return (int)(projectile.damage * 0.5);
            case "Artillery":
                return (projectile.damage);
            default:
                return (projectile.damage);
        }
    }
}
