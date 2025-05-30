package Domain.GameObjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.Random;

public class GoldDrop extends ImageView {
    private final int goldAmount;

    public GoldDrop(double x, double y, int archerCost, Pane parentPane, Runnable onPickup) {
        setFitWidth(20);
        setFitHeight(20);
        setLayoutX(x);
        setLayoutY(y);

        Random rand = new Random();
        this.goldAmount = 2 + rand.nextInt((archerCost / 2) - 1); // min 2, max (archerCost / 2)

        setOnMouseClicked((MouseEvent e) -> {
            onPickup.run(); // callback to add gold
            parentPane.getChildren().remove(this); // remove visual
        });

        // Auto-remove after 10 seconds
        PauseTransition disappearTimer = new PauseTransition(Duration.seconds(10));
        disappearTimer.setOnFinished(e -> Platform.runLater(() -> parentPane.getChildren().remove(this)));
        disappearTimer.play();

        parentPane.getChildren().add(this);
    }

    public int getGoldAmount() {
        return goldAmount;
    }
}
