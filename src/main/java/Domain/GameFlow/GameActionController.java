package Domain.GameFlow;

public class GameActionController {
    private static GameActionController instance;

    private boolean isPaused;
    private double gameSpeed;
    private static final double maxSpeed = 8.0;
    private static final double defaultSpeed = 1.0;
    private static final double doubleSpeed = 2.0;
    private double FPS = 8.0;

    private GameActionController() {
        this.isPaused = false;
        this.gameSpeed = defaultSpeed;
    }

    public static synchronized GameActionController getInstance() {
        if (instance == null) {
            instance = new GameActionController();
        }
        return instance;
    }

    public double getFPS() {
        return FPS;
    }

    public void setFPS(double FPS) {
        this.FPS = FPS;
    }

    public boolean isPaused() {
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
        if (!isPaused) {
            this.isPaused = true;
            FPS = 0;
            System.out.println("Game is Paused");
        }
    }

    public void resumeGame() {
        if (isPaused) {
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