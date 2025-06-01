package Domain;

import Domain.GameObjects.MockEnemy;
import Domain.GameObjects.Projectile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Overview:
 * This class tests the Enemy abstract data type, which represents enemies in a tower defense game.
 * Enemies have health points, speed, and type attributes. They can take damage from projectiles
 * and die when their health reaches zero. Different enemy types (like Goblins and Knights) have
 * different damage multipliers for different projectile types.
 *
 * Abstract Function:
 * AF(enemy) = An enemy in the game with:
 * - position (x, y)
 * - health points (healthPoints)
 * - speed (speed)
 * - type (enemyType)
 * - alive status (isAlive)
 *
 * Representation Invariant:
 * - healthPoints >= 0
 * - speed > 0
 * - enemyType != null
 * - x >= 0
 * - y >= 0
 */
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

    /**
     * Checks if the representation invariant holds
     * @return true if the representation invariant holds, false otherwise
     */
    private boolean repOk() {
        return mockEnemy.getHealth() >= 0 &&
                mockEnemy.getSpeed() > 0 &&
                mockEnemy.getEnemyType() != null &&
                mockEnemy.getX() >= 0 &&
                mockEnemy.getY() >= 0;
    }

    @Test
    void enemy_InitialStateIsCorrect() {
        // Then initial state should be correct
        assertEquals(100, mockEnemy.getHealth());
        assertTrue(mockEnemy.isAlive());
        assertEquals("TestEnemy", mockEnemy.getEnemyType());
        assertEquals(5, mockEnemy.getSpeed());
        assertTrue(repOk(), "Representation invariant should hold after initialization");
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
        assertTrue(repOk(), "Representation invariant should hold after taking damage");
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
        assertTrue(repOk(), "Representation invariant should hold after death");
    }

    @Test
    void takeDamage_HealthNeverGoesBelowZero() {
        // Given an enemy with 100 health points
        Projectile arrow = new Projectile(0, 0, 200, "Arrow", null, null);

        // When taking more damage than health
        mockEnemy.takeDamage(arrow);

        // Then health should be exactly 0
        assertEquals(0, mockEnemy.getHealth());
        assertFalse(mockEnemy.isAlive());
        assertTrue(repOk(), "Representation invariant should hold after taking excessive damage");
    }

    @Test
    void takeDamage_NoEffectWhenAlreadyDead() {
        // Given a dead enemy
        Projectile arrow = new Projectile(0, 0, 100, "Arrow", null, null);
        mockEnemy.takeDamage(arrow);
        assertFalse(mockEnemy.isAlive());
        int healthAfterDeath = mockEnemy.getHealth();

        // When taking more damage
        mockEnemy.takeDamage(arrow);

        // Then health should remain the same
        assertEquals(healthAfterDeath, mockEnemy.getHealth());
        assertFalse(mockEnemy.isAlive());
        assertTrue(repOk(), "Representation invariant should hold after taking damage while dead");
    }
}