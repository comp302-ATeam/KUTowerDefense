package UI;

import Domain.GameFlow.GameActionController;
import Domain.GameObjects.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.swing.*;

public class TowerMenu extends Pane {
    private Tower tower;

    private boolean menuOn = false;


    private Button upButton;
    private Button downButton;
    private Button leftButton;

    private void towerMode(){



        downButton.setOnAction(e -> {
            tower.Destroy();
            Tower lotTower = new TowerLot( (int) Math.round(tower.getX()),(int) Math.round(tower.getY()),tower.mapPane);
//            new TowerMenu(lotTower);
            tower.mapPane.getChildren().remove(this);
        });

        upButton.setOnAction(e -> {
            tower.upgrade();
        });


    }

    private void lotMode(){
        upButton.setOnAction(e -> {
            tower.Destroy();
            Tower cur_tower = new ArcherTower((int) Math.round(tower.getX()),(int) Math.round(tower.getY()),tower.mapPane);
//            new TowerMenu(cur_tower);
            tower.mapPane.getChildren().remove(this);
            return;
        });
        leftButton.setOnAction(e -> {
            tower.Destroy();
            Tower cur_tower = new ArtilleryTower((int) Math.round(tower.getX()),(int) Math.round(tower.getY()),tower.mapPane);
//            new TowerMenu(cur_tower);
            tower.mapPane.getChildren().remove(this);
            return;
        });
        downButton.setOnAction(e -> {
            tower.Destroy();
            Tower cur_tower = new MageTower((int) Math.round(tower.getX()),(int) Math.round(tower.getY()),tower.mapPane);
//            new TowerMenu(cur_tower);
            tower.mapPane.getChildren().remove(this);
            return;
        });
    }

    public TowerMenu(Tower tower){
        this.tower = tower;
        tower.mapPane.getChildren().add(this);
        this.setLayoutX(tower.getX());
        this.setLayoutY(tower.getY());
        this.setPrefSize(80, 100);

        // Make the menu pane mouse transparent except for buttons
        this.setMouseTransparent(false);
        setPickOnBounds(true);
        //this.setOnMouseClicked(e -> clickTower());
        //tower.towerImage.setOnMouseClicked(e -> clickTower());
        this.setOpacity(0.0);

        this.setOnMouseEntered(e -> openMenu(true));
        this.setOnMouseExited(e -> openMenu(false));

         ///  ADASDASDASD
        Button cur_button = new Button();
        this.getChildren().add(cur_button);

        cur_button.setPrefSize(50,50);
        //cur_button.setLayoutX(tower.getX());
        cur_button.layoutXProperty().bind(this.widthProperty().subtract(cur_button.widthProperty()).divide(2));
        cur_button.setLayoutY(-50); // 10px from top

        cur_button.setOnAction(e -> allah());

        upButton = cur_button;

        cur_button = new Button();
        this.getChildren().add(cur_button);

        cur_button.setPrefSize(50,50);
        //cur_button.setLayoutX(tower.getX());
        cur_button.layoutXProperty().bind(this.widthProperty().subtract(cur_button.widthProperty()).divide(2));
        cur_button.setLayoutY(100); // 10px from top

        downButton = cur_button;



        setButtons(false);
        ///  aasdasdasdasd

        if (tower instanceof TowerLot ){
            cur_button = new Button();
            this.getChildren().add(cur_button);

            cur_button.setPrefSize(50,50);
            //cur_button.setLayoutX(tower.getX());
            cur_button.layoutYProperty().bind(this.heightProperty().subtract(cur_button.heightProperty()).divide(2));
            cur_button.setLayoutX(-70); // 10px from top

            leftButton = cur_button;

            lotMode();
        }
        else {
            towerMode();
        }



    }

    private void setButtons(boolean state){
        if (state){
            upButton.setVisible(true);
            downButton.setVisible(true);
            if (leftButton != null)leftButton.setVisible(true);

        }
        else {
            upButton.setVisible(false);
            downButton.setVisible(false);
            if (leftButton != null)leftButton.setVisible(false);
        }
    }


    private void openMenu(boolean state){
        if (state){
            setOpacity(1.0);
            setButtons(true);
        }
        else {
            setOpacity(0.0);
            setButtons(false);
        }
    }

    private void clickTower(){
        menuOn = !menuOn;
        System.out.println("menuOn: " + menuOn);
        if (menuOn){
            setOpacity(1.0);
            setButtons(true);
        }
        else{
            setOpacity(0.0);
            setButtons(false);
        }


    }

    private void allah(){
        System.out.println("hmm");
    }



}
