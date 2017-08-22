/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.controller;

import fxmonopoly.game.GameController;
import fxmonopoly.game.GameModel;
import fxmonopoly.gamedata.board.locations.*;
import fxmonopoly.gamedata.players.Player;

/**
 * Defines the AI interactions, without the concern of implementation.
 * <p>
 * Some methods are designed to be called from the Controller hence these require
 * a player to operate with.
 * @author Sam P. Morrissey
 */
public class DecisionSystem {
    
    private final GameModel model;
    private final GameController controller;
    
    public DecisionSystem(GameModel model, GameController controller) {
        this.model = model;
        this.controller = controller;
    }
    
    /**
     * Makes a decision to leave or stay in jail based on existing game conditions.
     */
    public void jailDecision() {
        if(model.getActivePlayer().isInJail()) {
            if(model.getActivePlayer().getOwnedProperty().size() < 3) {
                if(model.getActivePlayer().hasGOJFCard()) {
                    model.useActivePlayerGOJFCard();
                }
                else {
                    model.activePlayerPayToExitJail();
                }
            }
            else {
                int highestWorth = 0;
                
                for(Player player : model.getPlayerList()) {
                    if(player.getWorth() > highestWorth) {
                        highestWorth = player.getWorth();
                    }
                }
                
                if(!((model.getActivePlayer().getWorth() * 2) < highestWorth)) {
                    if(model.getActivePlayer().hasGOJFCard()) {
                        model.useActivePlayerGOJFCard();
                    }
                    else {
                        model.activePlayerPayToExitJail();
                    }
                }
            }
        }
    }
    
    /**
     * Rolls the dice and moves the active player.
     */
    public void rollDiceAndMove() {
        model.diceMove(model.rollDie());
    }
    
    /**
     * Determines the action to be taken at positions that have specific game
     * actions (GO, Jail, Go To Jail, Free Parking etc have required actions that
     * are not dealt with by players).
     */
    public void positionAction() {
        Location location = model.retrieveLocation(model.getActivePlayer().getPosition());
        
        if(location instanceof ChanceLocation) {
            model.processCardActions();
        }
        else if(location instanceof CommunityChestLocation) {
            model.processCardActions();
        }
        else if(location instanceof PropertyLocation) {
            
        }
        else if(location instanceof RailwayLocation) {
            
        }
        else if(location instanceof UtilityLocation) {
            
        }
    }
    
    public void propertyBuyingDecision(PropertyLocation property) {
        
    }
    
    public void utilityBuyingDecision(UtilityLocation utility) {
        
    }
    
    public void developmentDecision() {
        
    }
    
    public int minimumCashPosition() {
        
    }
    
    public void activePlayerMakeTrade() {
        
    }
    
    public boolean specifiedPlayerRespondToTrade() {
        
    }
    
    public void makeBid(Player player) {
        
    }
    
    public int valueDeterminer(Object object) {
        
    }
    
    public void bankruptcyResolution() {
        
    }
}
