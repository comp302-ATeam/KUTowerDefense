package Domain.GameFlow;

import Domain.GameObjects.ArcherTower;
import Domain.GameObjects.ArtilleryTower;
import Domain.GameObjects.MageTower;
import Domain.GameObjects.Tower;
import UI.TowerMenu;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;




public class MapLoader extends TileSetLoader {



    private TileMap tilemap;

    private Vector2<Double>[] path;

    private Pane gamePane;

    public Vector2<Double>[] getPath() {
        return path;
    }

    public void constructGrid (){

        Tile[][] tileGrid = tilemap.getTileGrid();

        for (int x = 0; x < tilemap.getSize().x; x++) {
            for (int y = 0; y < tilemap.getSize().y; y++) {
                Tile tile = tileGrid[x][y];
                addToGrid(5,tile.position);
                if (tile.getTileType() == Tile.TileType.PATH || tile.getTileType() == Tile.TileType.DECOR){
                    addToGrid(tile.getTileIndex(),tile.position);
                }


                setTilePos(tile);
                addTower(tile);
            }
        }
    }

    public void setTilePos(Tile tile){
        Bounds screenBounds = root.localToScreen(root.getBoundsInLocal());

//         factor = new Vector2<>( (tileWidth /2) * 1.0 , (tileHeight /2) * 1.0);
        Vector2<Double>factor = new Vector2<Double>(-tileWidth * .25,-tileHeight * 1.5);

        Vector2<Integer> relativePos = tile.position;
        tile.realPosition = new Vector2<Double>(factor.x + relativePos.x * tileWidth ,factor.y +  relativePos.y * tileHeight);

    }

    public void addTower(Tile tile) {
        int x = (int)(tile.realPosition.x.doubleValue());
        int y = (int)(tile.realPosition.y.doubleValue());
        
        Tower cur_tower = null;
        
        switch (tile.getTileType()) {
            case TOWER_ARCHER:
                cur_tower = new ArcherTower(x, y, gamePane);
                break;
            case TOWER_MAGE:
                cur_tower = new MageTower(x, y, gamePane);
                break;
            case TOWER_ARTILLERY:
                cur_tower = new ArtilleryTower(x, y, gamePane);
                break;
            case TOWER_LOT:
                cur_tower = new MageTower(x, y, gamePane);
                break;
            default:
                return;

        }
        new TowerMenu(cur_tower);
        
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

    public MapLoader(GridPane gridPane , Pane gamePane) {
        super(gridPane);

         // or relative like "saves/"
        File file = new File(directoryPath, "mapSave.ser");

        TileMap loadedMap = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            loadedMap = (TileMap) in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.gamePane = gamePane;

        this.tilemap = loadedMap;

        path = tilemap.getPath(gridPane,tileHeight);

        setUpGrid(gridPane,tilemap.getSize().x,tilemap.getSize().y);
        constructGrid();
    }
}
