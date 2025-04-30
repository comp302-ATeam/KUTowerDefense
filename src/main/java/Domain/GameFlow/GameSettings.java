package Domain.GameFlow;

// In GameSettings.java
import java.io.Serializable;

public class GameSettings implements Serializable {

    public Waves waves;
    public Enemy enemy;
    public Tower tower;
    public Player player;

    public static class Waves implements Serializable {
        public int numWaves, groupsPerWave, enemiesPerGroup, goblinsPerGroup, knightsPerGroup;
        public double delayBetweenWaves, delayBetweenGroups, delayBetweenEnemies;
    }

    public static class Enemy implements Serializable {
        public int goblinHP, knightHP, goblinGold, knightGold;
        public double goblinSpeed, knightSpeed;
    }

    public static class Tower implements Serializable {
        public int archerCost, artilleryCost, mageCost;
        public int archerRange, artilleryRange, mageRange;
        public int aoeRadius;
        public int arrowDamage, artilleryDamage, magicDamage;
        public double archerFireRate, artilleryFireRate, mageFireRate;
    }

    public static class Player implements Serializable {
        public int startingHP;
        public int startingGold;
    }
}
