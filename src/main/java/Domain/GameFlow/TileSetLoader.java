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
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;


public class TileSetLoader {

    protected Image tileset = new Image(getClass().getResource("/Assets/Tiles/Tileset 96x96.png").toExternalForm());
    protected GridPane root ;



    private Tile[][] tileGrid;
    private Tile[] path;

    private Tile startTile;
    private ImageView startTileImage;

    private Tile endTile;
    private ImageView endTileImage;

    protected String directoryPath = "saves";

    private Vector2<Integer> mapSize ;


    static int selectedTile = 0;

    protected int tileHeight = 96 ; /// DEPENDS ON the size of tileset
    protected int tileWidth = 96;
    protected int rowNum = 4;



    private boolean isMenu = false;

    private boolean pathMode = false;
    private boolean entryFlagged = false;

    public void changePathMode() {
        pathMode = !pathMode;
    }

    public void saveGrid(){


        if (startTile == null || endTile == null) return;


        List<Tile> temp_path = new ArrayList<Tile>();

        temp_path =  createPath(startTile,startTile,endTile);

        if(temp_path != null){
//            for (Tile tile : temp_path) {
//                System.out.println(tile.position);
//            }
        }
        else {
            System.out.println("no path found \n construct a proper path");
            return;
        }

         // or use relative path like "saves/"
        File directory = new File(directoryPath);

        // Create directory if it doesn't exist
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File saveFile = new File(directory, "mapSave.ser");

        Tile[] pathArray = temp_path.toArray(new Tile[0]);

        TileMap tileMap = new TileMap(tileGrid, pathArray,mapSize.x,mapSize.y);

//        for (Vector2<Double> pos : tileMap.getPath(root,tileHeight)){
//            System.out.println(pos);
//        }

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
    /// CreatePath

    public List<Tile> createPath(Tile prevTile,Tile startTile, Tile endTile) {
        if (startTile.position.equals(endTile.position)) {
            List<Tile> path = new ArrayList<>();
            path.add(endTile);
            return path;
        }

        boolean previousTileValid = false;
        Tile nextTile = null;

        Tile.Dtype[] possibleDir = startTile.getPossibleDir();
        for (Tile.Dtype dirType : possibleDir){

            if (startTile.position.equals(prevTile.position) )previousTileValid = true;

            Vector2<Integer> nextTilePos = startTile.getNextPathTile(dirType);

            //System.out.println(nextTilePos);

            if ( !isValidTile(nextTilePos)) continue;

            else if (prevTile.position.equals( nextTilePos))previousTileValid = true;

            else nextTile = tileGrid[nextTilePos.x][nextTilePos.y];

        }

        if (previousTileValid && nextTile != null) {

            //System.out.println(nextTile.position);

            List<Tile> path = createPath(startTile, nextTile, endTile);
            if (path == null) return null;
            path.add(startTile);  // appends to the end
            return path;
        }
        return null;
    }


    ///  check for valid tile

    public boolean isValidTile(Vector2<Integer> tilePos){
        if (tilePos == null) return false;
        if(tilePos.x >= mapSize.x || tilePos.y >= mapSize.y || tilePos.x < 0 || tilePos.y < 0){ return false; }
        if(tileGrid[tilePos.x][tilePos.y] == null){ return false; }

        if(tileGrid[tilePos.x][tilePos.y].getTileType() != Tile.TileType.PATH){ return false; }

        return true;
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



    public Tile.TileType getTileType(int tileIndex){
        if (tileIndex < 14 && tileIndex != 5) {
            return Tile.TileType.PATH;
        }
        else if(tileIndex == 15){
            return Tile.TileType.TOWER_LOT;
        }
        else if(tileIndex == 20){
            //ArtilleryTower artilleryTower = new ArtilleryTower(position.x,position.y);
            return Tile.TileType.TOWER_ARTILLERY;
        }
        else if (tileIndex == 21){
            //MageTower mageTower = new MageTower(position.x,position.y);
            return Tile.TileType.TOWER_MAGE;
        }
        else if (tileIndex == 26){
            //ArcherTower archerTower = new ArcherTower(position.x,position.y);
            return Tile.TileType.TOWER_ARCHER;
        }
        else {
            return Tile.TileType.DECOR;
        }
    }


    //MOUSE EVENTS FOR EACH TILE

    private void mOnClick(Tile tile,ImageView imageView){
        //System.out.println("hoppa");
        if (pathMode && (tile.getTileType() == Tile.TileType.PATH) && tile.isBoundary(mapSize)){
            ColorAdjust hoverEffect = new ColorAdjust();
            if (endTile != null) endTileImage.setEffect(null);



            if (!entryFlagged && (endTile == null || tile.position != endTile.position  )){
                if (startTile != null) startTileImage.setEffect(null);
                hoverEffect.setHue(0.2);
                imageView.setEffect(hoverEffect);



                startTile = tile;
                startTileImage = imageView;
                entryFlagged = !entryFlagged;
            }
            else if (tile.position != startTile.position) {


                hoverEffect.setHue(-0.2);
                imageView.setEffect(hoverEffect);



                endTile = tile;
                endTileImage = imageView;
                entryFlagged = !entryFlagged;
            }

            return;
        }

        if (isMenu){
            selectedTile = tile.getTileIndex();
        }
        else {
            addToGrid(5,tile.getPosition());
            addToGrid(selectedTile,tile.getPosition());
        }

        if(startTile != null && endTile != null){
            System.out.println(startTile.position + " " + endTile.position);
            Tile.Dtype[] dtype = startTile.getPossibleDir();
            for (Tile.Dtype dirType : dtype) {
                System.out.println(startTile.getNextPathTile(dirType).x + "___" + startTile.getNextPathTile(dirType).y);
            }
        }

        if(startTile != null && endTile != null){
            System.out.println(startTile.position + " " + endTile.position);
            Tile.Dtype[] dtype = startTile.getPossibleDir();
            for (Tile.Dtype dirType : dtype) {
                System.out.println(startTile.getNextPathTile(dirType).x + "___" + startTile.getNextPathTile(dirType).y);
            }
        }
    }


    public void mOnHover(ImageView tile){
        if(tile == startTileImage || tile == endTileImage) return;

        ColorAdjust hoverEffect = new ColorAdjust();
        //hoverEffect.setHue(0.2); // greenish
        hoverEffect.setBrightness(0.2);
        tile.setEffect(hoverEffect);
    }

    public void mOffHover(ImageView tile){
        if(tile == startTileImage || tile == endTileImage) return;
        tile.setEffect(null);
    }






    // INITALIZE A NEW TILE AND ADD TO GRID PANE
    public void addToGrid(int index , Vector2<Integer> position ){

        ImageView imageView = new ImageView(tileset);



        int col = index % rowNum;
        int row = index / rowNum;
        imageView.setViewport(new Rectangle2D(col * tileWidth, row * tileHeight, tileWidth, tileHeight));

        imageView.setFitWidth(tileWidth);
        imageView.setFitHeight(tileHeight);
        imageView.setPreserveRatio(false);



        Tile tile = new Tile(index ,getTileType(index),position);



        imageView.setOnMouseEntered(e -> mOnHover(imageView));
        imageView.setOnMouseExited(e ->mOffHover(imageView) );
        imageView.setOnMousePressed(e -> mOnClick(tile,imageView));



        root.add(imageView, tile.position.x,tile.position.y);
        tileGrid[position.x][position.y] = tile;
    }
}
