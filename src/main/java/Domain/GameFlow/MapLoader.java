package Domain.GameFlow;

import Domain.GameObjects.ArcherTower;
import Domain.GameObjects.ArtilleryTower;
import Domain.GameObjects.MageTower;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;




public class MapLoader extends TileSetLoader {



    private TileMap tilemap;

    private Vector2<Double>[] path;


    public Vector2<Double>[] getPath() {
        return path;
    }

    public void constructGrid (){

        Tile[][] tileGrid = tilemap.getTileGrid();

        for (int x = 0; x < tilemap.getSize().x; x++) {
            for (int y = 0; y < tilemap.getSize().y; y++) {
                Tile tile = tileGrid[x][y];
                addToGrid(5,tile.position);
                addToGrid(tile.getTileIndex(),tile.position);

            }
        }
    }




    @Override
    public void addToGrid(int index,Vector2<Integer> position){
        ImageView imageView = new ImageView(tileset);
        Tile tile = new Tile(index ,getTileType(index),position);

        int col = index % rowNum;
        int row = index / rowNum;
        imageView.setViewport(new Rectangle2D(col * tileWidth, row * tileHeight, tileWidth, tileHeight));

        imageView.setFitWidth(tileWidth);
        imageView.setFitHeight(tileHeight);
        imageView.setPreserveRatio(false);

        root.add(imageView, tile.position.x,tile.position.y);
    }

    public MapLoader(GridPane gridPane ) {
        super(gridPane);

         // or relative like "saves/"
        File file = new File(directoryPath, "saves/mapSave.ser");

        TileMap loadedMap = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            loadedMap = (TileMap) in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }



        this.tilemap = loadedMap;

        path = tilemap.getPath(gridPane,tileHeight);

        setUpGrid(gridPane,tilemap.getSize().x,tilemap.getSize().y);
        constructGrid();
    }
}
