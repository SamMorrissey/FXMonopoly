/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.controller;

import fxmonopoly.game.GameModel;
import fxmonopoly.gamedata.board.locations.ChanceLocation;
import fxmonopoly.gamedata.board.locations.CommunityChestLocation;
import fxmonopoly.gamedata.board.locations.Location;
import fxmonopoly.gamedata.board.locations.PropertyLocation;
import fxmonopoly.gamedata.board.locations.RailwayLocation;
import fxmonopoly.gamedata.board.locations.UtilityLocation;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Provides the content necessary for all dynamic dialogs.
 * <p>
 * Exclusively contains static methods since this utility has no need to represent
 * any state, it merely manipulates existing states.
 * @author Sam P. Morrissey.
 */
public class DialogContent {
    
    /**
     * Ensures that the class cannot be instantiated, since it has no reason to 
     * be, as it has no state and only static methods.
     */
    private DialogContent() {}
    
    /**
     * Creates a Dice roll pane to do the initial order.
     * @param diceRoll The alert to set the content of.
     * @param model The model to utilise.
     */
    public static void diceRollPane(Dialog diceRoll, GameModel model) {
        HBox dice = new HBox(20);
        
        ImageView die1 = new ImageView(new Image("fxmonopoly/resources/images/die/Blank.png"));
        ImageView die2 = new ImageView(new Image("fxmonopoly/resources/images/die/Blank.png"));
        Button rollAction = new Button("Roll");
        rollAction.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        
        dice.getChildren().addAll(die1, die2, rollAction);
        dice.setAlignment(Pos.CENTER);
        
        diceRoll.getDialogPane().setContent(dice);   
        diceRoll.getDialogPane().getButtonTypes().add(ButtonType.OK);
        diceRoll.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        
        int[] i = model.rollDie();
        rollAction.addEventHandler(ActionEvent.ACTION, e -> {
            HBox box = (HBox) diceRoll.getDialogPane().getContent();
            ((ImageView) box.getChildren().get(0)).setImage(new Image("fxmonopoly/resources/images/die/" + i[0] + ".png"));
            ((ImageView) box.getChildren().get(1)).setImage(new Image("fxmonopoly/resources/images/die/" + i[1] + ".png"));
            model.reorderList(i[0] + i[1]);
            
            diceRoll.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
            rollAction.disableProperty().setValue(true);
        });
        
        diceRoll.showAndWait();
    }
    
    /**
     * Creates the Dice roll pane that moves the active player.
     * @param diceRoll The alert to set the content of.
     * @param model The model to utilise.
     */
    public static void diceRollAndMovePane(Dialog diceRoll, Dialog newPositionAlert, GameModel model, ArrayList<BoardButton> board) {
        HBox dice = new HBox(20);
        
        ImageView die1 = new ImageView(new Image("fxmonopoly/resources/images/die/Blank.png"));
        ImageView die2 = new ImageView(new Image("fxmonopoly/resources/images/die/Blank.png"));
        Button rollAction = new Button("Roll");
        rollAction.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        
        dice.getChildren().addAll(die1, die2, rollAction);
        dice.setAlignment(Pos.CENTER);
        
        diceRoll.getDialogPane().setContent(dice);
        diceRoll.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        diceRoll.getDialogPane().lookupButton(ButtonType.CLOSE).setDisable(true);
        
        int[] i = model.rollDie();
        
        rollAction.addEventHandler(ActionEvent.ACTION, e -> {
            HBox box = (HBox) diceRoll.getDialogPane().getContent();
            ((ImageView) box.getChildren().get(0)).setImage(new Image("fxmonopoly/resources/images/die/" + i[0] + ".png"));
            ((ImageView) box.getChildren().get(1)).setImage(new Image("fxmonopoly/resources/images/die/" + i[1] + ".png"));
            
            diceRoll.getDialogPane().lookupButton(ButtonType.CLOSE).setDisable(false);
            rollAction.disableProperty().setValue(true);
        });
        
        diceRoll.getDialogPane().lookupButton(ButtonType.CLOSE).addEventFilter(ActionEvent.ACTION, event -> {
            model.diceMove(i);
        });
        
        diceRoll.showAndWait();
    }
    
    public static void getNewPositionDialog(Dialog position, GameModel model, ArrayList<BoardButton> board) {
        Location location = model.retrieveLocation(model.getActivePlayer().getPosition());
        
        if(location instanceof PropertyLocation) {
            if(!((PropertyLocation) location).getIsOwned()) {
                unownedOwnableLocation(position, model, board);
            }
        }
        else if(location instanceof RailwayLocation) {
            if(! ((RailwayLocation) location).getIsOwned()) {
                unownedOwnableLocation(position, model, board);
            }
        }
        else if(location instanceof UtilityLocation) {
            if(! ((UtilityLocation) location).getIsOwned()) {
                unownedOwnableLocation(position, model, board);
            }
        }
        else if(location instanceof ChanceLocation || location instanceof CommunityChestLocation) {
            cardLocation(position, model, board);
        }
    }
    
    private static void unownedOwnableLocation(Dialog position, GameModel model, ArrayList<BoardButton> board) {
        ButtonType buy = new ButtonType("Buy", ButtonData.OTHER); 
        position.getDialogPane().getButtonTypes().add(buy);
        position.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        position.getDialogPane().setMaxWidth(150);
        position.setContentText(model.retrieveLocation(model.getActivePlayer().getPosition()).getName() + " is available to purchase");
        
        double x = board.get(0).getParent().getScene().getX() + board.get(0).getParent().getScene().getWidth() / 2 - position.getWidth() / 2;
        double y = board.get(0).getParent().getScene().getY() + board.get(0).getParent().getScene().getHeight() / 2 - position.getHeight() / 2;
        
        position.setX(x);
        position.setY(y);
        
        position.getDialogPane().getScene().getWindow().sizeToScene();
        
        ImageView graphic = new ImageView(getBoardLocationImage(board, model.getActivePlayer().getPosition()));
        graphic.setRotate(calculateRotation(model.getActivePlayer().getPosition()));
        Label text = new Label(model.retrieveLocation(model.getActivePlayer().getPosition()).getName()+ " is available to purchase");
        text.wrapTextProperty().setValue(Boolean.TRUE);
        HBox box = new HBox(20);
        box.getChildren().addAll(graphic, text);
        box.setAlignment(Pos.CENTER);
        
        position.getDialogPane().setContent(box);
        
        position.getDialogPane().lookupButton(buy).addEventFilter(ActionEvent.ACTION, event -> {
            model.activePlayerBuyLocation();
        });
        
        position.getDialogPane().lookupButton(ButtonType.CANCEL).addEventFilter(ActionEvent.ACTION, event -> {
            
        });
        
        
        position.getDialogPane().getScene().getWindow().sizeToScene();
        position.showAndWait();
    }
    
    private static void cardLocation(Dialog position, GameModel model, ArrayList<BoardButton> board) {
        position.getDialogPane().getButtonTypes().add(ButtonType.OK);
        
        Label text = new Label(model.getActiveCard().getDescription());
        ImageView graphic = new ImageView(getBoardLocationImage(board, model.getActivePlayer().getPosition()));
        graphic.setRotate(calculateRotation(model.getActivePlayer().getPosition()));
        HBox box = new HBox(20);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(graphic, text);
        
        position.getDialogPane().setContent(box);
        
        position.getDialogPane().getScene().getWindow().sizeToScene();
        
        
        double x = board.get(0).getParent().getScene().getX() + board.get(0).getParent().getScene().getWidth() / 2 - position.getWidth() / 2;
        double y = board.get(0).getParent().getScene().getY() + board.get(0).getParent().getScene().getHeight() / 2 - position.getHeight() / 2;
        
        position.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            model.processCardActions();
        });
        
        position.setX(x);
        position.setY(y);
        position.getDialogPane().getScene().getWindow().sizeToScene();
        position.showAndWait();
        
    }
    
    private static Image getBoardLocationImage(ArrayList<BoardButton> board, int position) {
        int minX = (int) board.get(position).getBoundsInParent().getMinX() + 1;
        System.out.println(minX);
        int minY = (int) board.get(position).getBoundsInParent().getMinY() + 1;
        System.out.println(minY);
        
        int width = (int) board.get(position).getBoundsInParent().getWidth() - 2;
        System.out.println(width);
        int height = (int) board.get(position).getBoundsInParent().getHeight() - 2;
        System.out.println(height);
         
        Image image = new Image("fxmonopoly/resources/images/Board.png");
        PixelReader reader = image.getPixelReader();
        //PixelWriter writer = new PixelWriter();
        WritableImage newImage = new WritableImage(reader, minX, minY, width, height);
        //newImage.;
        
        return newImage;
    }
    
    private static int calculateRotation(int position) {
        if(position >= 0 && position < 10) {
            return 0;
        }
        else if(position > 10 && position < 20) {
            return 270;
        }
        else if(position >= 20 && position <= 30) {
            return 180;
        }
        else {
            return 90;
        }
    }
    
    public static void bidDialog(Dialog Bid, GameModel model, ArrayList<BoardButton> board) {
        VBox box = new VBox(10);
        
        Label enterBid = new Label("Enter Max Bid:");
        TextField numeric = new TextField();
        numeric.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numeric.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        if(model.getActiveBid().containsLocation()) { 
            ImageView graphic = new ImageView(getBoardLocationImage(board, model.retrieveLocationPosition(model.getActiveBid().getLocation())));
            graphic.setRotate(calculateRotation(model.getActivePlayer().getPosition()));
        }
        else if(model.getActiveBid().containsGOJFCard()) {
            ImageView graphic = new ImageView(new Image("fxmonopoly/resources/images/LeaveJailIcon"));
            graphic.setStyle("-fx-background-color: black;");
        }
               
        
    }
}
