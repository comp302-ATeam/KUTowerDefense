package Domain;

import Domain.GameObjects.MockEnemy;
import Domain.GameObjects.Projectile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyClassTest {
    private MockEnemy mockEnemy;

    @BeforeEach
    void setUp() {
        mockEnemy = new MockEnemy(0, 0, "TestEnemy", 100, 5) {
            @Override
            protected int CalcDamage(Projectile projectile) {
                return (int)(projectile.getDamage() * 1.25); // Ok hasarı için 1.25x çarpan
            }
        };
    }

    @Test
    void enemy_InitialStateIsCorrect() {
        // Then initial state should be correct
        assertEquals(100, mockEnemy.getHealth());
        assertTrue(mockEnemy.isAlive());
        assertEquals("TestEnemy", mockEnemy.getEnemyType());
        assertEquals(5, mockEnemy.getSpeed());
    }

    @Test
    void takeDamage_ReducesHealthPoints() {
        // Given an enemy with 100 health points
        int initialHealth = mockEnemy.getHealth();

        // When taking damage from an arrow
        Projectile arrow = new Projectile(0, 0, 10, "Arrow", null, null);
        mockEnemy.takeDamage(arrow);

        // Then health points should be reduced by correct amount
        assertEquals(87, mockEnemy.getHealth()); // 100 - (10 * 1.25)
        assertTrue(mockEnemy.isAlive());
    }

    @Test
    void takeDamage_DiesWhenHealthReachesZero() {
        // Given an enemy with 100 health points
        Projectile arrow = new Projectile(0, 0, 100, "Arrow", null, null);

        // When taking lethal damage
        mockEnemy.takeDamage(arrow);

        // Then enemy should die
        assertFalse(mockEnemy.isAlive());
        assertEquals(0, mockEnemy.getHealth());
    }
}