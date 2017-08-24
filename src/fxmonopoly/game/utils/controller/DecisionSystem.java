/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.controller;

import fxmonopoly.game.GameController;
import fxmonopoly.game.GameModel;
import fxmonopoly.gamedata.board.locations.*;
import fxmonopoly.gamedata.decks.cards.GOJFCard;
import fxmonopoly.gamedata.players.Player;
import java.util.ArrayList;
import java.util.Iterator;

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
            propertyBuyingDecision((PropertyLocation) location);
        }
        else if(location instanceof RailwayLocation) {
            if(model.getActivePlayer().getCash() > ((RailwayLocation) location).getPrice() && !((RailwayLocation) location).getIsOwned()) {
                model.activePlayerBuyLocation();
                //controller.printToTextFlow(model.getActivePlayer().getName() + " has purchased " + location.getName() + "\n", model.getActivePlayer());
            }
        }
        else if(location instanceof UtilityLocation) {
            utilityBuyingDecision((UtilityLocation) location);
        }
    }
    
    /**
     * Makes a property buying decision for the active player.
     * @param property The property under consideration.
     */
    public void propertyBuyingDecision(PropertyLocation property) {
        if(valueDeterminer(property, model.getActivePlayer()) >= property.getPrice() && model.getActivePlayer().getCash() > property.getPrice() && !property.getIsOwned()) {
            model.activePlayerBuyLocation();
            //controller.printToTextFlow(model.getActivePlayer().getName() + " has bought " + property.getName() + "\n", model.getActivePlayer());
            
            boolean unOwned = true;
            for(PropertyLocation prop : model.getColourGroup(property)) {
                if(prop.getIsOwned() && prop.getOwner() != model.getActivePlayer()) {
                    unOwned = false;
                    break;
                }
            }
            
            if(!unOwned) {
                model.mortgageLocation(property);
                //controller.printToTextFlow(model.getActivePlayer().getName() + " has mortgaged " + property.getName() + "\n", model.getActivePlayer());
            }
        }
        else {
            controller.bidResolution();
        }
    }
    
    /**
     * Makes a utility buying decision for the active player.
     * @param utility The utility under consideration.
     */
    public void utilityBuyingDecision(UtilityLocation utility) {
        if(valueDeterminer(utility, model.getActivePlayer()) >= utility.getPrice() && model.getActivePlayer().getCash() > utility.getPrice() && !utility.getIsOwned()) {
            model.activePlayerBuyLocation();
            //controller.printToTextFlow(model.getActivePlayer().getName() + " has bought " + utility.getName(), model.getActivePlayer());
        }
        else {
            controller.bidResolution();
        }
    }
    
    /**
     * Makes a development decision for the active player.
     */
    public void developmentDecision() {
        
        for(PropertyLocation location : model.getActivePlayer().getOwnedProperty()) {
            if(location.getMortgagedStatus() && model.getActivePlayer().getOwnedProperty().containsAll(model.getColourGroup(location))) {
                if(model.getActivePlayer().getCash() > minimumCashPosition()) {
                    model.deMortgageLocation(location);
                }
            }
        }
        
        if(model.getActivePlayer().getOwnedProperty().isEmpty()) {
            return;
        }
        else {
            ArrayList<ArrayList<PropertyLocation>> developable = new ArrayList<>();
            
            for(PropertyLocation property : model.getActivePlayer().getOwnedProperty()) {
                if(model.getActivePlayer().getOwnedProperty().containsAll(model.getColourGroup(property)) &&
                        !developable.contains(model.getColourGroup(property))) {
                    boolean notAllHotels = false;
                    for(PropertyLocation prop : model.getColourGroup(property)) {
                        if(!prop.getIsHotel()) {
                            notAllHotels = true;
                            break;
                        }
                    }
                    if(notAllHotels)
                        developable.add(model.getColourGroup(property));
                }
            }
            
            if(developable.isEmpty()) {
                return;
            }
            
            int difference = model.getActivePlayer().getCash() - minimumCashPosition();
            int times = 0;
            
            for(ArrayList<PropertyLocation> array : developable) {
                if(model.retrieveLocationPosition(array.get(0)) >= 17 && model.retrieveLocationPosition(array.get(difference)) <= 19) {
                    times = difference / array.get(0).getHousePrice();
                    int i;
                    for(i = 0; i < times; i++) {
                        model.activePlayerDevelopProperty(array.get(array.size() - 1));
                    }
                    return;
                }
                else if(model.retrieveLocationPosition(array.get(0)) >= 21 && model.retrieveLocationPosition(array.get(difference)) <= 24) {
                    times = difference / array.get(0).getHousePrice();
                    int i;
                    for(i = 0; i < times; i++) {
                        model.activePlayerDevelopProperty(array.get(array.size() - 1));
                    }
                    return;
                }
                else if(model.retrieveLocationPosition(array.get(0)) >= 37 && model.retrieveLocationPosition(array.get(difference)) <= 39) {
                    times = difference / array.get(0).getHousePrice();
                    int i;
                    for(i = 0; i < times; i++) {
                        model.activePlayerDevelopProperty(array.get(array.size() - 1));
                    }
                }
                else if(model.retrieveLocationPosition(array.get(0)) >= 11 && model.retrieveLocationPosition(array.get(difference)) <= 14) {
                    times = difference / array.get(0).getHousePrice();
                    int i;
                    for(i = 0; i < times; i++) {
                        model.activePlayerDevelopProperty(array.get(array.size() - 1));
                    }
                    return;
                }
                else if(model.retrieveLocationPosition(array.get(0)) >= 6 && model.retrieveLocationPosition(array.get(difference)) <= 9) {
                    times = difference / array.get(0).getHousePrice();
                    int i;
                    for(i = 0; i < times; i++) {
                        model.activePlayerDevelopProperty(array.get(array.size() - 1));
                    }
                    return;
                }
                else if(model.retrieveLocationPosition(array.get(0)) >= 1 && model.retrieveLocationPosition(array.get(difference)) <= 3) {
                    times = difference / array.get(0).getHousePrice();
                    int i;
                    for(i = 0; i < times; i++) {
                        model.activePlayerDevelopProperty(array.get(array.size() - 1));
                    }
                    return;
                }
                else if(model.retrieveLocationPosition(array.get(0)) >= 26 && model.retrieveLocationPosition(array.get(difference)) <= 29) {
                    times = difference / array.get(0).getHousePrice();
                    int i;
                    for(i = 0; i < times; i++) {
                        model.activePlayerDevelopProperty(array.get(array.size() - 1));
                    }
                    return;
                }
                else if(model.retrieveLocationPosition(array.get(0)) >= 31 && model.retrieveLocationPosition(array.get(difference)) <= 34) {
                    times = difference / array.get(0).getHousePrice();
                    int i;
                    for(i = 0; i < times; i++) {
                        model.activePlayerDevelopProperty(array.get(array.size() - 1));
                    }
                    return;
                }
            }
        }
    }
    
    /**
     * Determines the minimum desired cash position based on the current highest
     * worth.
     * @return The minimum cash position.
     */
    public int minimumCashPosition() {
        
        int highestWorth = 0;
        
        for(Player player : model.getPlayerList()) {
            if(player.getWorth() > highestWorth) {
                highestWorth = player.getWorth();
            }
        }
        
        return highestWorth / 3;
    }
    
    /**
     * Makes a trade if a viable possible trade is found otherwise exits.
     */
    public void makeTrade() {
        model.startTrade(model.getActivePlayer());
        
        if(!model.getActivePlayer().getOwnedLocations().isEmpty()) {
            for(PropertyLocation property : model.getActivePlayer().getOwnedProperty()) {
                ArrayList<Player> tradeOwner = new ArrayList<>();
                ArrayList<PropertyLocation> propertyList  = new ArrayList<>();
                
                for(PropertyLocation temp : model.getColourGroup(property)) {
                    if(temp.getOwner() != null && temp.getOwner() != model.getActivePlayer()) {
                        propertyList.add(temp);
                        tradeOwner.add(temp.getOwner());
                    }
                }
                
                if(!tradeOwner.isEmpty() && !propertyList.isEmpty()) {
                    if(tradeOwner.get(0) == propertyList.get(0).getOwner()) {
                        model.getActiveTrade().getForList().add(propertyList.get(0));
                        model.getActiveTrade().setPlayerTo(tradeOwner.get(0));
                    }
                    
                    for(PropertyLocation location : tradeOwner.get(0).getOwnedProperty()) {
                        int value = 0;
                        
                        for(PropertyLocation temp2 : model.getColourGroup(location)) {
                            if(model.getColourGroup(temp2) != model.getColourGroup(property) && 
                              (valueDeterminer(temp2, model.getActivePlayer()) > temp2.getValue()) &&
                              (temp2.getOwner() == model.getActivePlayer()) ) {
                            
                                model.getActiveTrade().getOfferList().add(temp2);
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
            
            if(model.getActiveTrade().hasPlayerTo() && model.getActiveTrade().containsOfferLocations() && model.getActiveTrade().containsForLocations()) {
                controller.tradeResolution();
            }
        }
    }
    
    /**
     * Determines whether a player will accept or decline a trade.
     * @param player The player receiving the trade.
     */
    public void specifiedPlayerRespondToTrade(Player player) {
        
        int receiveValue = 0;
        
        for(Location location : model.getActiveTrade().getOfferList()) {
            receiveValue += valueDeterminer(location, player);
        }
        
        receiveValue += model.getActiveTrade().getCashTo();
        receiveValue += model.getActiveTrade().getGOJFListTo().size() * 50;
        
        
        int sendOverValue = 0;
        
        for(Location location : model.getActiveTrade().getForList()) {
            sendOverValue += valueDeterminer(location, player);
        }
        
        sendOverValue += model.getActiveTrade().getCashFrom();
        sendOverValue += model.getActiveTrade().getGOJFListFrom().size() * 50;
        
        if(receiveValue < sendOverValue || receiveValue == sendOverValue) {
            model.cancelActiveTrade();
            controller.printToTextFlow(player.getName() + " has declined a trade \n", player);
        }
        else {
            model.resolveActiveTrade();
            controller.printToTextFlow(player.getName() + " has accepted a trade \n", player);
        }
        
    }
    
    /**
     * Makes a bid relative to the player's current state.
     * @param player The player to bid.
     */
    public void makeBid(Player player) {
        if(model.getActiveBid().containsGOJFCard()) {
            model.getActiveBid().addBid(player, 45);
        }
        else {
            model.getActiveBid().addBid(player, valueDeterminer(model.getActiveBid().getLocation(), player));
        }
    }
    
    /**
     * Determines the value of an inputted object, provided it is a property,
     * railway, utility or GOJFCard.
     * @param object The object to determine the value of.
     * @param player The player to determine the value for.
     * @return The value of the input object, or 0 if not a valid object.
     */
    public int valueDeterminer(Object object, Player player) {
        
        if(object instanceof PropertyLocation) {
            PropertyLocation property = (PropertyLocation) object;
            
            int numberOwned = 0;
            ArrayList<Player> owners = new ArrayList<>();
            
            for(PropertyLocation temp : model.getColourGroup(property)) {
                if(temp.getOwner() == player) {
                    numberOwned++;
                }
                else if(temp.getOwner() != null) {
                    owners.add(temp.getOwner());
                }
            }
            
            if(numberOwned == model.getColourGroup(property).size() - 1 && property.getOwner() != player) {
                if(property.getMortgagedStatus()) {
                    return (int) (property.getPrice() * 2.5) / 2;
                }
                else {
                    return (int) ((property.getPrice() + (property.getHousePrice() * property.getNumberOfHouses())) * 2.5);
                }
            }
            else if(numberOwned == 0 && owners.size() == 1 && model.getColourGroup(property).size() > 2) {
                if(property.getMortgagedStatus()) {
                    return (property.getPrice() * 3) / 2;
                }
                else {
                    return (int) ((property.getPrice() + (property.getHousePrice() * property.getNumberOfHouses())) * 3);
                }
            }
            else {
                if(property.getMortgagedStatus()) {
                    return (int) (property.getPrice() * 1.5) / 2;
                }
                else {
                    return (int) ((property.getPrice() + (property.getHousePrice() * property.getNumberOfHouses())) * 1.5);
                }
            }
        }
        else if(object instanceof RailwayLocation) {
            RailwayLocation railway = (RailwayLocation) object;
            
            if(player.getOwnedRailways().isEmpty()) {
                if(railway.getIsMortgaged()) 
                    return railway.getPrice();
                else 
                    return (int) (railway.getPrice() * 2);
            }
            else {
                if(railway.getIsMortgaged())
                    return (int) (railway.getPrice() * (player.getOwnedRailways().size())) / 2;
                else
                    return (int) (railway.getPrice() * (player.getOwnedRailways().size()));
            }
        }
        else if(object instanceof UtilityLocation) {
            UtilityLocation utility = (UtilityLocation) object;
            
            if(player.getOwnedUtilities().isEmpty()) {
                if(utility.getIsMortgaged())
                    return (int) (utility.getPrice() * 0.8) / 2;
                else
                    return (int) (utility.getPrice() * 0.8);
            }
            else {
                if(utility.getIsMortgaged())
                    return (int) (utility.getPrice() * 1.2) / 2;
                else
                    return (int) (utility.getPrice() * 1.2);
            }
        }
        else if(object instanceof GOJFCard) {
            return 50;
        }
        
        return 0;
    }
    
    /**
     * Determines what to do to resolve bankruptcy based on the game conditions.
     */
    public void bankruptcyResolution() {
        model.startBid();
        
        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<Location> lowestValueFirst = new ArrayList<>();
        
        if(model.getActivePlayer().hasGOJFCard()) {
            model.getActiveBid().setGOJFCard(model.getActivePlayer().getGOJFCard());
        }
        else {
            model.getActivePlayer().getOwnedProperty().stream().filter((property) -> (!property.getIsHotel() && property.getNumberOfHouses() == 0)).forEachOrdered((property) -> {
                locations.add(property);
            });
            model.getActivePlayer().getOwnedRailways().forEach((railway) -> {
                locations.add(railway);
            });
            model.getActivePlayer().getOwnedUtilities().forEach((utility) -> {
                locations.add(utility);
            });
            
            if(locations.isEmpty() && !model.getActivePlayer().getOwnedProperty().isEmpty()) {
                for(PropertyLocation property : model.getActivePlayer().getOwnedProperty()) {
                
                    while(model.getActivePlayer().getCash() < 0 || property.getNumberOfHouses() > 0 || property.getIsHotel()) {
                        model.activePlayerRegressProperty(property);
                    }
                    
                    if(model.getActivePlayer().getCash() >= 0) {
                        return;
                    }
                }
            }
            
            if(locations.isEmpty()) {
                return;
            }
        }

        while(!locations.isEmpty()) {
            int i = 20000;
            
            for(Location location : locations) {
                int value = valueDeterminer(location, model.getActivePlayer());
                
                if(value < i) {
                    i = value;
                }
            }
            
            Iterator it = locations.iterator();
            while(it.hasNext()) {
                if(i == valueDeterminer((Location) it.next(), model.getActivePlayer())) {
                    lowestValueFirst.add((Location) it.next());
                    locations.remove((Location) it.next());
                }
            } 
        }
        model.getActiveBid().setLocation(lowestValueFirst.get(0));
    }
}
