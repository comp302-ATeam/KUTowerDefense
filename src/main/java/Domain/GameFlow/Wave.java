package Domain.GameFlow;

import Domain.GameObjects.Enemy;
import Domain.GameObjects.Goblin;
import Domain.GameObjects.Knight;

import java.util.ArrayList;

public class Wave {
    /* store enemies in an arraylist of enemies. we have the total count and the distribution of goblins
    * and knights. We also have the position x and y to spawn the wave.
    * */
    static ArrayList<Enemy> enemyWave = new ArrayList<>();
    int knightCount;
    int goblinCount;
    int groupCount;
    int enemyCount;
    int xPos;
    int yPos;
    public Wave(int knightCount, int goblinCount,int groupCount,int xPos, int yPos) {
        this.knightCount = knightCount;
        this.goblinCount = goblinCount;
        this.groupCount = groupCount;
        // we can figure out the enemy count by summation.
        this.enemyCount = knightCount + goblinCount;
        this.xPos = xPos;
        this.yPos = yPos;
    }
    // this is the main method for creating a wave. We create a wave by adding the right amount of knights and
    // goblins. We do this by the group count of times.
    public ArrayList<Enemy> createGoblinKnightWave(int goblinCount, int knightCount, int xPos, int yPos) {
        for (int j = 0; j < groupCount; j++) {
            for (int i = 0; i < goblinCount; i++) {
                enemyWave.add(new Goblin(xPos, yPos, "Goblin", 100, 1));
            }
            for (int i = 0; i < knightCount; i++) {
                enemyWave.add(new Knight(xPos, yPos, "Knight", 100, 1));
            }
        }
        return enemyWave;
    }
}
