package Domain.GameObjects;

import UI.GameSceneController;
import javafx.animation.TranslateTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import java.util.Random;
import javafx.animation.Animation;
import javafx.application.Platform;

/**
 * GoldPouch handles spawning, flight animation, and click-to-collect logic.
 */
public class GoldPouch extends GameObject {
    private static final int MIN_GOLD_AMOUNT = 2;
    private static final int MAX_GOLD_AMOUNT = 20;
    private static final int FRAME_COUNT = 7;
    private static final double FRAME_DURATION = 0.1;
    private static final double FLY_DISTANCE = 50.0;
    private static final double FLY_DURATION = 0.5;
    private static final double DISAPPEAR_DURATION = 10.0;

    private final ImageView view;
    private final double spawnX, spawnY;
    private final double frameWidth;
    private int currentFrame = 0;
    private Timeline spriteAnimation;
    private static GameSceneController gameSceneController;
    private static final Random random = new Random();
    private boolean isCollected = false;
    private TranslateTransition flyAnimation;
    private PauseTransition disappearTimer;
    private final int goldAmount;

    public static void setGameSceneController(GameSceneController controller) {
        gameSceneController = controller;
    }

    /**
     * Constructs a pouch at (x,y), flies it, animates, and enables click-to-collect.
     */
    public GoldPouch(double x, double y) {
        super(x, y, new ImageView());
        this.spawnX = x;
        this.spawnY = y;
        this.view = (ImageView) getView();
        this.goldAmount = MIN_GOLD_AMOUNT + random.nextInt(MAX_GOLD_AMOUNT - MIN_GOLD_AMOUNT + 1);

        // Size and initial position
        view.setFitWidth(64);
        view.setFitHeight(64);
        view.setTranslateX(x);
        view.setTranslateY(y);

        // Load sprite sheet
        Image sheet = new Image(getClass().getResourceAsStream("/assets/phase2/G_Spawn.png"));
        this.frameWidth = sheet.getWidth() / FRAME_COUNT;
        view.setImage(sheet);
        view.setViewport(new Rectangle2D(0, 0, frameWidth, sheet.getHeight()));

        // Enable clicking to collect
        view.setOnMouseClicked((MouseEvent e) -> collect());

        // Start flight and sprite animation
        double endX = spawnX + (random.nextDouble() * FLY_DISTANCE * 2 - FLY_DISTANCE);
        double endY = spawnY + FLY_DISTANCE;
        flyAndAnimate(endX, endY);

        // Set up disappear timer
        disappearTimer = new PauseTransition(Duration.seconds(DISAPPEAR_DURATION));
        disappearTimer.setOnFinished(e -> {
            if (!isCollected) {
                Platform.runLater(() -> {
                    if (view != null && view.getParent() != null) {
                        javafx.scene.Parent parent = view.getParent();
                        if (parent instanceof javafx.scene.layout.Pane) {
                            ((javafx.scene.layout.Pane) parent).getChildren().remove(view);
                        } else if (parent instanceof javafx.scene.Group) {
                            ((javafx.scene.Group) parent).getChildren().remove(view);
                        }
                    }
                });
            }
        });
        disappearTimer.play();
    }

    /**
     * Flies straight to (endX,endY) while animating the sprite frames.
     */
    private void flyAndAnimate(double endX, double endY) {
        // Sprite animation
        spriteAnimation = new Timeline(new KeyFrame(Duration.seconds(FRAME_DURATION), evt -> {
            currentFrame = Math.min(currentFrame + 1, FRAME_COUNT - 1);
            view.setViewport(new Rectangle2D(
                    currentFrame * frameWidth,
                    0,
                    frameWidth,
                    view.getImage().getHeight()
            ));
        }));
        spriteAnimation.setCycleCount(FRAME_COUNT);

        // Flight transition
        flyAnimation = new TranslateTransition(Duration.seconds(FLY_DURATION), view);
        flyAnimation.setFromX(spawnX);
        flyAnimation.setFromY(spawnY);
        flyAnimation.setToX(endX);
        flyAnimation.setToY(endY);
        flyAnimation.setInterpolator(Interpolator.EASE_IN);

        // Play both in parallel
        ParallelTransition pt = new ParallelTransition(flyAnimation, spriteAnimation);
        pt.play();
    }

    private void startSpriteAnimation() {
        spriteAnimation = new Timeline(
            new KeyFrame(Duration.seconds(FRAME_DURATION), e -> {
                currentFrame = (currentFrame + 1) % FRAME_COUNT;
                view.setViewport(new Rectangle2D(currentFrame * frameWidth, 0, frameWidth, view.getImage().getHeight()));
                
                // When we reach the last frame, start the flying animation
                if (currentFrame == FRAME_COUNT - 1) {
                    startFlyingAnimation();
                }
            })
        );
        spriteAnimation.setCycleCount(FRAME_COUNT); // Play exactly one cycle
        spriteAnimation.play();
    }

    private void startFlyingAnimation() {
        // Calculate random end position within FLY_DISTANCE
        double angle = random.nextDouble() * 2 * Math.PI;
        double endX = spawnX + Math.cos(angle) * FLY_DISTANCE;
        double endY = spawnY + Math.sin(angle) * FLY_DISTANCE;

        // Create flying animation
        flyAnimation = new TranslateTransition(Duration.seconds(FLY_DURATION), view);
        flyAnimation.setFromX(spawnX);
        flyAnimation.setFromY(spawnY);
        flyAnimation.setToX(endX);
        flyAnimation.setToY(endY);
        flyAnimation.setCycleCount(1);
        flyAnimation.play();
    }

    /**
     * Collects the pouch: stops animation, removes view, and updates player gold.
     */
    public void collect() {
        if (!isCollected) {
            isCollected = true;
            
            // Stop animations safely
            if (flyAnimation != null && flyAnimation.getStatus() == Animation.Status.RUNNING) {
                flyAnimation.stop();
            }
            if (spriteAnimation != null && spriteAnimation.getStatus() == Animation.Status.RUNNING) {
                spriteAnimation.stop();
            }
            if (disappearTimer != null && disappearTimer.getStatus() == Animation.Status.RUNNING) {
                disappearTimer.stop();
            }
            
            // Remove from parent container
            if (view != null && view.getParent() != null) {
                javafx.scene.Parent parent = view.getParent();
                if (parent instanceof javafx.scene.layout.Pane) {
                    ((javafx.scene.layout.Pane) parent).getChildren().remove(view);
                } else if (parent instanceof javafx.scene.Group) {
                    ((javafx.scene.Group) parent).getChildren().remove(view);
                }
            }
            
            // Add gold to player's total
            if (gameSceneController != null) {
                int currentGold = Integer.parseInt(gameSceneController.getLabelGold().getText());
                gameSceneController.updateGold(currentGold + goldAmount);
            }
        }
    }

    public static GoldPouch spawnAt(double x, double y) {
        return new GoldPouch(x, y);
    }

    public static int getGoldAmount() {
        return MIN_GOLD_AMOUNT + random.nextInt(MAX_GOLD_AMOUNT - MIN_GOLD_AMOUNT + 1);
    }

    @Override
    public void update(double deltaTime) {

    }
}
 