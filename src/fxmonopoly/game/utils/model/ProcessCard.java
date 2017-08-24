/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.model;

import fxmonopoly.game.GameController;
import fxmonopoly.game.GameModel;
import fxmonopoly.gamedata.GameData;
import fxmonopoly.gamedata.board.locations.PropertyLocation;
import fxmonopoly.gamedata.board.locations.RailwayLocation;
import fxmonopoly.gamedata.board.locations.UtilityLocation;
import fxmonopoly.gamedata.decks.cards.*;

/**
 * Processes the active card instance contained in the GameData object.
 * <p>
 * Unlike other "Process.." utils in this package, this requires a GameData object
 * due to the wildly varying nature of the card actions that must be dealt with.
 * The wider the scope of manipulations the larger the data structure required.
 * <p>
 * All methods are static since this class is a utility function, not an active
 * participant in game state.
 * @author Sam P. Morrissey
 */
public final class ProcessCard {
    
    /**
     * Ensures that instantiation cannot occur on this class.
     */
    private ProcessCard() {}
    
    /**
     * Checks the type of the active card and then calls the necessary action to
     * perform.
     * @param controller The controller to utilise for text output.
     * @param model The model to utilise.
     * @param data The data to be manipulated.
     */
    public static void processCardActions(GameController controller, GameModel model, GameData data) {
        
        Card card = data.getActiveCard();
        
        if(card instanceof DoublePayableCard) {
            doublePayableCard(controller, data, (DoublePayableCard) card);
        }
        else if(card instanceof GOJFCard) {
            gojfCard(data, (GOJFCard) card);
        }
        else if(card instanceof MoveByCard) {
            moveByCard(controller, model, data, (MoveByCard) card);
        }
        else if(card instanceof MoveToCard) {
            moveToCard(controller, model, data, (MoveToCard) card);
        }
        else if(card instanceof NearestRailwayCard) {
            nearestRailwayCard(controller, model, data, (NearestRailwayCard) card);
        }
        else if(card instanceof NearestUtilityCard) {
            nearestUtilityCard(controller, model, data, (NearestUtilityCard) card);
        }
        else if(card instanceof PayableCard) {
            payableCard(data, (PayableCard) card);
        }
        
    }
    
    /**
     * The action for the DoublePayableCard class.
     * @param data The data to manipulate.
     * @param card The card to evaluate.
     */
    private static void doublePayableCard(GameController controller, GameData data,DoublePayableCard card) {
        int balance = 0;
        
        for(PropertyLocation location : data.getActivePlayer().getOwnedProperty()) {
                if(location.getIsHotel()) {
                    balance += card.getSecondValue();
                }
                else {
                    balance += (card.getFirstValue() * location.getNumberOfHouses());
                }
            }
            
            data.getActivePlayer().addCash(-balance);
            controller.printToTextFlow(data.getActivePlayer().getName() + " paid £" + balance + " for repairs \n", data.getActivePlayer());
    }
    
    /**
     * The action for the GOJFCard class.
     * @param data The data to manipulate.
     * @param card The card to evaluate.
     */
    private static void gojfCard(GameData data, GOJFCard card) {
        data.getActivePlayer().addGOJFCard(card);
    }
    
    /**
     * The action for the MoveByCard class.
     * @param data The data to manipulate.
     * @param card The card to evaluate.
     */
    private static void moveByCard(GameController controller, GameModel model, GameData data, MoveByCard card) {
        data.getActivePlayer().moveBy(card.getDistance());
        model.processRequiredPositionAction();
        controller.pathTransition(model);
        controller.runNextMove();
    }
    
    /**
     * The action for the MoveToCard class.
     * @param data The data to manipulate.
     * @param card The card to evaluate.
     */
    private static void moveToCard(GameController controller, GameModel model, GameData data, MoveToCard card) {
        if(card.getDescription().contains("Go to Jail")) {
                data.getActivePlayer().enterJail();
                data.getActivePlayer().moveTo(((MoveToCard) card).getMoveLocation());
                model.processRequiredPositionAction();
                controller.pathTransition(model);
                controller.printToTextFlow(model.getActivePlayer().getName() + " was sent to Jail \n", model.getActivePlayer());
                model.nextPlayer();
        }
        else {
            if(card.getMoveLocation() < model.getActivePlayer().getPosition()) {
                data.getActivePlayer().addCash(200);
                controller.printToTextFlow(model.getActivePlayer().getName() + " collected £200 passing Go \n", model.getActivePlayer());
            }
            data.getActivePlayer().moveTo(((MoveToCard) card).getMoveLocation());
            model.processRequiredPositionAction();
            controller.pathTransition(model);
            controller.runNextMove();
        }
    }
    
    /**
     * The action for the NearestRailwayCard class.
     * @param data The data to manipulate.
     * @param card The card to evaluate.
     */
    private static void nearestRailwayCard(GameController controller, GameModel model, GameData data, NearestRailwayCard card) {
        if(model.getActivePlayer().getPosition() > 35) {
            data.getActivePlayer().addCash(200);
            controller.printToTextFlow(model.getActivePlayer().getName() + " collected £200 passing Go \n", model.getActivePlayer());
        }
        while(!(data.getBoard().getLocation(data.getActivePlayer().getPosition()) instanceof RailwayLocation)) {
                data.getActivePlayer().moveBy(1);
        }
        controller.pathTransition(model);
        
        RailwayLocation location = (RailwayLocation) data.getBoard().getLocation(data.getActivePlayer().getPosition());
        
        if(location.getIsOwned() && location.getOwner() != data.getActivePlayer()) {
            int i = (location.getBaseRent() * location.getRentMultiplier(location.getOwner().getOwnedRailways().size()) * card.getMultiplier());
            
            data.getActivePlayer().addCash(-i);
            location.getOwner().addCash(i);
        }
        
        controller.runNextMove();
    }
    
    /**
     * The action for the NearestUtilityCard class.
     * @param data The data to manipulate.
     * @param card The card to evaluate.
     */
    private static void nearestUtilityCard(GameController controller, GameModel model, GameData data, NearestUtilityCard card) {
        if(model.getActivePlayer().getPosition() > 28) {
            data.getActivePlayer().addCash(200);
            controller.printToTextFlow(model.getActivePlayer().getName() + " collected £200 passing Go \n", model.getActivePlayer());
        }
        while(!(data.getBoard().getLocation(data.getActivePlayer().getPosition()) instanceof UtilityLocation)) {
                data.getActivePlayer().moveBy(1);
        }
            
        controller.pathTransition(model);
        UtilityLocation location = (UtilityLocation) data.getBoard().getLocation(data.getActivePlayer().getPosition());
        
        if(location.getIsOwned() && location.getOwner() != data.getActivePlayer()) {
            int i = (location.getDoubleMultiplier() * data.getDie().dieRollTotal());
            
            data.getActivePlayer().addCash(-i);
            location.getOwner().addCash(i);
        }
        
    }
    
    /**
     * The action for the PayableCard class.
     * @param data The data to manipulate.
     * @param card The card to evaluate.
     */
    private static void payableCard(GameData data, PayableCard card) {
        
        if(card.getPerPlayer()) {
                
            data.getPlayerList().forEach(e -> {
                if(!(e == data.getActivePlayer())) {
                    e.addCash(-card.getValue());
                    data.getActivePlayer().addCash(card.getValue());
                }
            });
                
        }
        else {
            data.getActivePlayer().addCash(card.getValue());
        }
    }
}
