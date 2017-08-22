/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.model;

import fxmonopoly.gamedata.GameData;
import fxmonopoly.gamedata.board.locations.*;
import fxmonopoly.gamedata.decks.cards.NearestRailwayCard;
import fxmonopoly.gamedata.decks.cards.NearestUtilityCard;

/**
 * Processes the required position actions at the active player location.
 * <p>
 * Contains exclusively static methods as this is a utility class, not an 
 * active participant in game state.
 * @author Sam P. Morrissey.
 */
public final class ProcessPositionAction {
    
    /**
     * Ensures that instantiation cannot occur on this class.
     */
    private ProcessPositionAction() {}
    
    /**
     * Determines the location type then calls the specific manipulation.
     * @param data The data to be manipulated.
     */
    public static void processRequiredPositionAction(GameData data) {
        
        Location location = data.getBoard().getLocation(data.getActivePlayer().getPosition());
        
        if(location instanceof ChanceLocation) {
            chanceLocation(data);
        }
        else if(location instanceof CommunityChestLocation) {
            communityChestLocation(data);
        }
        else if(location instanceof FreeParkingLocation) {
            freeParkingLocation(data);
        }
        else if(location instanceof GoLocation) {
            goLocation(data);
        }
        else if(location instanceof GoToJailLocation) {
            goToJailLocation(data, (GoToJailLocation) location);
        }
        else if(location instanceof JailLocation) {
            jailLocation(data);
        }
        else if(location instanceof PropertyLocation) {
            propertyLocation(data, (PropertyLocation) location);
            
        }
        else if(location instanceof RailwayLocation) {
            railwayLocation(data, (RailwayLocation) location);
        }
        else if(location instanceof TaxLocation) {
            taxLocation(data, (TaxLocation) location);
        }
        else if(location instanceof UtilityLocation) {
            utilityLocation(data, (UtilityLocation) location);
        } 
    }
    
    /**
     * The actions of the ChanceLocation class.
     * @param data The data to be manipulated.
     */
    private static void chanceLocation(GameData data) {
        data.drawNextChanceCard();
    }
    
    /**
     * The actions of the CommunityChestLocation class.
     * @param data The data to be manipulated.
     */
    private static void communityChestLocation(GameData data) {
        data.drawNextCommunityChestCard();
    }
    
    /**
     * The actions of the FreeParkingLocation class.
     * @param data The data to be manipulated.
     */
    private static void freeParkingLocation(GameData data) {
        
    }
    
    /**
     * The actions of the GoLocation class.
     * @param data The data to be manipulated.
     */
    private static void goLocation(GameData data) {
        data.getActivePlayer().addCash(200);
    }
    
    /**
     * The actions of the GoToJailLocation class.
     * @param data The data to be manipulated.
     */
    private static void goToJailLocation(GameData data, GoToJailLocation jail) {
        data.getActivePlayer().enterJail();
        data.getActivePlayer().moveTo(jail.getJailPosition());   
    }
    
    /**
     * The actions of the JailLocation class.
     * @param data The data to be manipulated.
     */
    private static void jailLocation(GameData data) {
        
    }
    
    /**
     * The actions of the PropertyLocation class.
     * @param data The data to be manipulated.
     */
    private static void propertyLocation(GameData data, PropertyLocation property) {
        if(property.getIsOwned() && !(property.getMortgagedStatus()) && property.getOwner() != data.getActivePlayer()) {
            data.getActivePlayer().addCash(-property.getRent());
            property.getOwner().addCash(property.getRent());
        }
    } 
    
    /**
     * The actions of the RailwayLocation class.
     * @param data The data to be manipulated.
     */
    private static void railwayLocation(GameData data, RailwayLocation railway) {
        if(railway.getIsOwned() && !(railway.getIsMortgaged())) {
                if(data.getActiveCard() instanceof NearestRailwayCard) {
                    int rent = (railway.getRentMultiplier(railway.getOwner().getOwnedRailways().size())) * 2;
                    data.getActivePlayer().addCash(-rent);
                    railway.getOwner().addCash(rent);
                }
                else {
                    int rent = (railway.getRentMultiplier(railway.getOwner().getOwnedRailways().size()));
                    data.getActivePlayer().addCash(-rent);
                    railway.getOwner().addCash(rent);
                }
        }
    }
    
    /**
     * The actions of the TaxLocation class.
     * @param data The data to be manipulated.
     */
    private static void taxLocation(GameData data, TaxLocation location) {
        data.getActivePlayer().addCash(((TaxLocation) location).getValue());
    }
    
    /**
     * The actions of the UtilityLocation class.
     * @param data The data to be manipulated.
     */
    private static void utilityLocation(GameData data, UtilityLocation location) {
        int multiplier = 0;
            
            if(location.getIsOwned() && !(location.getIsMortgaged())) {
                if(location.getOwner().getOwnedUtilities().size() == 1 && !(data.getActiveCard() instanceof NearestUtilityCard)) {
                    multiplier = location.getSingleMultiplier();
                    int rent = (multiplier * data.getDie().dieRollTotal());
                    data.getActivePlayer().addCash(-rent);
                    location.getOwner().addCash(rent);
                }
                else {
                    multiplier = location.getDoubleMultiplier();
                    int rent = (multiplier * data.getDie().dieRollTotal());
                    data.getActivePlayer().addCash(-rent);
                    location.getOwner().addCash(rent);
                }
            }
    }
}
