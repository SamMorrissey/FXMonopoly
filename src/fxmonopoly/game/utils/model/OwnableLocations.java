/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.model;

import fxmonopoly.gamedata.GameData;
import fxmonopoly.gamedata.board.locations.*;

/**
 * Defines the highest level manipulations regarding ownable locations, although
 * developments only concern properties, not railways or utilities.
 * <p>
 * All methods are static based on this being a utility function, not 
 * @author Sam P. Morrissey.
 */
public class OwnableLocations {
    
    /**
     * Transfers the ownership of the current location to the active player, must
     * test for current ownership on the location before utilising this method. 
     * This is necessary at a higher level than the GameModel hence this method 
     * does not check.
     * @param data
     */
    public static void activePlayerBuyLocation(GameData data) {
        
        Location location = data.getBoard().getLocation(data.getActivePlayer().getPosition());
        
        if(location instanceof PropertyLocation || location instanceof UtilityLocation ||
           location instanceof RailwayLocation) {
            
            if(location instanceof PropertyLocation) {
                if(data.getActivePlayer().getCash() > ((PropertyLocation) location).getPrice()) {
                    data.getActivePlayer().addCash(((PropertyLocation) location).getPrice());
                    ((PropertyLocation) location).transferOwnership(data.getActivePlayer());
                    data.getBoard().assimilateColourGroupBooleans(data.getBoard().getGroup((PropertyLocation) location));
                    data.getActivePlayer().addLocation(location);
                }
            }
            else if(location instanceof UtilityLocation) {
                if(data.getActivePlayer().getCash() > ((UtilityLocation) location).getPrice()) {
                    data.getActivePlayer().addCash(((UtilityLocation) location).getPrice());
                    ((UtilityLocation) location).setOwner(data.getActivePlayer());
                    data.getActivePlayer().addLocation(location);
                }
            }
            else if(location instanceof RailwayLocation) {
                if(data.getActivePlayer().getCash() > ((RailwayLocation) location).getPrice()) {
                    data.getActivePlayer().addCash(((RailwayLocation) location).getPrice());
                    ((RailwayLocation) location).setOwner(data.getActivePlayer());
                    data.getActivePlayer().addLocation(location);
                }
            }
        } 
    }
    
    /**
     * Develops the specified property provided that the user is the owner. If
     * either the user doesn't own the property or the property/none of its colour
     * group can be developed then this will return 0 and have no effect on 
     * development.
     * @param data
     * @param property The property to develop
     * @return The cost of the development.
     */
    public static int userDevelopProperty(GameData data, PropertyLocation property) {
        
        if(property.getOwner() == data.getUserPlayer()) {
            int cost = data.getBoard().evenlyDevelop(property);
            data.getBoard().assimilateColourGroupBooleans(data.getBoard().getGroup(property));
            data.getUserPlayer().addCash(-cost);
            return cost;
        }
        
        return 0;
    }
    
    /**
     * Regresses the specified property provided that the user is the owner. If
     * either the user doesn't own the property or the property/none of its colour
     * group can be regressed then this will return 0 and have no effect on 
     * development.
     * @param data
     * @param property The property to regress.
     * @return The value of the reimbursement.
     */
    public static int userRegressProperty(GameData data, PropertyLocation property) {
        
        if(property.getOwner() == data.getUserPlayer()) {
            int reimbursement = data.getBoard().evenlyReduce(property);
            data.getBoard().assimilateColourGroupBooleans(data.getBoard().getGroup(property));
            data.getUserPlayer().addCash(reimbursement);
            return reimbursement;
        }
        
        return 0;
    }
    
    /**
     * Develops the specified property provided that the active player is the owner.
     * If either the active player doesn't own this property or the property/none
     * of its colour group can be developed then this will return 0 and have no 
     * effect on development.
     * @param data
     * @param property The property to develop.
     * @return The cost of the development.
     */
    public static int activePlayerDevelopProperty(GameData data, PropertyLocation property) {
        if(property.getOwner() == data.getActivePlayer()) {
            int cost = data.getBoard().evenlyDevelop(property);
            data.getBoard().assimilateColourGroupBooleans(data.getBoard().getGroup(property));
            data.getActivePlayer().addCash(-cost);
            return cost;
        }
        
        return 0;
    }
    
    /**
     * Regresses the specified property provided that the active player is the owner.
     * If either the active player doesn't own this property of the property/none
     * of its colour group can be regressed then this will return 0 and have no
     * effect on development.
     * @param data
     * @param property The property to regress.
     * @return The value of the reimbursement.
     */
    public static int activePlayerRegressProperty(GameData data, PropertyLocation property) {
        if(property.getOwner() == data.getActivePlayer()) {
            int reimburse = data.getBoard().evenlyReduce(property);
            data.getBoard().assimilateColourGroupBooleans(data.getBoard().getGroup(property));
            data.getActivePlayer().addCash(reimburse);
            return reimburse;
        }
        
        return 0;
    }
}
