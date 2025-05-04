package Domain.GameFlow;

import javafx.geometry.Bounds;
import javafx.scene.layout.GridPane;

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

//        for (Tile tile : path) {
//            System.out.println(tile.position);
//        }

    }

    public Tile[][] getTileGrid() {
        return tileGrid;
    }



    public Vector2<Double>[] getPath(GridPane gridPane , int tileSize){

        Bounds screenBounds = gridPane.localToScreen(gridPane.getBoundsInLocal());

        Vector2<Double> factor = new Vector2<>(screenBounds.getMinX() + (tileSize /2) ,screenBounds.getMinY() + (tileSize /2));

        Vector2<Double>[] new_path = new Vector2[this.path.length];



        for (int i = 0; i < this.path.length; i++) {
            Vector2<Integer> relativePos = path[i].position;
            new_path[i] = new Vector2<>(factor.x + relativePos.x * tileSize ,factor.y +  relativePos.y * tileSize);
        }
        return new_path;
    }

    public Vector2<Integer> getSize() {
        return  new Vector2<Integer>(xLength , yLength);
    }
}
