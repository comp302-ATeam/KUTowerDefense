package Domain;
import Domain.GameObjects.Arrow;
import Domain.GameObjects.Enemy;
import Domain.GameObjects.Goblin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;
public class EnemyTest {
    /**
     * requires: initial health > 0
     * modifies: this.health, this.alive
     * effects: subtracts damage from health (not below 0);
     *          sets alive=false if health reaches 0
     */
    @Test
    void takeDamage_ReducesHealthByExactAmount_whenDamageLessThanHealth() {
        // Given an Enemy with 10 health
        Goblin enemy = new Goblin(0,0,"Goblin",100,1,null);
        Arrow arrow = new Arrow(0,0,10,enemy,null);
        // When it takes 15 (10*1.5) damage
        enemy.takeDamage(arrow);

        // Then health should drop to 85 and it should still be alive
        assertEquals(85, enemy.getHealth());
        assertTrue(enemy.isAlive());
    }
    @Test
    void takeDamage_DoesNotGoBelowZero_andKills_whenDamageExceedsHealth() {
        // Given an Enemy with 2 health
        Goblin enemy = new Goblin(0,0,"Goblin",5,1,null);
        Arrow arrow = new Arrow(0,0,10,enemy,null);


        // When it takes 15 damage
        enemy.takeDamage(arrow);

        // Then health should bottom out at 0 and alive==false
        assertEquals(0, enemy.getHealth());
        assertFalse(enemy.isAlive());
    }
    @Test
    void takeDamage_LeavesDeadEnemyDead_whenAlreadyAtZeroHealth() {
        // Given an Enemy already at 0 health
        Goblin enemy = new Goblin(0,0,"Goblin",0,1,null);
        Arrow arrow = new Arrow(0,0,10,enemy,null);

        enemy.takeDamage(arrow);
        // Then it remains at 0 health and dead
        assertEquals(0, enemy.getHealth());
        assertFalse(enemy.isAlive());
    }
}
