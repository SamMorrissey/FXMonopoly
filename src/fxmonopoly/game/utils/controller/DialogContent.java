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
import fxmonopoly.gamedata.players.Player;
import fxmonopoly.utils.GameDialogs;
import fxmonopoly.utils.StageManager;
import fxmonopoly.utils.View;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
    
    /**
     * Provides the methods for retrieving the necessary dialog.
     * @param manager The manager to utilise.
     * @param model The model to pass.
     * @param board The BoardButton list to pass.
     */
    public static void getNewPositionDialog(StageManager manager, GameModel model, ArrayList<BoardButton> board) {
        Location location = model.retrieveLocation(model.getActivePlayer().getPosition());
        
        if(location instanceof PropertyLocation) {
            if(!((PropertyLocation) location).getIsOwned()) {
                unownedOwnableLocation(manager, model, board);
            }
        }
        else if(location instanceof RailwayLocation) {
            if(! ((RailwayLocation) location).getIsOwned()) {
                unownedOwnableLocation(manager, model, board);
            }
        }
        else if(location instanceof UtilityLocation) {
            if(! ((UtilityLocation) location).getIsOwned()) {
                unownedOwnableLocation(manager, model, board);
            }
        }
        else if(location instanceof ChanceLocation || location instanceof CommunityChestLocation) {
            cardLocation(manager.getGameDialog(GameDialogs.BLANK), model, board);
        }
    }
    
    /**
     * Displays the Dialog for when an ownable location is reached but has no current
     * owner.
     * @param position The Dialog to set the content of.
     * @param model The model to operate on.
     * @param board The board position to grab the image from.
     */
    private static void unownedOwnableLocation(StageManager manager, GameModel model, ArrayList<BoardButton> board) {
        Dialog position = manager.getGameDialog(GameDialogs.BLANK);
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
        
        ImageView graphic = getBoardLocationImage(board, model.getActivePlayer().getPosition());
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
    
    /**
     * Displays the Dialog utilised when a Card location is landed on.
     * @param position The Dialog to set the content of.
     * @param model The model to operate on.
     * @param board The board position to grab the image from.
     */
    private static void cardLocation(Dialog position, GameModel model, ArrayList<BoardButton> board) {
        position.getDialogPane().getButtonTypes().add(ButtonType.OK);
        
        Label text = new Label(model.getActiveCard().getDescription());
        ImageView graphic = getBoardLocationImage(board, model.getActivePlayer().getPosition());
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
    
    /**
     * Retrieves the Image from the specified board position.
     * @param board The board to retrieve bounds from.
     * @param position The board position to grab the image of.
     * @return The image created.
     */
    private static ImageView getBoardLocationImage(ArrayList<BoardButton> board, int position) {
        int minX = (int) board.get(position).getBoundsInParent().getMinX() + 1;
        int minY = (int) board.get(position).getBoundsInParent().getMinY() + 1;
        
        int width = (int) board.get(position).getBoundsInParent().getWidth() - 2;
        int height = (int) board.get(position).getBoundsInParent().getHeight() - 2;
         
        Image image = new Image("fxmonopoly/resources/images/Board.png");
        PixelReader reader = image.getPixelReader();
        WritableImage newImage = new WritableImage(reader, minX, minY, width, height);
        
        ImageView crop = new ImageView(newImage);
        crop.setPreserveRatio(true);
        crop.setRotate(calculateRotation(position));
        
        if((position > 10 && position < 20) || (position > 30 && position <= 39)) {
            crop.setFitHeight(width);
            crop.setFitWidth(height + (width - height));
        }

        return crop;
    }
    
    /**
     * Calculates the board image rotation for utilisation in Dialogs.
     * @param position The position to calculate the rotation from.
     * @return The rotation to apply.
     */
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
    
    /**
     * Retrieves the initial bid Dialog window.
     * @param bid The dialog to fill the content of.
     * @param model The model to operate on.
     * @param board The board to grab images from.
     */
    public static void bidDialog(Dialog bid, GameModel model, ArrayList<BoardButton> board) {
        VBox box = new VBox(10);
        
        Label enterBid = new Label("Enter Max Bid:");
        TextField numeric = new TextField();
        numeric.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numeric.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        ImageView graphic = new ImageView();
        
        if(model.getActiveBid().containsLocation()) { 
            graphic = getBoardLocationImage(board, model.retrieveLocationPosition(model.getActiveBid().getLocation()));
        }
        else if(model.getActiveBid().containsGOJFCard()) {
            graphic.setImage(new Image("fxmonopoly/resources/images/LeaveJailIcon"));
        }
        
        box.getChildren().addAll(graphic, enterBid, numeric);
        
        bid.getDialogPane().setContent(box);
        bid.getDialogPane().getScene().getWindow().sizeToScene();
        
        ButtonType bidOK = new ButtonType("Bid", ButtonData.OK_DONE);
        bid.getDialogPane().getButtonTypes().add(bidOK);
        bid.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        
        bid.getDialogPane().getScene().getWindow().sizeToScene();
        
        bid.getDialogPane().lookupButton(bidOK).addEventFilter(ActionEvent.ACTION, event -> {
            if(numeric.getText().isEmpty() || numeric.getText() == null) {
                
            }
            else {
                model.getActiveBid().addBid(model.getUser(), Integer.parseInt(numeric.getText()));
                
            }
        });
        
        bid.getDialogPane().lookupButton(ButtonType.CANCEL).addEventFilter(ActionEvent.ACTION, event -> {
            
        });
        
        double x = board.get(0).getParent().getScene().getX() + board.get(0).getParent().getScene().getWidth() / 2 - bid.getWidth() / 2;
        double y = board.get(0).getParent().getScene().getY() + board.get(0).getParent().getScene().getHeight() / 2 - bid.getHeight() / 2;
        
        bid.setX(x);
        bid.setY(y);
        bid.getDialogPane().getScene().getWindow().sizeToScene();
        bid.showAndWait();
    }
    
    /**
     * Retrieves the second bid Dialog which appears only if the conditions are 
     * correct from the initial bid window.
     * @param bid The dialog to fill the content of.
     * @param model The model to operate on.
     * @param board The board to grab images from.
     */
    public static void secondaryBidDialog(Dialog bid, GameModel model, ArrayList<BoardButton> board) {
        VBox box = new VBox(10);
        
        Label enterBid = new Label("Max Bid Currently: " + (model.getActiveBid().getSecondHighestBid() + 1) + "\n" +
                                   "Enter Max Bid:");
        TextField numeric = new TextField();
        numeric.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numeric.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        ImageView graphic = new ImageView();
        
        if(model.getActiveBid().containsLocation()) { 
            graphic = getBoardLocationImage(board, model.retrieveLocationPosition(model.getActiveBid().getLocation()));
            //graphic.setRotate(calculateRotation(model.getActivePlayer().getPosition()));
        }
        else if(model.getActiveBid().containsGOJFCard()) {
            graphic.setImage(new Image("fxmonopoly/resources/images/LeaveJailIcon"));
        }
        
        box.getChildren().addAll(graphic, enterBid, numeric);      
        bid.getDialogPane().getScene().getWindow().sizeToScene();
        
        ButtonType bidOK = new ButtonType("Bid", ButtonData.OK_DONE);
        bid.getDialogPane().getButtonTypes().add(bidOK);
        bid.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        
        bid.getDialogPane().getScene().getWindow().sizeToScene();
        
        bid.getDialogPane().lookupButton(bidOK).addEventFilter(ActionEvent.ACTION, event -> {
            if(numeric.getText().isEmpty() || numeric.getText() == null) {
                model.resolveActiveBid();
            }
            else {
                if(Integer.parseInt(numeric.getText()) < model.getActiveBid().getHighestBid()) {
                    model.resolveActiveBid();
                }
                else {
                    model.getActiveBid().addBid(model.getUser(), Integer.parseInt(numeric.getText()));
                    model.resolveActiveBid();
                } 
            }
        });
        
        bid.getDialogPane().lookupButton(ButtonType.CANCEL).addEventFilter(ActionEvent.ACTION, event -> {
            model.resolveActiveBid();
        });
        
        double x = board.get(0).getParent().getScene().getX() + board.get(0).getParent().getScene().getWidth() / 2 - bid.getWidth() / 2;
        double y = board.get(0).getParent().getScene().getY() + board.get(0).getParent().getScene().getHeight() / 2 - bid.getHeight() / 2;
        
        bid.setX(x);
        bid.setY(y);
        bid.getDialogPane().getScene().getWindow().sizeToScene();
        bid.showAndWait();
    }
    
    /**
     * Retrieves the Dialog for when a board button is clicked on, to provide
     * information on generic areas i.e. non-ownable locations.
     * @param position The Dialog to set up.
     * @param model The model to operate on.
     * @param board The list of BoardButtons to grab the graphic from.
     * @param indexOf The index of the button to be utilised (i.e. board position).
     */
    public static void genericPositionDialog(Dialog position, GameModel model, ArrayList<BoardButton> board, int indexOf) {
   
        position.getDialogPane().setMaxWidth(150);
        
        ImageView graphic = getBoardLocationImage(board, indexOf);
           
        Label text = new Label(model.retrieveLocation(indexOf).getName());
        text.setMinWidth(80);
        text.wrapTextProperty().setValue(Boolean.TRUE);
        HBox box = new HBox(20);
        box.getChildren().addAll(graphic, text);
        box.setMinHeight(graphic.getFitWidth() + 20);
        box.setAlignment(Pos.CENTER);
        
        position.getDialogPane().setContent(box);
        
        if(position.getDialogPane().getButtonTypes().isEmpty()) {
            position.getDialogPane().getButtonTypes().add(ButtonType.OK);
            
            position.getDialogPane().getScene().getWindow().sizeToScene();
            double x = board.get(0).getParent().getScene().getX() + board.get(0).getParent().getScene().getWidth() / 2 - position.getWidth() / 2;
            double y = board.get(0).getParent().getScene().getY() + board.get(0).getParent().getScene().getHeight() / 2 - position.getHeight() / 2;
        
            position.setX(x);
            position.setY(y);
        }
       
        position.showAndWait();
    }
    
    /**
     * Retrieves the Dialog for when an unbought ownable board button is clicked on, 
     * to provide information on the specified location.
     * @param position The Dialog to set up.
     * @param model The model to operate on.
     * @param board The list of BoardButtons to grab the graphic from.
     * @param indexOf The index of the button to be utilised (i.e. board position).
     */
    public static void unboughtOwnableLocationDialog(Dialog position, GameModel model, ArrayList<BoardButton> board, int indexOf) {
        position.getDialogPane().setMaxWidth(170);
        
        ImageView graphic = getBoardLocationImage(board, indexOf);
        Label text = new Label(model.retrieveLocation(indexOf).getName() + "\n" +
                               "You must purchase this property to reveal more information");
        text.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        text.wrapTextProperty().setValue(Boolean.TRUE);
        HBox box = new HBox(20);
        box.getChildren().addAll(graphic, text);
        box.setMinHeight(graphic.getFitWidth() + 20);
        box.setAlignment(Pos.CENTER);
        
        position.getDialogPane().setContent(box);
        
        if(position.getDialogPane().getButtonTypes().isEmpty()) {
            position.getDialogPane().getButtonTypes().add(ButtonType.OK);
            
            position.getDialogPane().getScene().getWindow().sizeToScene();
            double x = board.get(0).getParent().getScene().getX() + board.get(0).getParent().getScene().getWidth() / 2 - position.getWidth() / 2;
            double y = board.get(0).getParent().getScene().getY() + board.get(0).getParent().getScene().getHeight() / 2 - position.getHeight() / 2;
        
            position.setX(x);
            position.setY(y);
        }
        
        position.showAndWait();
    }
    
    /**
     * Retrieves the Dialog for when an unbought ownable board button is clicked on, 
     * to provide information on the specified location.
     * @param position The Dialog to set up.
     * @param model The model to operate on.
     * @param board The list of BoardButtons to grab the graphic from.
     * @param indexOf The index of the button to be utilised (i.e. board position).
     */
    public static void ownedOwnableLocationDialog(Dialog position, GameModel model, ArrayList<BoardButton> board, int indexOf) {
        
        HBox box = new HBox(20);
        
        if(board.get(indexOf).isOwned() && board.get(indexOf).userIsOwner(model.getUser())) {
            ImageView graphic = getBoardLocationImage(board, indexOf);
            //graphic.setRotate(calculateRotation(indexOf));
            box.setMinHeight(graphic.getFitWidth() + 20);
            String userText;
            
            if(board.get(indexOf).getLocation() instanceof PropertyLocation) {
                userText = ((PropertyLocation) board.get(indexOf).getLocation()).getUserOwnedString();
                Label text = new Label(userText);
                text.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
                text.wrapTextProperty().setValue(Boolean.TRUE);
                Button develop = new Button("+");
                develop.setOnAction(e -> {
                    model.userDevelopProperty((PropertyLocation) board.get(indexOf).getLocation());
                });
                develop.setStyle("-fx-font-size: 20;");
            
                Button undevelop = new Button("-");
                undevelop.setOnAction(e -> {
                    model.userRegressProperty((PropertyLocation) board.get(indexOf).getLocation());
                });
                undevelop.setStyle("-fx-font-size: 20;");
                
                Button mortgage = new Button("(De)Mortgage");
                mortgage.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
                
                mortgage.setOnAction(e -> {
                    if(((PropertyLocation) board.get(indexOf).getLocation()).getMortgagedStatus()) {
                        model.deMortgageLocation(board.get(indexOf).getLocation());
                    }
                    else if(!((PropertyLocation) board.get(indexOf).getLocation()).getMortgagedStatus()) {
                        model.mortgageLocation(board.get(indexOf).getLocation());
                    }
                });
                
                VBox box2 = new VBox(20);
                box2.setAlignment(Pos.CENTER);
                box2.getChildren().addAll(graphic, develop, undevelop, mortgage);
                
                box.getChildren().addAll(box2, text);
            }
            else if(board.get(indexOf).getLocation() instanceof RailwayLocation) {
                userText = ((RailwayLocation) board.get(indexOf).getLocation()).getUserOwnedString();
                Label text = new Label(userText);
                text.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
                text.wrapTextProperty().setValue(Boolean.TRUE);
                
                
                Button mortgage = new Button("Mortgage");
                mortgage.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
                
                if(((RailwayLocation) board.get(indexOf).getLocation()).getIsMortgaged()) {
                        mortgage.setDisable(true);
                    }
                
                mortgage.setOnAction(e -> {
                    model.mortgageLocation(board.get(indexOf).getLocation());
                    if(((PropertyLocation) board.get(indexOf).getLocation()).getMortgagedStatus()) {
                        mortgage.setDisable(true);
                    }
                });  
                box.getChildren().addAll(graphic, text, mortgage);
            }
            else if(board.get(indexOf).getLocation() instanceof UtilityLocation) {
                userText = ((UtilityLocation) board.get(indexOf).getLocation()).getUserOwnedString();
                Label text = new Label(userText);
                text.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
                text.wrapTextProperty().setValue(Boolean.TRUE);
                
                Button mortgage = new Button("(De)Mortgage");
                mortgage.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
                
                if(((UtilityLocation) board.get(indexOf).getLocation()).getIsMortgaged()) {
                        mortgage.setDisable(true);
                    }
                
                mortgage.setOnAction(e -> {
                    model.mortgageLocation(board.get(indexOf).getLocation());
                    if(((PropertyLocation) board.get(indexOf).getLocation()).getMortgagedStatus()) {
                        mortgage.setDisable(true);
                    }
                });
                
                box.getChildren().addAll(graphic, text, mortgage);
            }
        }
        else {
            ImageView graphic = getBoardLocationImage(board, indexOf);
            box.setMinHeight(graphic.getFitWidth() + 20);
            String userText;
            
            if(board.get(indexOf).getLocation() instanceof PropertyLocation) {
                userText = ((PropertyLocation) board.get(indexOf).getLocation()).getOwner().getName();
                Label text = new Label(model.retrieveLocation(indexOf).getName() + "\n" +
                                                          userText);
                text.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
                text.wrapTextProperty().setValue(Boolean.TRUE);
                box.getChildren().addAll(graphic, text);   
            }
            else if(board.get(indexOf).getLocation() instanceof RailwayLocation) {
                userText = ((RailwayLocation) board.get(indexOf).getLocation()).getOwner().getName();
                Label text = new Label(model.retrieveLocation(indexOf).getName() + "\n" +
                                                          userText);
                text.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
                text.wrapTextProperty().setValue(Boolean.TRUE);
                box.getChildren().addAll(graphic, text);
            }
            else if(board.get(indexOf).getLocation() instanceof UtilityLocation) {
                userText = ((UtilityLocation) board.get(indexOf).getLocation()).getOwner().getName();
                Label text = new Label(model.retrieveLocation(indexOf).getName() + "\n" +
                                                          userText);
                text.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
                text.wrapTextProperty().setValue(Boolean.TRUE);
                box.getChildren().addAll(graphic, text);
            }
        }
        
        box.setAlignment(Pos.CENTER);
        position.getDialogPane().setContent(box);
        
        if(position.getDialogPane().getButtonTypes().isEmpty()) {
            position.getDialogPane().getButtonTypes().add(ButtonType.OK);
            position.getDialogPane().setMaxWidth(170);
            
            position.getDialogPane().getScene().getWindow().sizeToScene();
            double x = board.get(0).getParent().getScene().getX() + board.get(0).getParent().getScene().getWidth() / 2 - position.getWidth() / 2;
            double y = board.get(0).getParent().getScene().getY() + board.get(0).getParent().getScene().getHeight() / 2 - position.getHeight() / 2;
        
            position.setX(x);
            position.setY(y);
            
            position.getDialogPane().getScene().getWindow().sizeToScene();
        }
        
        position.showAndWait();
    }
    
    /**
     * Retrieves the Dialog for when an unbought ownable board button is clicked on, 
     * to provide information on the specified location.
     * @param trade The Dialog to set up.
     * @param model The model to operate on.
     * @param board The list of BoardButtons to grab the graphic from.
     */
    public static void tradeOfferDialog(Dialog trade, GameModel model, ArrayList<BoardButton> board) {
        model.startTrade(model.getUser());
        
        //trade.getDialogPane().setMaxWidth(400);
        
        HBox sides = new HBox(30);
        sides.setAlignment(Pos.CENTER);
        VBox offer = new VBox(10);
        offer.setAlignment(Pos.CENTER);
        
        Label text = new Label("Your Offer:");
        text.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        text.wrapTextProperty().setValue(Boolean.TRUE);
        
        ListView<String> list = new ListView();
        list.setMaxHeight(100);
        list.setMaxWidth(150);
        
        ObservableList<String> locations = FXCollections.observableArrayList();
        for(Location location : model.getUser().getOwnedLocations()) {
            locations.add(location.getName());
        }
        
        list.setItems(locations);
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        list.setCellFactory(e -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            
            Location location = model.getUser().getOwnedLocations().get(locations.indexOf(cell.textProperty().getValue()) + 1);
            if(location instanceof PropertyLocation) {
                if(((PropertyLocation) location).getMortgagedStatus())
                    cell.getStyleClass().add("mortgaged-cell");
                }
                else if(location instanceof RailwayLocation) {
                    if(((RailwayLocation) location).getIsMortgaged())
                        cell.getStyleClass().add("mortgaged-cell");
                }
                else if(location instanceof UtilityLocation) {
                    if(((UtilityLocation) location).getIsMortgaged())
                        cell.getStyleClass().add("mortgaged-cell");
                }
                        
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                list.requestFocus();
                if (! cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (list.getSelectionModel().getSelectedIndices().contains(index)) {
                        list.getSelectionModel().clearSelection(index);
                    } else {
                        list.getSelectionModel().select(index);
                    }
                    event.consume();
                }
            });
            return cell;
        });
                
        Label cashValue = new Label();
        cashValue.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        cashValue.wrapTextProperty().setValue(Boolean.TRUE);
        Slider cashSlider = new Slider();
        cashSlider.setMaxWidth(120);
        cashSlider.setMin(0);
        cashSlider.setMax(model.getUser().getCash());
        cashSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            cashValue.textProperty().setValue(String.valueOf("£" + (int) cashSlider.getValue()));
        });
        
        HBox gojfBox = new HBox(10);
        gojfBox.setAlignment(Pos.CENTER);
        Label gojf = new Label("Get Out of Jail Free Cards:");
        gojf.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        gojf.wrapTextProperty().setValue(Boolean.TRUE);
        Button add = new Button("+");
        add.setOnAction(e -> {
            if(model.getUser().hasGOJFCard())
                model.getActiveTrade().getGOJFListTo().add(model.getUser().getGOJFCard());
        });
        add.setStyle("-fx-font-size: 20;");
            
        Button remove = new Button("-");
        remove.setOnAction(e -> {
            if(!model.getActiveTrade().getGOJFListTo().isEmpty())
                model.getActiveTrade().getGOJFListTo().remove(0);
        });
        remove.setStyle("-fx-font-size: 20;");
        
        gojfBox.getChildren().addAll(remove, add);
        
        offer.getChildren().addAll(text, list, cashValue, cashSlider, gojf, gojfBox);
        
        
        VBox toGet = new VBox(10);
        toGet.setAlignment(Pos.CENTER);
        
        ComboBox combo = new ComboBox();
        
        for(Player player : model.getPlayerList()) {
            if(player != model.getUser()) {
                combo.getItems().add(player.getName());
            }
        }
        
        ListView<String> forList = new ListView();
        forList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        forList.setMaxHeight(100);
        forList.setMaxWidth(150);
        
        Label cashValueFrom = new Label();
        cashValueFrom.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        cashValueFrom.wrapTextProperty().setValue(Boolean.TRUE);
        Slider cashSliderFrom = new Slider();
        cashSliderFrom.setMaxWidth(120);
        
        combo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList listFor = FXCollections.observableArrayList();
            for(Player player : model.getPlayerList()) {
                if(player.getName() == newValue) {
                    for(Location location : player.getOwnedLocations()) {
                        listFor.add(location.getName());
                    }
                    forList.setItems(listFor);
                    
                    forList.setCellFactory(e -> {
                        ListCell<String> cell = new ListCell<>();
                        cell.textProperty().bind(cell.itemProperty());
                        Location location = model.getActiveTrade().getPlayerTo().getOwnedLocations().get(locations.indexOf(cell.textProperty().getValue()));
                        if(location instanceof PropertyLocation) {
                            if(((PropertyLocation) location).getMortgagedStatus())
                                cell.getStyleClass().add("mortgaged-cell");
                        }
                        else if(location instanceof RailwayLocation) {
                            if(((RailwayLocation) location).getIsMortgaged())
                                cell.getStyleClass().add("mortgaged-cell");
                        }
                        else if(location instanceof UtilityLocation) {
                            if(((UtilityLocation) location).getIsMortgaged())
                                cell.getStyleClass().add("mortgaged-cell");
                        }
                        
                        cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                            list.requestFocus();
                            if (! cell.isEmpty()) {
                                int index = cell.getIndex();
                                if (list.getSelectionModel().getSelectedIndices().contains(index)) {
                                    list.getSelectionModel().clearSelection(index);
                                } 
                                else {
                                    list.getSelectionModel().select(index);
                                }
                                  event.consume();
                            }
                        });
                        return cell;
                    });
                    
                    cashSliderFrom.setMax(player.getCash());
                    model.getActiveTrade().setPlayerTo(player);
                    model.getActiveTrade().getGOJFListFrom().removeAll(model.getActiveTrade().getGOJFListFrom());
                    break;
                    
                }
            }
        });
        
        cashSliderFrom.setMin(0);
        cashSliderFrom.valueProperty().addListener((observable, oldValue, newValue) -> {
            cashValueFrom.textProperty().setValue(String.valueOf("£" + (int) cashSliderFrom.getValue()));
        });
        cashSliderFrom.setValue(0);
        
        HBox gojfBoxFrom = new HBox(10);
        gojfBoxFrom.setAlignment(Pos.CENTER);
        Label gojfFrom = new Label("Get Out of Jail Free Cards:");
        Button addFrom = new Button("+");
        addFrom.setOnAction(e -> {
            if(model.getActiveTrade().getPlayerTo() != null && model.getActiveTrade().getPlayerTo().hasGOJFCard())
                model.getActiveTrade().getGOJFListFrom().add(model.getUser().getGOJFCard());
        });
        addFrom.setStyle("-fx-font-size: 20;");
            
        Button removeFrom = new Button("-");
        removeFrom.setOnAction(e -> {
            if(!model.getActiveTrade().getGOJFListFrom().isEmpty())
                model.getActiveTrade().getGOJFListFrom().remove(0);
        });
        removeFrom.setStyle("-fx-font-size: 20;");
        
        gojfBoxFrom.getChildren().addAll(removeFrom, addFrom);
        
        toGet.getChildren().addAll(combo, forList, cashValueFrom, cashSliderFrom, gojfFrom, gojfBoxFrom);
        
        sides.getChildren().addAll(offer, toGet);
        
        trade.getDialogPane().setContent(sides);
        trade.getDialogPane().getScene().getWindow().sizeToScene();
        
        if(trade.getDialogPane().getButtonTypes().isEmpty()) {
            trade.getDialogPane().getButtonTypes().add(ButtonType.OK);
            trade.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            
            trade.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
                if(combo.getSelectionModel().getSelectedItem() != null) {
       
                    for(Location location : model.getActiveTrade().getPlayerFrom().getOwnedLocations()) {
                        for(String string : list.getSelectionModel().getSelectedItems()) {
                            if(string.equals(location.getName())) 
                                model.getActiveTrade().getOfferList().add(location);
                        }
                    }
                
                    for(Location location : model.getActiveTrade().getPlayerTo().getOwnedLocations()) {
                        for(String string : forList.getSelectionModel().getSelectedItems()) {
                            if(string.equals(location.getName())) 
                                model.getActiveTrade().getOfferList().add(location);
                        }
                    }
                
                    if(cashSlider.getValue() > 0) {
                        model.getActiveTrade().addCashTo((int) cashSlider.getValue());
                    }
                    if(cashSliderFrom.getValue() > 0) {
                        model.getActiveTrade().addCashFrom((int) cashSliderFrom.getValue());
                    }
                }
                else {
                    model.cancelActiveTrade();
                }
                
            });
            trade.getDialogPane().lookupButton(ButtonType.CLOSE).addEventFilter(ActionEvent.ACTION, event -> {
                model.cancelActiveTrade();
            });
            
            trade.getDialogPane().getScene().getWindow().sizeToScene();
            double x = board.get(0).getParent().getScene().getX() + board.get(0).getParent().getScene().getWidth() / 2 - trade.getWidth() / 2;
            double y = board.get(0).getParent().getScene().getY() + board.get(0).getParent().getScene().getHeight() / 2 - trade.getHeight() / 2;
        
            trade.setX(x);
            trade.setY(y);
            
            trade.getDialogPane().getScene().getWindow().sizeToScene();
            
        }
        
        trade.showAndWait();
    }
    
    /**
     * Retrieves the Dialog for when an unbought ownable board button is clicked on, 
     * to provide information on the specified location.
     * @param trade The Dialog to set up.
     * @param model The model to operate on.
     * @param board The list of BoardButtons to grab the graphic from.
     */
    public static void tradeReceivedDialog(Dialog trade, GameModel model, ArrayList<BoardButton> board) {
        model.startTrade(model.getUser());
        
        
        HBox sides = new HBox(30);
        sides.setAlignment(Pos.CENTER);
        VBox offer = new VBox(10);
        offer.setAlignment(Pos.CENTER);
        
        Label text = new Label(model.getActiveTrade().getPlayerFrom().getName() + "has offered:");
        text.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        text.wrapTextProperty().setValue(Boolean.TRUE);
        
        ListView<String> list = new ListView();
        list.setMaxHeight(100);
        list.setMaxWidth(150);
        
        ObservableList<String> locations = FXCollections.observableArrayList();
        for(Location location : model.getUser().getOwnedLocations()) {
            locations.add(location.getName());
        }
        
        list.setItems(locations);
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        list.setCellFactory(e -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            
            Location location = model.getUser().getOwnedLocations().get(locations.indexOf(cell.textProperty().getValue()) + 1);
            if(location instanceof PropertyLocation) {
                if(((PropertyLocation) location).getMortgagedStatus())
                    cell.getStyleClass().add("mortgaged-cell");
                }
                else if(location instanceof RailwayLocation) {
                    if(((RailwayLocation) location).getIsMortgaged())
                        cell.getStyleClass().add("mortgaged-cell");
                }
                else if(location instanceof UtilityLocation) {
                    if(((UtilityLocation) location).getIsMortgaged())
                        cell.getStyleClass().add("mortgaged-cell");
                }
            return cell;
        });
                
        Label cashValue = new Label("Cash: " + model.getActiveTrade().getCashFrom());
        cashValue.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        cashValue.wrapTextProperty().setValue(Boolean.TRUE);
        
        Label gojf = new Label("Get Out of Jail Free Cards: " + model.getActiveTrade().getGOJFListFrom().size());
        gojf.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        gojf.wrapTextProperty().setValue(Boolean.TRUE);
        
        offer.getChildren().addAll(text, list, cashValue, gojf);
        
        
        VBox toGet = new VBox(10);
        toGet.setAlignment(Pos.CENTER);
        
        Label toText = new Label("In Exchange For:");
        
        ListView<String> forList = new ListView();
        forList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        forList.setMaxHeight(100);
        forList.setMaxWidth(150);
        
        Label cashValueTo = new Label("Cash: " + model.getActiveTrade().getCashTo());
        cashValueTo.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        cashValueTo.wrapTextProperty().setValue(Boolean.TRUE);
        
        ObservableList listFor = FXCollections.observableArrayList();
            
        for(Location location : model.getActiveTrade().getForList()) {
            listFor.add(location.getName());
        }
        forList.setItems(listFor);
                    
        forList.setCellFactory(e -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            Location location = model.getActiveTrade().getOfferList().get(locations.indexOf(cell.textProperty().getValue()));
            if(location instanceof PropertyLocation) {
                if(((PropertyLocation) location).getMortgagedStatus())
                    cell.getStyleClass().add("mortgaged-cell");
                }
            else if(location instanceof RailwayLocation) {
                if(((RailwayLocation) location).getIsMortgaged())
                    cell.getStyleClass().add("mortgaged-cell");
                }
            else if(location instanceof UtilityLocation) {
                if(((UtilityLocation) location).getIsMortgaged())
                    cell.getStyleClass().add("mortgaged-cell");
                }
            return cell;
        });
        
        Label gojfTo = new Label("Get Out of Jail Free Cards: " + model.getActiveTrade().getGOJFListTo().size());
        
        toGet.getChildren().addAll(toText, forList, cashValueTo, gojfTo);
        
        sides.getChildren().addAll(offer, toGet);
        
        trade.getDialogPane().setContent(sides);
        trade.getDialogPane().getScene().getWindow().sizeToScene();
        
        if(trade.getDialogPane().getButtonTypes().isEmpty()) {
            trade.getDialogPane().getButtonTypes().add(ButtonType.OK);
            trade.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            
            trade.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
                model.resolveActiveTrade();
            });
            trade.getDialogPane().lookupButton(ButtonType.CLOSE).addEventFilter(ActionEvent.ACTION, event -> {
                model.cancelActiveTrade();
            });
            
            trade.getDialogPane().getScene().getWindow().sizeToScene();
            double x = board.get(0).getParent().getScene().getX() + board.get(0).getParent().getScene().getWidth() / 2 - trade.getWidth() / 2;
            double y = board.get(0).getParent().getScene().getY() + board.get(0).getParent().getScene().getHeight() / 2 - trade.getHeight() / 2;
        
            trade.setX(x);
            trade.setY(y);
            
            trade.getDialogPane().getScene().getWindow().sizeToScene();
            
        }
        
        trade.showAndWait();
    }
    
    /**
     * Generates and displays the stats dialog.
     * @param model The model to get the player object from.
     * @param dialog The dialog to manipulate.
     * @param colours The colours to utilise.
     * @param sprites The sprites to utilise.
     * @param board The board for generating the dialog position.
     */
    public static void statsDialog(GameModel model, Dialog dialog, HashMap<Player, Color> colours, HashMap<Player, ImageView> sprites, ArrayList<BoardButton> board) {
        
        HBox box = new HBox(40);
        
        for(Player player : model.getPlayerList()) {
            VBox info = new VBox(10);
            
            Label label = new Label(player.getName());
            label.setStyle("-fx-text-fill :" + "#" + String.valueOf(colours.get(player)).substring(2) + ";");
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);

            ImageView image = new ImageView(sprites.get(player).getImage());
            
            Label cash = new Label("£" + player.getCash());
            cash.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            
            info.getChildren().addAll(label, image, cash);
            box.getChildren().add(info);
        }
        
        dialog.getDialogPane().setContent(box);
        
        if(dialog.getDialogPane().getButtonTypes().isEmpty()) {
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
            double x = board.get(0).getParent().getScene().getX() + board.get(0).getParent().getScene().getWidth() / 2 - dialog.getWidth() / 2;
            double y = board.get(0).getParent().getScene().getY() + board.get(0).getParent().getScene().getHeight() / 2 - dialog.getHeight() / 2;
        
            dialog.setX(x);
            dialog.setY(y);
        }
        
        dialog.showAndWait();
    }
    
    /**
     * Provides the dialog for bankruptcy resolution.
     * @param model The model to utilise.
     * @param dialog The dialog to populate.
     * @param board The board for positioning the dialog.
     */
    public static void bankruptcyResolutionDialog(GameModel model, Dialog dialog, ArrayList<BoardButton> board) {
        VBox box = new VBox(20);
        
        ListView<String> list = new ListView();
        list.setMaxHeight(100);
        list.setMaxWidth(150);
        
        ObservableList<String> locations = FXCollections.observableArrayList();
        for(Location location : model.getUser().getOwnedLocations()) {
            locations.add(location.getName());
        }
        
        Button undevelop = new Button("Downgrade");
        undevelop.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        
        list.setItems(locations);
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        list.setCellFactory(e -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            
            Location location = model.getUser().getOwnedLocations().get(locations.indexOf(cell.textProperty().getValue()) + 1);
            if(location instanceof PropertyLocation) {
                if(((PropertyLocation) location).getMortgagedStatus())
                    cell.getStyleClass().add("mortgaged-cell");
                }
                else if(location instanceof RailwayLocation) {
                    if(((RailwayLocation) location).getIsMortgaged())
                        cell.getStyleClass().add("mortgaged-cell");
                }
                else if(location instanceof UtilityLocation) {
                    if(((UtilityLocation) location).getIsMortgaged())
                        cell.getStyleClass().add("mortgaged-cell");
                }
                        
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                list.requestFocus();
                if (! cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (location instanceof PropertyLocation && (((PropertyLocation) location).getIsHotel() || ((PropertyLocation) location).getNumberOfHouses() > 0)) {
                        undevelop.disableProperty().setValue(false);
                    } 
                    else {
                        undevelop.disableProperty().setValue(true);
                    }
                    event.consume();
                }
            });
            return cell;
        });
        
        Button sellGOJFCards = new Button("Sell Get Out of Jail Free Card");
        if(model.getUser().hasGOJFCard())
            sellGOJFCards.disableProperty().setValue(false);
        else
            sellGOJFCards.disableProperty().setValue(true);
        
        sellGOJFCards.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        
        sellGOJFCards.setOnAction(e -> {
            
        });
        
        box.getChildren().addAll(list, undevelop, sellGOJFCards);
        
        dialog.getDialogPane().setContent(box);
        
        if(dialog.getDialogPane().getButtonTypes().isEmpty()) {
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            
            dialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
                
            });
            
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
            double x = board.get(0).getParent().getScene().getX() + board.get(0).getParent().getScene().getWidth() / 2 - dialog.getWidth() / 2;
            double y = board.get(0).getParent().getScene().getY() + board.get(0).getParent().getScene().getHeight() / 2 - dialog.getHeight() / 2;
        
            dialog.setX(x);
            dialog.setY(y);
        }
    }
    
    /**
     * Provides the end game dialog.
     * @param manager The manager to utilise.
     * @param model The model to utilise.
     * @param dialog The dialog to fill the content of.
     * @param colours The colour to utilise.
     * @param sprites The sprite to utilise.
     */
    public static void endGameDialog(StageManager manager, GameModel model, Dialog dialog, HashMap<Player, Color> colours, HashMap<Player, ImageView> sprites) {
        VBox box = new VBox(20);
        
        Label name = new Label(model.getPlayerList().get(0).getName() + "has won the Match!");
        name.setStyle("-fx-text-fill: #" + String.valueOf(colours.get(model.getPlayerList().get(0))).substring(2) + ";");
        
        ImageView image = new ImageView(sprites.get(model.getPlayerList().get(0)).getImage());
        
        Label cash = new Label("£" + model.getPlayerList().get(0).getCash());
        
        box.getChildren().addAll(name, image, cash);
        
        dialog.getDialogPane().setContent(box);
        
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            manager.changeScene(View.MAIN_MENU);
        });
    }
}
