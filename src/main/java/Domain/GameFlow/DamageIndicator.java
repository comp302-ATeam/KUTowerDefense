package Domain.GameFlow;

import javafx.animation.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Random;

public class DamageIndicator {

    private static final Random random = new Random();

    public static void showDamageText(Pane parent, int damage, double x, double y) {
        Text dmgText = new Text("-" + damage);

        dmgText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        dmgText.setFill(Color.RED);


        // Random slight offset in X and Y

        double xOffset = (random.nextDouble() - 0.5) * 20; // Range: -10 to +10
        double yOffset = (random.nextDouble() - 0.5) * 10; // Range: -5 to +5

        dmgText.setX(x + xOffset);
        dmgText.setY(y + yOffset);

        // Optional: add slight shadow for visibility
        dmgText.setEffect(new DropShadow(2, Color.BLACK));

        parent.getChildren().add(dmgText);

        // Move up
        TranslateTransition moveUp = new TranslateTransition(Duration.seconds(1), dmgText);
        moveUp.setByY(-30);

        // Fade out
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), dmgText);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Play together
        ParallelTransition animation = new ParallelTransition(moveUp, fadeOut);
        animation.setOnFinished(e -> parent.getChildren().remove(dmgText));
        animation.play();
    }

    public static void showText(GridPane parent, String text, double x, double y) {
        Text dmgText = new Text("-" + text);

        dmgText.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        dmgText.setFill(Color.WHITE);


        // Random slight offset in X and Y

        double xOffset = (random.nextDouble() - 0.5) * 20; // Range: -10 to +10
        double yOffset = (random.nextDouble() - 0.5) * 10; // Range: -5 to +5

        dmgText.setX(x + xOffset);
        dmgText.setY(y + yOffset);

        // Optional: add slight shadow for visibility
        dmgText.setEffect(new DropShadow(2, Color.BLACK));

        parent.getChildren().add(dmgText);

        // Move up
        TranslateTransition moveUp = new TranslateTransition(Duration.seconds(8), dmgText);
        moveUp.setByY(-30);

        // Fade out
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(8), dmgText);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Play together
        ParallelTransition animation = new ParallelTransition(moveUp, fadeOut);
        animation.setOnFinished(e -> parent.getChildren().remove(dmgText));
        animation.play();
    }
}
