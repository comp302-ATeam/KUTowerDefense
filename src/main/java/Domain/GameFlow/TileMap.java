package Domain.GameFlow;

import java.io.Serializable;

public class TileMap implements Serializable {

    private static final long serialVersionUID = 1L;

    private Tile[][] tileGrid;
    private Tile[] path;
    private int xLength, yLength;




    public TileMap(Tile[][] tileGrid, Tile[] path , int xLength , int yLength) {
        this.xLength = xLength;
        this.yLength = yLength;
        this.tileGrid = tileGrid;
        this.path = path;

    }

    public Tile[][] getTileGrid() {
        return tileGrid;
    }
    public Tile[] getPath() {
        return path;
    }

    public void setPath(Tile[] path) {
        this.path = path;

    }

    public Vector2<Integer> getSize() {
        return  new Vector2<Integer>(xLength , yLength);
    }
}
