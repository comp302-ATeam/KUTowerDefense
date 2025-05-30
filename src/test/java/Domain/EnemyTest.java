package Domain.GameFlow;

import Domain.GameObjects.Enemy;
import Domain.GameObjects.MockEnemy;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class WaveTest {
    private Wave wave;
    private Pane mockPane;
    private Vector2<Double>[] mockPath;

    @BeforeEach
    void setUp() {
        // Initialize with 2 knights, 2 goblins, and 2 groups
        mockPane = new Pane();
        mockPath = new Vector2[2];
        mockPath[0] = new Vector2<>(0.0, 0.0);
        mockPath[1] = new Vector2<>(100.0, 100.0);
        wave = new Wave(1, 2, 2, 2, 0, 0, mockPane, mockPath);
    }


}