package UI;

import Domain.GameFlow.GameActionController;
import Domain.GameObjects.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.Scene;

import javax.swing.*;

public class TowerMenu extends Pane {
    private Tower tower;

    private boolean menuOn = false;


    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private boolean setTower = false;
    
    // Static reference to game controller
    private static UI.GameSceneController gameController;
    
    public static void setGameController(UI.GameSceneController controller) {
        gameController = controller;
    }
    
    /**
     * Attempts to purchase a tower if the player has enough gold
     */
    private boolean tryPurchaseTower(Class<? extends Tower> towerType) {
        if (gameController == null) return false;
        
        int currentGold = Integer.parseInt(gameController.getLabelGold().getText());
        int cost = getTowerCost(towerType);
        
        if (currentGold >= cost) {
            gameController.updateGold(currentGold - cost);
            return true;
        } else {
            System.out.println("Not enough gold to purchase tower! Need: " + cost + " gold, have: " + currentGold);
            return false;
        }
    }
    
    /**
     * Sells the tower and gives refund to player
     */
    private void sellTower() {
        if (gameController != null && !(tower instanceof TowerLot)) {
            int refund = tower.calculateRefundAmount();
            int currentGold = Integer.parseInt(gameController.getLabelGold().getText());
            gameController.updateGold(currentGold + refund);
            System.out.println("Tower sold for " + refund + " gold");
        }
    }
    
    /**
     * Gets the cost of a tower type
     */
    private int getTowerCost(Class<? extends Tower> towerType) {
        Domain.GameFlow.GameSettings settings = Domain.GameFlow.GameSettingsManager.getInstance().getSettings();
        if (towerType == ArcherTower.class) return settings.tower.archerCost;
        if (towerType == ArtilleryTower.class) return settings.tower.artilleryCost;
        if (towerType == MageTower.class) return settings.tower.mageCost;
        return 0;
    }

    private void towerMode(){
        if(tower instanceof MageTower){
            Image downImage = new Image(getClass().getResourceAsStream("/Assets/UI/Buttons/button_3_0.png"));
            ImageView downImageView = new ImageView(downImage);
            downImageView.setFitWidth(50);
            downImageView.setFitHeight(50);
            downButton.setGraphic(downImageView);
            downButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

            Image upImage = new Image(getClass().getResourceAsStream("/Assets/UI/Buttons/button_1_2.png"));
            ImageView upImageView = new ImageView(upImage);
            upImageView.setFitWidth(50);
            upImageView.setFitHeight(50);
            upButton.setGraphic(upImageView);
            upButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");


            downButton.setOnAction(e -> {
                sellTower();  // Give refund to player
                tower.Destroy();
                Tower lotTower = new TowerLot( (int) Math.round(tower.getX()),(int) Math.round(tower.getY()),tower.mapPane);
//            new TowerMenu(lotTower);
                tower.mapPane.getChildren().remove(this);
            });

            upButton.setOnAction(e -> {
                if (gameController != null) {
                    if (!tower.tryUpgrade(gameController)) {
                        System.out.println("Not enough gold to upgrade tower! Need: " + tower.calculateUpgradeCost() + " gold");
                    }
                } else {
                    // Fallback to old behavior if controller not found
                    tower.upgrade();
                }
            });
        }
        if(tower instanceof ArcherTower){
            Image downImage = new Image(getClass().getResourceAsStream("/Assets/UI/Buttons/button_3_0.png"));
            ImageView downImageView = new ImageView(downImage);
            downImageView.setFitWidth(50);
            downImageView.setFitHeight(50);
            downButton.setGraphic(downImageView);
            downButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

            Image upImage = new Image(getClass().getResourceAsStream("/Assets/UI/Buttons/button_1_2.png"));
            ImageView upImageView = new ImageView(upImage);
            upImageView.setFitWidth(50);
            upImageView.setFitHeight(50);
            upButton.setGraphic(upImageView);
            upButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");


            downButton.setOnAction(e -> {
                sellTower();  // Give refund to player
                tower.Destroy();
                Tower lotTower = new TowerLot( (int) Math.round(tower.getX()),(int) Math.round(tower.getY()),tower.mapPane);
//            new TowerMenu(lotTower);
                tower.mapPane.getChildren().remove(this);
            });

            upButton.setOnAction(e -> {
                if (gameController != null) {
                    if (!tower.tryUpgrade(gameController)) {
                        System.out.println("Not enough gold to upgrade tower! Need: " + tower.calculateUpgradeCost() + " gold");
                    }
                } else {
                    // Fallback to old behavior if controller not found
                    tower.upgrade();
                }
            });
        }
        if(tower instanceof ArtilleryTower){
            Image downImage = new Image(getClass().getResourceAsStream("/Assets/UI/Buttons/button_3_0.png"));
            ImageView downImageView = new ImageView(downImage);
            downImageView.setFitWidth(50);
            downImageView.setFitHeight(50);
            downButton.setGraphic(downImageView);
            downButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

            Image upImage = new Image(getClass().getResourceAsStream("/Assets/UI/Buttons/button_1_2.png"));
            ImageView upImageView = new ImageView(upImage);
            upImageView.setFitWidth(50);
            upImageView.setFitHeight(50);
            upButton.setGraphic(upImageView);
            upButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");


            downButton.setOnAction(e -> {
                sellTower();  // Give refund to player
                tower.Destroy();
                Tower lotTower = new TowerLot( (int) Math.round(tower.getX()),(int) Math.round(tower.getY()),tower.mapPane);
//            new TowerMenu(lotTower);
                tower.mapPane.getChildren().remove(this);
            });

            upButton.setOnAction(e -> {
                if (gameController != null) {
                    if (!tower.tryUpgrade(gameController)) {
                        System.out.println("Not enough gold to upgrade tower! Need: " + tower.calculateUpgradeCost() + " gold");
                    }
                } else {
                    // Fallback to old behavior if controller not found
                    tower.upgrade();
                }
            });
        }



    }

    private void lotMode(){
        upButton.setOnAction(e -> {
            if (tryPurchaseTower(ArcherTower.class)) {
                tower.Destroy();
                setTower = true;
                Tower cur_tower = new ArcherTower((int) Math.round(tower.getX()),(int) Math.round(tower.getY()),tower.mapPane);
//            new TowerMenu(cur_tower);
                tower.mapPane.getChildren().remove(this);
            }
            return;
        });
        leftButton.setOnAction(e -> {
            if (tryPurchaseTower(ArtilleryTower.class)) {
                tower.Destroy();
                setTower = true;
                Tower cur_tower = new ArtilleryTower((int) Math.round(tower.getX()),(int) Math.round(tower.getY()),tower.mapPane);
//            new TowerMenu(cur_tower);
                tower.mapPane.getChildren().remove(this);
            }
            return;
        });
        downButton.setOnAction(e -> {
            if (tryPurchaseTower(MageTower.class)) {
                tower.Destroy();
                setTower = true;
                Tower cur_tower = new MageTower((int) Math.round(tower.getX()),(int) Math.round(tower.getY()),tower.mapPane);
//            new TowerMenu(cur_tower);
                tower.mapPane.getChildren().remove(this);
            }
            return;
        });
    }

    public TowerMenu(Tower tower){
        this.tower = tower;
        tower.mapPane.getChildren().add(this);
        this.toFront();
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
        // Create up button
        Button cur_button = new Button();
        this.getChildren().add(cur_button);
        cur_button.setPrefSize(50,50);
        cur_button.layoutXProperty().bind(this.widthProperty().subtract(cur_button.widthProperty()).divide(2));
        cur_button.setLayoutY(-50);
        
        // Set up button image
        Image upImage = new Image(getClass().getResourceAsStream("/Assets/UI/Buttons/button_0_2.png"));
        ImageView upImageView = new ImageView(upImage);
        upImageView.setFitWidth(50);
        upImageView.setFitHeight(50);
        cur_button.setGraphic(upImageView);
        cur_button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        
        upButton = cur_button;

        // Create down button
        cur_button = new Button();
        this.getChildren().add(cur_button);
        cur_button.setPrefSize(50,50);
        cur_button.layoutXProperty().bind(this.widthProperty().subtract(cur_button.widthProperty()).divide(2));
        cur_button.setLayoutY(100);
        
        // Set down button image
        Image downImage = new Image(getClass().getResourceAsStream("/Assets/UI/Buttons/button_2_2.png"));
        ImageView downImageView = new ImageView(downImage);
        downImageView.setFitWidth(50);
        downImageView.setFitHeight(50);
        cur_button.setGraphic(downImageView);
        cur_button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        
        downButton = cur_button;

        setButtons(false);
        ///  aasdasdasdasd

        if (tower instanceof TowerLot ){
            // Create left button
            cur_button = new Button();
            this.getChildren().add(cur_button);
            cur_button.setPrefSize(50,50);
            cur_button.layoutYProperty().bind(this.heightProperty().subtract(cur_button.heightProperty()).divide(2));
            cur_button.setLayoutX(-70);
            
            // Set left button image
            Image leftImage = new Image(getClass().getResourceAsStream("/Assets/UI/Buttons/button_3_2.png"));
            ImageView leftImageView = new ImageView(leftImage);
            leftImageView.setFitWidth(50);
            leftImageView.setFitHeight(50);
            cur_button.setGraphic(leftImageView);
            cur_button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            
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
            tower.showRange();
        }
        else {
            setOpacity(0.0);
            setButtons(false);
            tower.hideRange();
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

    private Button createButton(String imagePath) {
        Button button = new Button();
        button.setPrefSize(50, 50);
        
        // Create and set the button image
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        button.setGraphic(imageView);
        
        // Remove button background and border
        button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        
        return button;
    }

}
