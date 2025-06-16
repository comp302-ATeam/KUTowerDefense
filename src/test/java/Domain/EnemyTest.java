package Domain;
import Domain.GameObjects.Arrow;
import Domain.GameObjects.Enemy;
import Domain.GameObjects.Goblin;
import Domain.GameObjects.MockGoblin;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

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

        MockGoblin goblin = new MockGoblin(0,0,"Goblin",100,1);
        Arrow arrow = new Arrow(0,0,10,null);
        // When it takes 15 (10*1.5) damage
        goblin.takeDamage(arrow);

        // Then health should drop to 85 and it should still be alive
        assertEquals(90, goblin.getHealth());
        assertTrue(goblin.isAlive());
    }
    @Test
    void takeDamage_DoesNotGoBelowZero_andKills_whenDamageExceedsHealth() {
        // Given an Enemy with 2 health

        MockGoblin goblin = new MockGoblin(0,0,"Goblin",2,1);
        Arrow arrow = new Arrow(0,0,15,null);


        // When it takes 15 damage
        goblin.takeDamage(arrow);

        // Then health should bottom out at 0 and alive==false
        assertEquals(0, goblin.getHealth());
        assertFalse(goblin.isAlive());
    }
    @Test
    void takeDamage_LeavesDeadEnemyDead_whenAlreadyAtZeroHealth() {
        // Given an Enemy already at 0 health

        MockGoblin goblin = new MockGoblin(0,0,"Goblin",0,1);
        Arrow arrow = new Arrow(0,0,10,null);

        goblin.takeDamage(arrow);
        // Then it remains at 0 health and dead
        assertEquals(0, goblin.getHealth());
        assertFalse(goblin.isAlive());
    }
}
