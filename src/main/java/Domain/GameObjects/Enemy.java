package Domain.GameObjects;

public abstract class Enemy extends GameObject {
    /* enemy has 3 attributes, it has its own enemy type (goblin or knight),
    *  its health points and its speed.
    * */
    String enemyType;
    int healthPoints;
    double speed;
    // constructor for the enemy superclass
    public Enemy(int xPos, int yPos, String enemyType, int healthPoints, double speed) {
        super(xPos, yPos);
        this.enemyType = enemyType;
        this.healthPoints = healthPoints;
        this.speed = speed;
    }
    @Override
    public void update(double deltaTime) {

    }
    // calculate damage method is an abstract method which will be used accordingly for each class goblin or knight
    // this abstract method is used for calculating the damage took by
    // enemy based on its type (goblins take less magic damage)
    protected abstract int CalcDamage(Projectile projectile);

    // this method takes the calculated damage based on the subclass (goblin or knight) and deducts that
    // amount of damage from the health point of that enemy object
    // if the damage is lethal, then the enemy object calls the method Die()
    public void takeDamage(Projectile projectile) {
        int damageTaken = CalcDamage(projectile);
        healthPoints -= damageTaken;

        if( healthPoints <= 0 ) {
            Die();
        }
    }
    // to be implemented, if we are going to recycle the object created rather than destroy and create
    // this method should make this object to go back to its starting state.
    public void Die(){}
}
