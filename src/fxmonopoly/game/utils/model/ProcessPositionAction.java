/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.model;

import fxmonopoly.game.GameController;
import fxmonopoly.game.GameModel;
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
     * @param controller The controller for text output.
     * @param model The model to utilise.
     * @param data The data to be manipulated.
     */
    public static void processRequiredPositionAction(GameController controller, GameModel model, GameData data) {
        
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
            goLocation(controller, data);
        }
        else if(location instanceof GoToJailLocation) {
            goToJailLocation(controller, data, (GoToJailLocation) location);
        }
        else if(location instanceof JailLocation) {
            jailLocation(data);
        }
        else if(location instanceof PropertyLocation) {
            propertyLocation(controller, data, (PropertyLocation) location);
            
        }
        else if(location instanceof RailwayLocation) {
            railwayLocation(controller, data, (RailwayLocation) location);
        }
        else if(location instanceof TaxLocation) {
            taxLocation(controller, data, (TaxLocation) location);
        }
        else if(location instanceof UtilityLocation) {
            utilityLocation(controller, data, (UtilityLocation) location);
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
     * @param controller The controller for text output
     * @param data The data to be manipulated.
     */
    private static void goLocation(GameController controller, GameData data) {
        data.getActivePlayer().addCash(200);
        controller.printToTextFlow(data.getActivePlayer().getName() + " collects £200 \n", data.getActivePlayer());
    }
    
    /**
     * The actions of the GoToJailLocation class.
     * @param controller The controller for text output
     * @param data The data to be manipulated.
     * @param jail The jail location.
     */
    private static void goToJailLocation(GameController controller, GameData data, GoToJailLocation jail) {
        data.getActivePlayer().enterJail();
        data.getActivePlayer().moveTo(jail.getJailPosition());
        controller.printToTextFlow(data.getActivePlayer().getName() + " made bad choices... \n", data.getActivePlayer());
    }
    
    /**
     * The actions of the JailLocation class.
     * @param data The data to be manipulated.
     */
    private static void jailLocation(GameData data) {
        
    }
    
    /**
     * The actions of the PropertyLocation class.
     * @param controller The controller for text output
     * @param data The data to be manipulated.
     * @param property The property location.
     */
    private static void propertyLocation(GameController controller, GameData data, PropertyLocation property) {
        if(property.getIsOwned() && !(property.getMortgagedStatus()) && property.getOwner() != data.getActivePlayer()) {
            data.getActivePlayer().addCash(-property.getRent());
            property.getOwner().addCash(property.getRent());
            controller.printToTextFlow(data.getActivePlayer().getName() + " paid £" + property.getRent() + " to " + property.getOwner().getName() + "\n", data.getActivePlayer());
        }
    } 
    
    /**
     * The actions of the RailwayLocation class.
     * @param controller The controller for text output
     * @param data The data to be manipulated.
     * @param railway The railway location.
     */
    private static void railwayLocation(GameController controller, GameData data, RailwayLocation railway) {
        if(railway.getIsOwned() && !(railway.getIsMortgaged())) {
                if(data.getActiveCard() instanceof NearestRailwayCard) {
                    int rent = railway.getBaseRent() * ((railway.getRentMultiplier(railway.getOwner().getOwnedRailways().size())) * 2);
                    data.getActivePlayer().addCash(-rent);
                    railway.getOwner().addCash(rent);
                    controller.printToTextFlow(data.getActivePlayer().getName() + " paid £" + rent + " to " + railway.getOwner().getName(), data.getActivePlayer());
                }
                else {
                    int rent = railway.getBaseRent() * (railway.getRentMultiplier(railway.getOwner().getOwnedRailways().size()));
                    data.getActivePlayer().addCash(-rent);
                    railway.getOwner().addCash(rent);
                    controller.printToTextFlow(data.getActivePlayer().getName() + " paid £" + rent + " to " + railway.getOwner().getName(), data.getActivePlayer());
                }
        }
    }
    
    /**
     * The actions of the TaxLocation class.
     * @param controller The controller for text output
     * @param data The data to be manipulated.
     * @param location The tax location.
     */
    private static void taxLocation(GameController controller, GameData data, TaxLocation location) {
        data.getActivePlayer().addCash(location.getValue());
        controller.printToTextFlow(data.getActivePlayer().getName() + " paid £" + location.getValue() + " in tax \n", data.getActivePlayer());
    }
    
    /**
     * The actions of the UtilityLocation class.
     * @param controller The controller for text output
     * @param data The data to be manipulated.
     * @param location The utility location.
     */
    private static void utilityLocation(GameController controller, GameData data, UtilityLocation location) {
        int multiplier = 0;
            
            if(location.getIsOwned() && !(location.getIsMortgaged())) {
                if(location.getOwner().getOwnedUtilities().size() == 1 && !(data.getActiveCard() instanceof NearestUtilityCard)) {
                    multiplier = location.getSingleMultiplier();
                    int rent = (multiplier * data.getDie().dieRollTotal());
                    data.getActivePlayer().addCash(-rent);
                    location.getOwner().addCash(rent);
                    controller.printToTextFlow(data.getActivePlayer().getName() + " paid £" + rent + " to " + location.getOwner().getName(), data.getActivePlayer());
                }
                else {
                    multiplier = location.getDoubleMultiplier();
                    int rent = (multiplier * data.getDie().dieRollTotal());
                    data.getActivePlayer().addCash(-rent);
                    location.getOwner().addCash(rent);
                    controller.printToTextFlow(data.getActivePlayer().getName() + " paid £" + rent + " to " + location.getOwner().getName(), data.getActivePlayer());
                }
            }
    }
}
