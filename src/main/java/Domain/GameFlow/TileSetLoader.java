package Domain.GameFlow;

import javafx.geometry.Rectangle2D;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class TileSetLoader {

    private Image tileset = new Image(getClass().getResource("/Assets/Tiles/Tileset 96x96.png").toExternalForm());
    private GridPane root ;


    static int selectedTile = 0;

    private int tileHeight = 96 ; /// DEPENDS ON the size of tileset
    private int tileWidth = 96;
    private int rowNum = 4;

    private Tile[][] tileGrid;

    private boolean isMenu = false;

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

    public void setUpGrid (GridPane grid){
        root = grid;
        root.getChildren().clear();
        root.getColumnConstraints().clear();
        root.getRowConstraints().clear();
        root.getColumnConstraints().add(new ColumnConstraints(tileWidth));
        root.getRowConstraints().add(new RowConstraints(tileHeight));
    }


    ///  TWO CONSTRUCTORS THIS ONE FOR LOADING EDIT TILE SET

    public TileSetLoader(GridPane pane, int xLength, int yLength ) {

        setUpGrid(pane);
        //rowNum = xLength;
        constructGrid(xLength,yLength);

    }

    ///  FOR MENU ON THE RIGHT


    public TileSetLoader(GridPane pane, int xLength, int yLength , boolean isMenu ) {

        setUpGrid(pane);
        //rowNum = xLength;
        constructMenuGrid(xLength,yLength);

        this.isMenu = isMenu;

    }



    //MOUSE EVENTS FOR EACH TILE

    private void mOnClick(Tile tile){
        System.out.println("hoppa");
        if (isMenu){
            selectedTile = tile.getTileIndex();
        }
        else {
            addToGrid(5,tile.getPosition());
            addToGrid(selectedTile,tile.getPosition());
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

        Tile tile = new Tile(tileset,index ,Tile.TileType.PATH,position);

        int col = index % rowNum;
        int row = index / rowNum;
        tile.setViewport(new Rectangle2D(col * tileWidth, row * tileHeight, tileWidth, tileHeight));

        tile.setFitWidth(tileWidth);
        tile.setFitHeight(tileHeight);
        tile.setPreserveRatio(false);



        tile.setOnMouseEntered(e -> mOnHover(tile));
        tile.setOnMouseExited(e ->mOffHover(tile) );
        tile.setOnMousePressed(e -> mOnClick(tile));



        root.add(tile, tile.position.x,tile.position.y);
        tileGrid[position.x][position.y] = tile;
    }
}
