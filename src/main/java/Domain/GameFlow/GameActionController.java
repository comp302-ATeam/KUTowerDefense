package Domain.GameFlow;

import Domain.GameObjects.Tower;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.List;

public class GameActionController {
    // Singleton instance
    private static GameActionController instance;

    public static boolean isPaused; // we will be depending on this to check if game is Paused
    public static double gameSpeed;
    private static final double maxSpeed = 8.0;
    public static final double defaultSpeed = 1.0;
    private static final double doubleSpeed = 2.0;
    private static double FPS = 8.0;

    public static List<Tower>  towerList =  new ArrayList<>();


    // Private constructor to prevent direct instantiation
    private GameActionController() {
        this.isPaused = false;  // Game should be running as expected when this is false
        this.gameSpeed = 1.0;   // To define a regular speed

        new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double deltaTime = (now - lastTime) / 1_000_000_000.0; // convert to seconds
                lastTime = now;

                updateGame(deltaTime);
            }
        }.start();
    }

    // Singleton getInstance method
    public static GameActionController getInstance() {
        if (instance == null) {
            instance = new GameActionController();
        }
        return instance;
    }

    public void updateGame(double deltaTime) {
        for (Tower tower : towerList) {
            tower.update(deltaTime);
        }
    }

    public static double getFPS(){
        return FPS;
    }
    public void setFPS(double FPS){
        this.FPS = FPS;
    }
    public static boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public static double getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(double gameSpeed) {
        this.gameSpeed = gameSpeed;
    }
    public void pauseGame() {
        if (!isPaused) {  // Checks if it is not paused
            this.isPaused = true;
            FPS = 0;
            System.out.println("Game is Paused");
        }
    }
    public void resumeGame() {
        if (isPaused) {  // Checks if it is paused
            this.isPaused = false;
            FPS = 8.0;
            System.out.println("Game Resumes");
        }
    }
    public void speedUpGame() {
        if (isPaused) {
            System.out.println("You can't speed up the game while it is paused");
            return;
        }

        if (gameSpeed * doubleSpeed <= maxSpeed) {
            gameSpeed *= doubleSpeed;
        } else {
            gameSpeed = defaultSpeed;
        }
        System.out.println("Game speed set to: " + gameSpeed + "x");
    }
}
