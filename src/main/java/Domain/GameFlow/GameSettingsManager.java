package Domain.GameFlow;

import java.io.*;

/**
 * Singleton class that manages game settings throughout the application.
 * Provides a single source of truth for all configuration values.
 */
public class GameSettingsManager {
    private static GameSettingsManager instance;
    private GameSettings currentSettings;
    private static final String SETTINGS_FILE_PATH = "src/data/OptionsSettings.dat";

    // Private constructor to prevent direct instantiation
    private GameSettingsManager() {
        loadSettings();
    }

    /**
     * Gets the singleton instance of GameSettingsManager.
     * @return The singleton instance
     */
    public static synchronized GameSettingsManager getInstance() {
        if (instance == null) {
            instance = new GameSettingsManager();
        }
        return instance;
    }

    /**
     * Gets the current game settings.
     * @return Current GameSettings object
     */
    public GameSettings getSettings() {
        return currentSettings;
    }

    /**
     * Updates the current settings and saves them to disk.
     * @param settings New settings to apply
     */
    public synchronized void updateSettings(GameSettings settings) {
        this.currentSettings = settings;
        saveSettings();
        System.out.println("🔄 GameSettingsManager: Settings updated and saved");
    }

    /**
     * Reloads settings from disk.
     */
    public synchronized void reloadSettings() {
        loadSettings();
        System.out.println("🔄 GameSettingsManager: Settings reloaded from disk");
    }

    /**
     * Resets settings to defaults and saves them.
     */
    public synchronized void resetToDefaults() {
        this.currentSettings = createDefaultSettings();
        saveSettings();
        System.out.println("🔄 GameSettingsManager: Settings reset to defaults");
    }

    /**
     * Loads settings from disk. If file doesn't exist, creates default settings.
     */
    private void loadSettings() {
        File file = new File(SETTINGS_FILE_PATH);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            this.currentSettings = (GameSettings) ois.readObject();
            System.out.println("✅ GameSettingsManager: Loaded settings from " + file.getAbsolutePath());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠️ GameSettingsManager: Could not load settings (" + e.getMessage() + "), using defaults");
            this.currentSettings = createDefaultSettings();
        }
    }

    /**
     * Saves current settings to disk.
     */
    private void saveSettings() {
        File file = new File(SETTINGS_FILE_PATH);
        try {
            file.getParentFile().mkdirs(); // Ensure directory exists
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(currentSettings);
                System.out.println("✅ GameSettingsManager: Saved settings to " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("❌ GameSettingsManager: Could not save settings: " + e.getMessage());
        }
    }

    /**
     * Creates default game settings.
     * @return GameSettings with default values
     */
    private GameSettings createDefaultSettings() {
        GameSettings settings = new GameSettings();
        
        // Initialize all sub-objects
        settings.waves = new GameSettings.Waves();
        settings.enemy = new GameSettings.Enemy();
        settings.tower = new GameSettings.Tower();
        settings.player = new GameSettings.Player();
        
        // Wave defaults
        settings.waves.numWaves = 10;
        settings.waves.groupsPerWave = 3;
        settings.waves.enemiesPerGroup = 5;
        settings.waves.goblinsPerGroup = 3;
        settings.waves.knightsPerGroup = 2;
        settings.waves.delayBetweenWaves = 5.0;
        settings.waves.delayBetweenGroups = 2.0;
        settings.waves.delayBetweenEnemies = 0.5;

        // Enemy defaults
        settings.enemy.goblinHP = 20;
        settings.enemy.knightHP = 40;
        settings.enemy.goblinGold = 5;
        settings.enemy.knightGold = 10;
        settings.enemy.goblinSpeed = 1.0;
        settings.enemy.knightSpeed = 0.8;

        // Tower defaults
        settings.tower.archerCost = 50;
        settings.tower.artilleryCost = 70;
        settings.tower.mageCost = 60;
        settings.tower.archerRange = 150;
        settings.tower.artilleryRange = 120;
        settings.tower.mageRange = 140;
        settings.tower.aoeRadius = 50;
        settings.tower.arrowDamage = 5;
        settings.tower.artilleryDamage = 10;
        settings.tower.magicDamage = 7;
        settings.tower.archerFireRate = 1.5;
        settings.tower.artilleryFireRate = 2.0;
        settings.tower.mageFireRate = 1.8;
        
        // Player defaults
        settings.player.startingGold = 100;
        settings.player.startingHP = 10;
        settings.player.musicOn = true;
        settings.player.sfxOn = true;
        
        return settings;
    }

    /**
     * Gets player settings specifically.
     * @return Player settings
     */
    public GameSettings.Player getPlayerSettings() {
        return currentSettings.player;
    }

    /**
     * Gets wave settings specifically.
     * @return Wave settings
     */
    public GameSettings.Waves getWaveSettings() {
        return currentSettings.waves;
    }

    /**
     * Gets enemy settings specifically.
     * @return Enemy settings
     */
    public GameSettings.Enemy getEnemySettings() {
        return currentSettings.enemy;
    }

    /**
     * Gets tower settings specifically.
     * @return Tower settings
     */
    public GameSettings.Tower getTowerSettings() {
        return currentSettings.tower;
    }
} 