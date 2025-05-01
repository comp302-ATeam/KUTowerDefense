package Domain.GameFlow;

public class Tile {
    int row;
    int col;

    // World position (in pixels or JavaFX units)
    double x;
    double y;
    // Whether this tile is already occupied by a Tower
    boolean occupied = false;

    // Size of the tile (width & height in pixels)
    double size;
}
