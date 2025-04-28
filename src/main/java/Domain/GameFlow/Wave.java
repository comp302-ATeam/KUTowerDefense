package Domain.GameFlow;

import Domain.GameObjects.Enemy;
import Domain.GameObjects.Goblin;
import Domain.GameObjects.Knight;

import java.util.ArrayList;

public class Wave {
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
        this.enemyCount = knightCount + goblinCount;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public ArrayList<Enemy> createGoblinKnightWave(int goblinCount, int knightCount, int xPos, int yPos) {
        for (int j = 0; j < groupCount; j++) {
            for (int i = 0; i < goblinCount; i++) {
                enemyWave.add(new Goblin(xPos, yPos, "Goblin", 100, 1));
                waitForEnemy();
            }
            for (int i = 0; i < knightCount; i++) {
                enemyWave.add(new Knight(xPos, yPos, "Knight", 100, 1));
                waitForEnemy();
            }
            waitForEnemy();
        }
        return enemyWave;
    }
    public void waitForGroup(){};
    public void waitForEnemy(){};
}
