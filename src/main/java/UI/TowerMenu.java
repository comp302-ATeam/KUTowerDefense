package UI;

import Domain.GameObjects.Tower;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import javax.swing.*;

public class TowerMenu extends Pane {
    private Tower tower;

    public TowerMenu(Tower tower){
        this.tower = tower;
        tower.mapPane.getChildren().add(this);
        this.setLayoutX(tower.getX());
        this.setLayoutY(tower.getY());
        this.setPrefSize(80, 100);

        this.setStyle("-fx-background-color: red;");



    }
}
