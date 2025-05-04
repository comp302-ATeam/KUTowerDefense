package Domain.GameFlow;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.io.Serializable;


public class Tile  implements Serializable {

    public enum TileType {EMPTY,PATH,TOWER_LOT,TOWER_MAGE,TOWER_ARTILLERY,TOWER_ARCHER,DECOR,} /// TYPES OF TILES FOR LOADING LATER ON
    public enum Dtype {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0);

        public final int x;
        public final int y;

        Dtype(int x, int y) {
            this.x = x;
            this.y = y;
        }};

    public Vector2<Integer> position;

    private TileType tileType;
    private int tileIndex;

    private int defaultColorHex = 0xF7D680FF;

    private static final long serialVersionUID = 1L;



/// WIP
    public Dtype[][] dirLookUp = {
            {Dtype.RIGHT,Dtype.DOWN},{Dtype.LEFT,Dtype.RIGHT},{Dtype.LEFT,Dtype.DOWN},{Dtype.DOWN},
            {Dtype.UP,Dtype.DOWN},{null},{Dtype.UP,Dtype.DOWN},{Dtype.UP,Dtype.DOWN},
            {Dtype.UP,Dtype.RIGHT},{Dtype.LEFT,Dtype.RIGHT},{Dtype.LEFT,Dtype.UP},{Dtype.UP},
            {Dtype.RIGHT},{Dtype.LEFT,Dtype.RIGHT},{Dtype.LEFT}

    };

    public Dtype[] getPossibleDir() {
        return dirLookUp[tileIndex];
    }

    public Vector2<Integer> getNextPathTile(Dtype dir){
        return new Vector2<>(dir.x + position.x, dir.y + position.y);
    }

    public Vector2<Integer> getPosition() {
        return position;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public TileType getTileType() {
        return tileType;
    }

    public Tile( int index, TileType tileType, Vector2<Integer> position) {

        this.tileIndex = index;
        this.tileType = tileType;


        this.position = position;
//        this.xPos = xPos;
//        this.yPos = yPos;
    }


    public Vector2<Integer> getDirection(Tile tile ) {
        return null;
    }


    public boolean isBoundary(Vector2<Integer> size) {
        return position.x == 0 || position.y == 0 ||
                position.x == (size.x - 1) || position.y == (size.y - 1);
    }

    /// WIP

//    public Color getColor(ImageView imageView , Vector2<Integer> pos) {
//         // your ImageView
//        Image image = imageView.getImage(); // get the actual Image
//
//        PixelReader reader = image.getPixelReader();
//        if (reader != null) {
//            Color color = reader.getColor(pos.x, pos.y);
//
//            if (color.toString() == String.valueOf(defaultColorHex) ) {
//                System.out.println("Allahh");
//            }
//            return color;
//        }
//        return null;
//    }


}
