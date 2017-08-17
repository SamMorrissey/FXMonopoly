/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.model;

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
     * @param data The data to be manipulated.
     */
    public static void processCardActions(GameData data) {
        
        Card card = data.getActiveCard();
        
        if(card instanceof DoublePayableCard) {
            doublePayableCard(data, (DoublePayableCard) card);
        }
        else if(card instanceof GOJFCard) {
            gojfCard(data, (GOJFCard) card);
        }
        else if(card instanceof MoveByCard) {
            moveByCard(data, (MoveByCard) card);
        }
        else if(card instanceof MoveToCard) {
            moveToCard(data, (MoveToCard) card);
        }
        else if(card instanceof NearestRailwayCard) {
            nearestRailwayCard(data, (NearestRailwayCard) card);
        }
        else if(card instanceof NearestUtilityCard) {
            nearestUtilityCard(data, (NearestUtilityCard) card);
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
    private static void doublePayableCard(GameData data,DoublePayableCard card) {
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
    private static void moveByCard(GameData data, MoveByCard card) {
        data.getActivePlayer().moveBy(card.getDistance());
    }
    
    /**
     * The action for the MoveToCard class.
     * @param data The data to manipulate.
     * @param card The card to evaluate.
     */
    private static void moveToCard(GameData data, MoveToCard card) {
        if(card.getDescription().contains("Go to Jail")) {
                data.getActivePlayer().moveTo(((MoveToCard) card).getMoveLocation());
                data.getActivePlayer().enterJail();
        }
        data.getActivePlayer().moveTo(((MoveToCard) card).getMoveLocation());
    }
    
    /**
     * The action for the NearestRailwayCard class.
     * @param data The data to manipulate.
     * @param card The card to evaluate.
     */
    private static void nearestRailwayCard(GameData data, NearestRailwayCard card) {
        while(!(data.getBoard().getLocation(data.getActivePlayer().getPosition()) instanceof RailwayLocation)) {
                data.getActivePlayer().moveBy(1);
            }
        
        RailwayLocation location = (RailwayLocation) data.getBoard().getLocation(data.getActivePlayer().getPosition());
        
        if(location.getIsOwned() && location.getOwner() != data.getActivePlayer()) {
            int i = (location.getBaseRent() * location.getRentMultiplier(location.getOwner().getOwnedRailways().size()) * card.getMultiplier());
            
            data.getActivePlayer().addCash(-i);
            location.getOwner().addCash(i);
        }
    }
    
    /**
     * The action for the NearestUtilityCard class.
     * @param data The data to manipulate.
     * @param card The card to evaluate.
     */
    private static void nearestUtilityCard(GameData data, NearestUtilityCard card) {
        while(!(data.getBoard().getLocation(data.getActivePlayer().getPosition()) instanceof UtilityLocation)) {
                data.getActivePlayer().moveBy(1);
            }
            
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
