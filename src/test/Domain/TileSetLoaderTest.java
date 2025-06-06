package Domain;

import Domain.GameFlow.Tile;
import Domain.GameFlow.TileSetLoader;
import Domain.GameFlow.Vector2;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for TileSetLoader.createPath method
 */
public class TileSetLoaderTest {

    private TileSetLoader tileSetLoader;
    private Tile[][] testGrid;
    private Vector2<Integer> mapSize;

    @BeforeAll
    static void initJavaFX() {
        // Initialize JavaFX toolkit for testing
        new JFXPanel();
    }

    @BeforeEach
    void setUp() throws Exception {
        // Create a test grid pane
        GridPane testPane = new GridPane();

        // Initialize TileSetLoader with a 5x5 grid
        tileSetLoader = new TileSetLoader(testPane, 5, 5);

        // Set up test grid and map size using reflection
        mapSize = new Vector2<>(5, 5);
        testGrid = new Tile[5][5];

        setPrivateField("tileGrid", testGrid);
        setPrivateField("mapSize", mapSize);
    }

    private void setPrivateField(String fieldName, Object value) throws Exception {
        Field field = TileSetLoader.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(tileSetLoader, value);
    }

    /**
     * Requires: prevTile, startTile, and endTile are not null and have valid positions within the grid bounds.
     * Modifies: None (method is pure - doesn't modify object state).
     * Effects: Returns a List<Tile> representing the path from startTile to endTile if a valid path exists,
     *          or null if no valid path can be found. The path includes all tiles from start to end in order.
     *          Uses recursive pathfinding to traverse connected PATH tiles based on their directional constraints.
     */
    private List<Tile> callCreatePath(Tile prevTile, Tile startTile, Tile endTile) throws Exception {
        Method method = TileSetLoader.class.getDeclaredMethod("createPath", Tile.class, Tile.class, Tile.class);
        method.setAccessible(true);
        return (List<Tile>) method.invoke(tileSetLoader, prevTile, startTile, endTile);
    }

    private void createStraightPath() {
        // Create a straight horizontal path from (0,2) to (4,2)
        // Path tiles: (0,2) -> (1,2) -> (2,2) -> (3,2) -> (4,2)
        for (int x = 0; x < 5; x++) {
            testGrid[x][2] = new Tile(1, Tile.TileType.PATH, new Vector2<>(x, 2)); // tile index 1 = LEFT-RIGHT
        }

        // Fill non-path tiles with decorative tiles
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (y != 2) {
                    testGrid[x][y] = new Tile(5, Tile.TileType.DECOR, new Vector2<>(x, y));
                }
            }
        }
    }

    private void createLShapedPath() {
        // Create an L-shaped path: (0,0) -> (2,0) -> (2,2)
        testGrid[0][0] = new Tile(0, Tile.TileType.PATH, new Vector2<>(0, 0)); // RIGHT-DOWN corner
        testGrid[1][0] = new Tile(1, Tile.TileType.PATH, new Vector2<>(1, 0)); // LEFT-RIGHT
        testGrid[2][0] = new Tile(2, Tile.TileType.PATH, new Vector2<>(2, 0)); // LEFT-DOWN corner
        testGrid[2][1] = new Tile(4, Tile.TileType.PATH, new Vector2<>(2, 1)); // UP-DOWN
        testGrid[2][2] = new Tile(11, Tile.TileType.PATH, new Vector2<>(2, 2)); // UP end

        // Fill remaining tiles with decorative tiles
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (testGrid[x][y] == null) {
                    testGrid[x][y] = new Tile(5, Tile.TileType.DECOR, new Vector2<>(x, y));
                }
            }
        }
    }



    /**
     * Test Case 1: Base case - start and end tiles are the same
     * Tests that the method correctly handles the recursive base case
     */
    @Test
    void testCreatePath_SameTileStartAndEnd() throws Exception {
        // Arrange
        createStraightPath();
        Tile startTile = testGrid[2][2];
        Tile endTile = testGrid[2][2];
        Tile prevTile = testGrid[1][2];

        // Act
        List<Tile> result = callCreatePath(prevTile, startTile, endTile);

        // Assert
        assertNotNull(result, "Path should not be null when start equals end");
        assertEquals(1, result.size(), "Path should contain exactly one tile when start equals end");
        assertEquals(endTile, result.get(0), "The single tile should be the end tile");
        assertEquals(startTile.position, result.get(0).position, "Position should match start/end tile");
    }

    /**
     * Test Case 2: Valid straight path
     * Tests pathfinding along a simple horizontal path
     */
    @Test
    void testCreatePath_ValidStraightPath() throws Exception {
        // Arrange
        createStraightPath();
        Tile prevTile = testGrid[0][2];  // Previous tile (will be marked as valid)
        Tile startTile = testGrid[1][2]; // Start from position (1,2)
        Tile endTile = testGrid[4][2];   // End at position (4,2)

        // Act
        List<Tile> result = callCreatePath(prevTile, startTile, endTile);

        // Assert
        assertNotNull(result, "Path should be found for valid straight path");
        assertTrue(result.size() >= 2, "Path should contain at least start and end tiles");

        // Verify path contains the start tile
        assertTrue(result.contains(startTile), "Path should contain the start tile");

        // Verify path progression (tiles should be in reverse order due to recursive addition)
        Vector2<Integer> lastPos = result.get(result.size() - 1).position;
        assertEquals(startTile.position, lastPos, "Last tile in path should be start tile");

        // Verify all tiles in path are PATH type
        for (Tile tile : result) {
            assertEquals(Tile.TileType.PATH, tile.getTileType(), "All tiles in path should be PATH type");
        }
    }

    /**
     * Test Case 3: Complex L-shaped path
     * Tests pathfinding through a path that changes direction
     */
    @Test
    void testCreatePath_LShapedPath() throws Exception {
        // Arrange
        createLShapedPath();
        Tile prevTile = testGrid[0][0];  // Start from corner
        Tile startTile = testGrid[1][0]; // Move to next tile
        Tile endTile = testGrid[2][2];   // End at the corner of L

        // Act
        List<Tile> result = callCreatePath(prevTile, startTile, endTile);

        // Assert
        assertNotNull(result, "Path should be found for valid L-shaped path");
        assertTrue(result.size() >= 3, "L-shaped path should have at least 3 tiles");

        // Verify path contains key tiles
        assertTrue(result.contains(startTile), "Path should contain start tile");
        assertTrue(result.contains(testGrid[2][0]), "Path should contain corner tile");

        // Verify all tiles are PATH type
        for (Tile tile : result) {
            assertEquals(Tile.TileType.PATH, tile.getTileType(), "All tiles in L-shaped path should be PATH type");
        }

        // Verify path connectivity - each tile should connect to the next
        for (int i = 0; i < result.size() - 1; i++) {
            Tile current = result.get(i);
            Tile next = result.get(i + 1);

            // Calculate distance between consecutive tiles
            int deltaX = Math.abs(current.position.x - next.position.x);
            int deltaY = Math.abs(current.position.y - next.position.y);

            // Tiles should be adjacent (distance of 1 in exactly one direction)
            assertTrue((deltaX == 1 && deltaY == 0) || (deltaX == 0 && deltaY == 1),
                    "Consecutive tiles should be adjacent");
        }
    }

}
