package Domain.GameFlow;

import Domain.GameObjects.ArcherTower;
import Domain.GameObjects.ArtilleryTower;
import Domain.GameObjects.MageTower;
import Domain.GameObjects.Tower;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class TileSetLoader {

    protected Image tileset = new Image(getClass().getResource("/Assets/Tiles/Tileset 96x96.png").toExternalForm());
    protected GridPane root ;
    private Tile[][] tileGrid;
    private Tile[] path;
    protected String directoryPath = "saves";

    private Vector2<Integer> mapSize ;


    static int selectedTile = 0;

    protected int tileHeight = 96 ; /// DEPENDS ON the size of tileset
    protected int tileWidth = 96;
    protected int rowNum = 4;



    private boolean isMenu = false;




    public void saveGrid(){


         // or use relative path like "saves/"
        File directory = new File(directoryPath);

        // Create directory if it doesn't exist
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File saveFile = new File(directory, "mapSave.ser");


        TileMap tileMap = new TileMap(tileGrid,path,mapSize.x,mapSize.y);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveFile))) {
            out.writeObject(tileMap);
            System.out.println("Map saved to: " + saveFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    ///  CONSTRUCTS THE EDITABLE TILEMAP
    public void constructGrid (int xLength, int yLength){

        tileGrid = new Tile[xLength][yLength];

        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                addToGrid(5,new Vector2(i,j));
            }
        }
    }

/// ADDS EVERY POSSIBLE TILESET INTO SELECTABLE TILES
    public void constructMenuGrid (int xLength, int yLength){

        tileGrid = new Tile[xLength][yLength];
        int index = 0;

        for (int i = 0; i < yLength; i++) {
            for (int j = 0; j < xLength; j++) {
                addToGrid(index++,new Vector2(j,i));
            }
        }
    }

    ///  GRID PANE SET UP FOR CORRECT LAYOUT

    public void setUpGrid (GridPane grid , int xLength , int yLength){
        root = grid;
        root.getChildren().clear();
        root.getColumnConstraints().clear();
        root.getRowConstraints().clear();
        root.getColumnConstraints().add(new ColumnConstraints(tileWidth));
        root.getRowConstraints().add(new RowConstraints(tileHeight));

        mapSize = new Vector2<Integer>(xLength,yLength);
    }


    ///  TWO CONSTRUCTORS THIS ONE FOR LOADING EDIT TILE SET

    public TileSetLoader(GridPane pane, int xLength, int yLength ) {

        setUpGrid(pane,xLength,yLength);
        //rowNum = xLength;
        constructGrid(xLength,yLength);

    }

    ///  FOR MENU ON THE RIGHT


    public TileSetLoader(GridPane pane, int xLength, int yLength , boolean isMenu ) {

        setUpGrid(pane,xLength,yLength);
        //rowNum = xLength;
        constructMenuGrid(xLength,yLength);

        this.isMenu = isMenu;

    }

    public TileSetLoader(GridPane pane) {

        return;
    }



    //MOUSE EVENTS FOR EACH TILE

    private void mOnClick(Tile tile){
        System.out.println("hoppa");
        if (isMenu){
            selectedTile = tile.getTileIndex();
        }
        else {
            addToGrid(5,tile.position);
            addToGrid(selectedTile,tile.position);
        }
    }


    public void mOnHover(ImageView tile){
        ColorAdjust hoverEffect = new ColorAdjust();
        //hoverEffect.setHue(0.2); // greenish
        hoverEffect.setBrightness(0.2);
        tile.setEffect(hoverEffect);
    }

    public void mOffHover(ImageView tile){
        tile.setEffect(null);
    }






    // INITALIZE A NEW TILE AND ADD TO GRID PANE
    public void addToGrid(int index , Vector2<Integer> position ){

        ImageView imageView = new ImageView(tileset);
        Tile tile = new Tile(index ,Tile.TileType.PATH,position);

        int col = index % rowNum;
        int row = index / rowNum;
        imageView.setViewport(new Rectangle2D(col * tileWidth, row * tileHeight, tileWidth, tileHeight));

        imageView.setFitWidth(tileWidth);
        imageView.setFitHeight(tileHeight);
        imageView.setPreserveRatio(false);



        imageView.setOnMouseEntered(e -> mOnHover(imageView));
        imageView.setOnMouseExited(e ->mOffHover(imageView) );
        imageView.setOnMousePressed(e -> mOnClick(tile));



        root.add(imageView, tile.position.x,tile.position.y);
        tileGrid[position.x][position.y] = tile;
    }
}
