package Domain.GameObjects;
import javafx.scene.image.Image;

import java.util.Vector;

public abstract class GameObject {
    protected Image ObjectImage;
    protected Vector<Integer> position ;
    protected Vector<Float> scale ;
    protected float rotation = 0.0f;
    protected boolean isAlive;            // Whether the object is active in the game


    public GameObject(int xPos, int yPos) {
        position =  new Vector<>(xPos,yPos);
        scale =  new Vector<>(1,1);
        this.isAlive = true;
    }

    // Getters
    public int getX() { return 0; }
    public int getY() { return 0; }
    public boolean isAlive() { return isAlive; }

    // Setters
    public void setX(int x){} //{ this.x = x; }
    public void setY(int y) {}//{ this.y = y; }
    public void setAlive(boolean alive) { this.isAlive = alive; }

    // Method to update object state
    public abstract void update(double deltaTime);

    // Method to calculate distance to another object
    /*public double distanceTo(GameObject other) {
        return Math.sqrt(
            Math.pow(this.x - other.x, 2) + 
            Math.pow(this.y - other.y, 2)
        );
    }*/
} 