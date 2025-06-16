package Domain.GameFlow;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class AnimationPlayer {
    private final ImageView view;
    private final Timeline animation;
    private final int frameCount;
    private final int frameColumns;
    private final double frameWidth;
    private final double frameHeight;
    private int currentFrame = 0;
    private boolean isPlaying = false;
    private boolean isLooping = true;
    private Runnable onComplete;

    /**
     * Creates a new AnimationPlayer for a sprite sheet animation
     * @param spriteSheet The sprite sheet image
     * @param frameCount Total number of frames in the animation
     * @param frameColumns Number of columns in the sprite sheet
     * @param fps Frames per second for the animation
     */
    public AnimationPlayer(Image spriteSheet, int frameCount, int frameColumns, double fps) {
        this.frameCount = frameCount;
        this.frameColumns = frameColumns;
        this.frameWidth = spriteSheet.getWidth() / frameColumns;
        this.frameHeight = spriteSheet.getHeight();

        // Create the ImageView and set its initial viewport
        this.view = new ImageView(spriteSheet);
        view.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));

        // Create the animation timeline
        this.animation = new Timeline(
            new KeyFrame(Duration.seconds(1.0 / fps), e -> advanceFrame())
        );
        animation.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Gets the ImageView used for the animation
     * @return The ImageView instance
     */
    public ImageView getView() {
        return view;
    }

    /**
     * Plays the animation at the specified location
     * @param x X coordinate to play the animation
     * @param y Y coordinate to play the animation
     * @param gamePane The game pane to add the animation to
     */
    public void playAt(double x, double y, Pane gamePane) {
        // Position the view
        view.setTranslateX(x - frameWidth / 2);
        view.setTranslateY(y - frameHeight / 2);

        // Add to game pane if not already added
        if (view.getParent() == null) {
            gamePane.getChildren().add(view);
        }

        // Start the animation
        currentFrame = 0;
        isPlaying = true;
        animation.play();
    }

    /**
     * Stops the animation and removes it from the game pane
     */
    public void stop() {
        isPlaying = false;
        animation.stop();
        if (view.getParent() != null) {
            ((Pane) view.getParent()).getChildren().remove(view);
        }
    }

    /**
     * Sets whether the animation should loop
     * @param looping true to loop, false to play once
     */
    public void setLooping(boolean looping) {
        this.isLooping = looping;
        animation.setCycleCount(looping ? Animation.INDEFINITE : frameCount);
    }

    /**
     * Sets a callback to be executed when the animation completes
     * @param onComplete The callback to execute
     */
    public void setOnComplete(Runnable onComplete) {
        this.onComplete = onComplete;
    }

    /**
     * Advances to the next frame of the animation
     */
    private void advanceFrame() {
        currentFrame = (currentFrame + 1) % frameCount;
        int xOffset = (currentFrame % frameColumns) * (int)frameWidth;
        int yOffset = (currentFrame / frameColumns) * (int)frameHeight;
        view.setViewport(new Rectangle2D(xOffset, yOffset, frameWidth, frameHeight));

        // If we've reached the last frame and we're not looping
        if (currentFrame == frameCount - 1 && !isLooping) {
            stop();
            if (onComplete != null) {
                onComplete.run();
            }
        }
    }

    /**
     * Gets the current frame width
     * @return The width of each frame
     */
    public double getFrameWidth() {
        return frameWidth;
    }

    /**
     * Gets the current frame height
     * @return The height of each frame
     */
    public double getFrameHeight() {
        return frameHeight;
    }

    /**
     * Checks if the animation is currently playing
     * @return true if the animation is playing
     */
    public boolean isPlaying() {
        return isPlaying;
    }
}
