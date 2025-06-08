package UI;

import Domain.GameFlow.GameActionController;
import Domain.GameObjects.*;
import Domain.PlayerData.PlayerStats;
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
            tower.sell();
            
            Tower lotTower = new TowerLot( (int) Math.round(tower.getX()),(int) Math.round(tower.getY()),tower.mapPane);
            new TowerMenu(lotTower);
            tower.mapPane.getChildren().remove(this);
        });
    }

    private void lotMode(){
        upButton.setOnAction(e -> {
            PlayerStats playerStats = PlayerStats.getInstance();
            int archerCost = 100; // Default archer tower cost - should match the cost in ArcherTower
            
            if (playerStats.spendGold(archerCost)) {
                tower.Destroy();
                Tower cur_tower = new ArcherTower((int) Math.round(tower.getX()),(int) Math.round(tower.getY()),tower.mapPane);
                new TowerMenu(cur_tower);
                tower.mapPane.getChildren().remove(this);
            } else {
                System.out.println("Not enough gold to purchase Archer Tower!");
            }
        });
        
        leftButton.setOnAction(e -> {
            PlayerStats playerStats = PlayerStats.getInstance();
            int artilleryCost = 200; // Default artillery tower cost - should match the cost in ArtilleryTower
            
            if (playerStats.spendGold(artilleryCost)) {
                tower.Destroy();
                Tower cur_tower = new ArtilleryTower((int) Math.round(tower.getX()),(int) Math.round(tower.getY()),tower.mapPane);
                new TowerMenu(cur_tower);
                tower.mapPane.getChildren().remove(this);
            } else {
                System.out.println("Not enough gold to purchase Artillery Tower!");
            }
        });
        
        downButton.setOnAction(e -> {
            PlayerStats playerStats = PlayerStats.getInstance();
            int mageCost = 150; // Default mage tower cost - should match the cost in MageTower
            
            if (playerStats.spendGold(mageCost)) {
                tower.Destroy();
                Tower cur_tower = new MageTower((int) Math.round(tower.getX()),(int) Math.round(tower.getY()),tower.mapPane);
                new TowerMenu(cur_tower);
                tower.mapPane.getChildren().remove(this);
            } else {
                System.out.println("Not enough gold to purchase Mage Tower!");
            }
        });
    }

    public TowerMenu(Tower tower){
        this.tower = tower;
        tower.mapPane.getChildren().add(this);
        this.setLayoutX(tower.getX());
        this.setLayoutY(tower.getY());
        this.setPrefSize(80, 100);

        //this.setStyle("-fx-background-color: red;");

        this.setOnMouseClicked(e -> clickTower());
        //tower.towerImage.setOnMouseClicked(e -> clickTower());
        this.setOpacity(0.0);

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

        cur_button = new Button();
        this.getChildren().add(cur_button);

        cur_button.setPrefSize(50,50);
        //cur_button.setLayoutX(tower.getX());
        cur_button.layoutYProperty().bind(this.heightProperty().subtract(cur_button.heightProperty()).divide(2));
        cur_button.setLayoutX(-70); // 10px from top

        leftButton = cur_button;

        setButtons(false);
        ///  aasdasdasdasd

        if (tower instanceof TowerLot ){
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
            leftButton.setVisible(true);
        }
        else {
            upButton.setVisible(false);
            downButton.setVisible(false);
            leftButton.setVisible(false);
        }
    }

    private void clickTower(){
        menuOn = !menuOn;
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
