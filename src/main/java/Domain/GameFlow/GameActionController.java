package Domain.GameFlow;

public class GameActionController {
    private static boolean isPaused; // we will be depending on this to check if game is Paused
    private double gameSpeed;
    private static final double maxSpeed = 8.0;
    private static final double defaultSpeed = 1.0;
    private static final double doubleSpeed = 2.0;
    private static double FPS = 8.0;

    public GameActionController() {
        this.isPaused = false;  // Game should be running as expected when this is false
        this.gameSpeed = 1.0;   // To define a regular speed
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

    public double getGameSpeed() {
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
