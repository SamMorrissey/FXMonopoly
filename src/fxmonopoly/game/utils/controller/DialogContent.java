/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.controller;

import fxmonopoly.game.GameModel;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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
    public static void diceRollPane(Alert diceRoll, GameModel model) {
        HBox dice = new HBox(20);
        
        ImageView die1 = new ImageView(new Image("fxmonopoly/resources/images/die/Blank.png"));
        ImageView die2 = new ImageView(new Image("fxmonopoly/resources/images/die/Blank.png"));
        Button rollAction = new Button("Roll");
        
        dice.getChildren().addAll(die1, die2, rollAction);
        dice.setAlignment(Pos.CENTER);
        
        diceRoll.getDialogPane().setContent(dice);   
        diceRoll.getButtonTypes().add(ButtonType.OK);
        diceRoll.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        
        int[] i = model.rollDie();
        rollAction.addEventHandler(ActionEvent.ACTION, e -> {
            HBox box = (HBox) diceRoll.getDialogPane().getContent();
            ((ImageView) box.getChildren().get(0)).setImage(new Image("fxmonopoly/resources/images/die/" + i[0] + ".png"));
            ((ImageView) box.getChildren().get(1)).setImage(new Image("fxmonopoly/resources/images/die/" + i[1] + ".png"));
            
            model.reorderList(i[0] + i[1]);
            
            diceRoll.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
        });
        
        diceRoll.showAndWait();
    }
    
    /**
     * Creates the Dice roll pane that moves the active player.
     * @param diceRoll The alert to set the content of.
     * @param model The model to utilise.
     */
    public static void diceRollAndMovePane(Alert diceRoll, GameModel model) {
        HBox dice = new HBox(20);
        
        ImageView die1 = new ImageView(new Image("fxmonopoly/resources/images/die/Blank.png"));
        ImageView die2 = new ImageView(new Image("fxmonopoly/resources/images/die/Blank.png"));
        Button rollAction = new Button("Roll");
        
        dice.getChildren().addAll(die1, die2, rollAction);
        dice.setAlignment(Pos.CENTER);
        
        diceRoll.getDialogPane().setContent(dice);
        diceRoll.getButtonTypes().add(ButtonType.OK);
        diceRoll.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        
        rollAction.addEventHandler(ActionEvent.ACTION, e -> {
            HBox box = (HBox) diceRoll.getDialogPane().getContent();
            int[] i = model.rollDieAndMove();
            ((ImageView) box.getChildren().get(0)).setImage(new Image("fxmonopoly/resources/images/die/" + i[0] + ".png"));
            ((ImageView) box.getChildren().get(1)).setImage(new Image("fxmonopoly/resources/images/die/" + i[1] + ".png"));
            
            diceRoll.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
        });
        
        diceRoll.showAndWait();
    }
}
