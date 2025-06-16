package Utils;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpriteSheetDivider {
    private static final String OUTPUT_DIR = "src/main/resources/Assets/UI/Buttons";

    public static void divideSpriteSheet(String spriteSheetPath, int tileWidth, int tileHeight) {
        try {
            // Create output directory if it doesn't exist
            Path outputPath = Paths.get(OUTPUT_DIR);
            if (!Files.exists(outputPath)) {
                Files.createDirectories(outputPath);
            }

            // Load the sprite sheet
            Image spriteSheet = new Image(SpriteSheetDivider.class.getResourceAsStream(spriteSheetPath));
            int sheetWidth = (int) spriteSheet.getWidth();
            int sheetHeight = (int) spriteSheet.getHeight();

            // Calculate number of tiles
            int tilesX = sheetWidth / tileWidth;
            int tilesY = sheetHeight / tileHeight;

            // Extract each tile
            for (int y = 0; y < tilesY; y++) {
                for (int x = 0; x < tilesX; x++) {
                    WritableImage tile = new WritableImage(
                        spriteSheet.getPixelReader(),
                        x * tileWidth,
                        y * tileHeight,
                        tileWidth,
                        tileHeight
                    );

                    // Convert to BufferedImage and save
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(tile, null);
                    String fileName = String.format("button_%d_%d.png", x, y);
                    File outputFile = new File(OUTPUT_DIR, fileName);
                    ImageIO.write(bufferedImage, "png", outputFile);
                }
            }
            System.out.println("Successfully divided sprite sheet into " + (tilesX * tilesY) + " images");
        } catch (IOException e) {
            System.err.println("Error dividing sprite sheet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Example usage
        divideSpriteSheet("/Assets/UI/kutowerbuttons4.png", 64, 64); // Adjust tile size as needed
    }
} 