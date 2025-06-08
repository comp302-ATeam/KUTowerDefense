package Domain.PlayerData;

import java.util.ArrayList;
import java.util.List;

public class PlayerStats {
    private static PlayerStats instance;
    
    private int gold;
    private int lives;
    private int currentWave;
    
    // Callback interface for gold changes
    public interface GoldChangeListener {
        void onGoldChanged(int newGold);
    }
    
    private List<GoldChangeListener> goldListeners = new ArrayList<>();
    
    // Private constructor for singleton
    private PlayerStats() {
        this.gold = 100;  // Default starting gold
        this.lives = 10;  // Default starting lives  
        this.currentWave = 1;
    }
    
    // Singleton getInstance method
    public static PlayerStats getInstance() {
        if (instance == null) {
            instance = new PlayerStats();
        }
        return instance;
    }
    
    // Listener management
    public void addGoldChangeListener(GoldChangeListener listener) {
        goldListeners.add(listener);
    }
    
    public void removeGoldChangeListener(GoldChangeListener listener) {
        goldListeners.remove(listener);
    }
    
    private void notifyGoldListeners() {
        for (GoldChangeListener listener : goldListeners) {
            listener.onGoldChanged(gold);
        }
    }
    
    // Gold management methods
    public int getGold() {
        return gold;
    }
    
    public void addGold(int amount) {
        this.gold += amount;
        System.out.println("Added " + amount + " gold. Total: " + gold);
        notifyGoldListeners();
    }
    
    public boolean spendGold(int amount) {
        if (gold >= amount) {
            this.gold -= amount;
            System.out.println("Spent " + amount + " gold. Remaining: " + gold);
            notifyGoldListeners();
            return true;
        }
        System.out.println("Not enough gold! Need " + amount + ", have " + gold);
        return false;
    }
    
    public void setGold(int gold) {
        this.gold = gold;
        notifyGoldListeners();
    }
    
    // Lives management methods
    public int getLives() {
        return lives;
    }
    
    public void loseLife() {
        this.lives--;
    }
    
    public void setLives(int lives) {
        this.lives = lives;
    }
    
    // Wave management methods
    public int getCurrentWave() {
        return currentWave;
    }
    
    public void nextWave() {
        this.currentWave++;
    }
    
    public void setCurrentWave(int wave) {
        this.currentWave = wave;
    }
    
    // Reset method for new games
    public void resetStats() {
        this.gold = 100;
        this.lives = 10;
        this.currentWave = 1;
        notifyGoldListeners();
    }
    
    // Initialize with custom starting values
    public void initializeStats(int startingGold, int startingLives) {
        this.gold = startingGold;
        this.lives = startingLives;
        this.currentWave = 1;
        notifyGoldListeners();
    }
}

// test
