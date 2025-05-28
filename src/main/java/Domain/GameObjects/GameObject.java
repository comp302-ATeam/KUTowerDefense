package Domain.GameObjects;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * GameObject is the base class for all game objects in the tower defense game.
 * It provides basic functionality for position, scale, rotation, and life state.
 * All game objects (towers, enemies, projectiles) inherit from this class.
 */
public abstract class GameObject {
    // Position of the object in the game world
    protected double x;
    protected double y;
    // Scale factors for the object's size (not sure if we need, but let's keep it)
    protected float scaleX = 1.0f;
    protected float scaleY = 1.0f;
    // Rotation angle in degrees (0 means no rotation)
    protected float rotation = 0.0f;
    // Whether the object is active in the game
    protected boolean isAlive;
    protected ImageView imageObject;

    /**
     * Creates a new game object at the specified position.
     * @param xPos X coordinate of the object
     * @param yPos Y coordinate of the object
     */
    public GameObject(double xPos, double yPos, ImageView imageObject) {
        this.x = xPos;
        this.y = yPos;
        this.isAlive = true;
        this.imageObject = imageObject;
        updateViewTransform();

    }

    // Getters for object properties
    public double getX() { return x; }
    public double getY() { return y; }
    public float getScaleX() { return scaleX; }
    public float getScaleY() { return scaleY; }
    public float getRotation() { return rotation; }
    public boolean isAlive() { return isAlive; }

    // Setters for object properties
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setScaleX(float scaleX) { this.scaleX = scaleX; }
    public void setScaleY(float scaleY) { this.scaleY = scaleY; }
    public void setRotation(float rotation) { this.rotation = rotation; }
    public void setAlive(boolean alive) { this.isAlive = alive; }

    /**
     * Updates the object's state based on the elapsed time.
     * This method must be implemented by all subclasses.
     * @param deltaTime Time elapsed since last update in seconds
     */
    public abstract void update(double deltaTime);

    /**
     * Calculates the distance between this object and another game object.
     * @param other The other game object to measure distance to
     * @return The distance between the two objects
     */
    public double distanceTo(GameObject other) {
        return Math.sqrt(
                Math.pow(this.x - other.x, 2) +
                        Math.pow(this.y - other.y, 2)
        );
    }

    public Node getView() {
        return imageObject;
    }
    public void updateViewTransform() {
        if(imageObject != null) {
            imageObject.setTranslateX(x);
            imageObject.setTranslateY(y);
            imageObject.setScaleX(scaleX);
            imageObject.setScaleY(scaleY);
            imageObject.setRotate(rotation);
        }
    }
}