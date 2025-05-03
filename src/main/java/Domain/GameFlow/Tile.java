package Domain.GameFlow;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;


public class Tile extends ImageView {

    public enum TileType {EMPTY,PATH,TOWER,DECOR,} /// TYPES OF TILES FOR LOADING LATER ON
    public enum Dtype {UP,DOWN,LEFT,RIGHT};

    private TileType tileType;
    private int tileIndex;


    public Vector2<Integer> position;

/// WIP
    public Dtype[][] dirLookUp = {
            {Dtype.RIGHT,Dtype.DOWN},{Dtype.LEFT,Dtype.RIGHT},{Dtype.RIGHT,Dtype.DOWN},{Dtype.DOWN},
            {},{},{},{},
            {},{},{},{},
            {},{},{}

    };

    public Vector2<Integer> getPosition() {
        return position;
    }

    public int getTileIndex() {
        return tileIndex;
    }



    public Tile(Image image, int index, TileType tileType, Vector2<Integer> position) {
        super(image);
        this.tileIndex = index;
        this.tileType = tileType;


        this.position = position;
//        this.xPos = xPos;
//        this.yPos = yPos;
    }

    /// WIP

    public Color getColor(ImageView imageView , Vector2<Integer> pos) {
         // your ImageView
        Image image = imageView.getImage(); // get the actual Image

        PixelReader reader = image.getPixelReader();
        if (reader != null) {
            Color color = reader.getColor(pos.x, pos.y);
            return color;
        }
        return null;
    }

    public Vector2<Integer> getDirection(Tile tile ) {
        return null;
    }
}
