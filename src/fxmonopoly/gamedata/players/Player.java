/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.players;

import fxmonopoly.gamedata.board.locations.Location;
import fxmonopoly.gamedata.board.locations.PropertyLocation;
import fxmonopoly.gamedata.board.locations.RailwayLocation;
import fxmonopoly.gamedata.board.locations.UtilityLocation;
import fxmonopoly.gamedata.decks.cards.GOJFCard;
import java.util.ArrayList;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Defines the Player class almost entirely, with the separate concrete extensions
 * merely being utilised for action parsing.
 * @author Sam P. Morrissey
 */
public abstract class Player {
    
    private final String name;
    
    private final ArrayList<Location> ownedLocations;
    
    private final ArrayList<GOJFCard> ownedGOJFCards;
    
    private final SimpleBooleanProperty inJail;
    private final SimpleBooleanProperty hasGOJFCard;
    private final SimpleBooleanProperty canRoll;
  
    private final SimpleIntegerProperty cash;
    private final SimpleIntegerProperty boardPosition;
    
    private int turnsInJail;
    
    
    
    /**
     * Creates a Player object with the specified name. ArrayLists instantiated,
     * and default parameters set.
     * @param name The name of the Player.
     */
    public Player(String name) {
        this.name = name;
        
        ownedLocations = new ArrayList<>();
        ownedGOJFCards = new ArrayList<>();
        
        inJail = new SimpleBooleanProperty(this, "inJail", false);
        hasGOJFCard = new SimpleBooleanProperty(this, "hasGOJFCard", false);
        canRoll = new SimpleBooleanProperty(this, "canRoll", false);
        cash = new SimpleIntegerProperty(this, "cash", 1500);
        boardPosition = new SimpleIntegerProperty(this, "position", 0);
        
    }
    
    /**
     * Retrieves the name of the player.
     * @return The player's name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Adds a location to this player's list of owned locations. Exactly equivalent
     * to an ArrayList.add() call, as long as the location reference is not already
     * in the list.
     * @param location The location to be added.
     */
    public void addLocation(Location location) {
        if(!ownedLocations.contains(location)) {
            ownedLocations.add(location);
        }
    }
    
    /**
     * Removes a location from this player's list of owned locations. The location
     * must be in the list of owned locations to have any effect.
     * @param location The location to be removed.
     */
    public void removeLocation(Location location) {
        if(ownedLocations.contains(location)) {
            ownedLocations.remove(location);
        }
    }
    
    /**
     * Retrieves the ArrayList of locations owned by the player.
     * @return The list of owned locations.
     */
    public ArrayList<Location> getOwnedLocations() {
        return ownedLocations;
    }
    
    /**
     * Retrieves the ArrayList of properties owned by the player.
     * @return The list of owned properties.
     */
    public ArrayList<PropertyLocation> getOwnedProperty() {
        ArrayList<PropertyLocation> owned = new ArrayList<>();
        
        ownedLocations.forEach(location -> {
            if(location instanceof PropertyLocation) {
                owned.add((PropertyLocation) location);
            }
        });
        
        return owned;
    }
    
    /**
     * Retrieves the ArrayList of utilities owned by the player.
     * @return The list of owned utilities.
     */
    public ArrayList<UtilityLocation> getOwnedUtilities() {
        ArrayList<UtilityLocation> owned = new ArrayList<>();
        
        ownedLocations.forEach(location -> {
            if(location instanceof UtilityLocation) {
                owned.add((UtilityLocation) location);
            }
        });
        
        return owned;
    }
    
    /**
     * Retrieves the ArrayList of railways owned by the player.
     * @return The list of owned railways.
     */
    public ArrayList<RailwayLocation> getOwnedRailways() {
        ArrayList<RailwayLocation> owned = new ArrayList<>();
        
        ownedLocations.forEach(location -> {
            if(location instanceof RailwayLocation) {
                owned.add((RailwayLocation) location);
            }
        });
        
        return owned;
    }
    
    /**
     * Checks whether the player owns any Get Out of Jail Free cards. Exactly
     * equivalent to an isEmpty() ArrayList call.
     * @return True if the player owns any GOJFCards, false otherwise.
     */
    public boolean hasGOJFCard() {
        return !ownedGOJFCards.isEmpty();
    }
    
    /**
     * Retrieves the has GOJFCard property.
     * @return The has GOJFCard property
     */
    public SimpleBooleanProperty getHasGOJFCardProperty() {
        return hasGOJFCard;
    }
    
    /**
     * Retrieves the first GOJFCard in the list, if one is available. Else returns
     * null.
     * @return The Get Out Of Jail Free card.
     */
    public GOJFCard getGOJFCard() {
        if(hasGOJFCard()) {
            return ownedGOJFCards.get(0);
        }
        else {
            return null;
        }
    }
    
    /**
     * Adds a Get Out of Jail Free card to the player's owned list.
     * @param card The card to be added.
     */
    public void addGOJFCard(GOJFCard card) {
        ownedGOJFCards.add(card);
        hasGOJFCard.setValue(true);
    }
    
    /**
     * Removes the first Get Out of Jail Free card in the player's owned list, if
     * the player owns any and returns it. Null if the player does not own a card.
     * @return The first GOJFCard owned, if none owned then null.
     */
    public GOJFCard removeGOJFCard() {
        if(!hasGOJFCard()) {
            return null;
        }
        else {
            GOJFCard card = ownedGOJFCards.get(0);
            ownedGOJFCards.remove(0);
            
            if(ownedGOJFCards.isEmpty())
                hasGOJFCard.setValue(false);
            
            return card;
        }
    }
    
    /**
     * Retrieves the player's current location
     * @return The player's location.
     */
    public int getPosition() {
        return boardPosition.getValue();
    }
    
    /**
     * Retrieves the player position property.
     * @return The position property.
     */
    public SimpleIntegerProperty getPositionProperty() {
        return boardPosition;
    }
    
    /**
     * Moves the player's board position by the specified distance. The maths at
     * play here is fine for regular Monopoly, where the largest move in a single 
     * turn is 35, although in MegaMonopoly for example this would need to be 
     * changed.
     * <p>
     * Resets the board position when above 39 or below 0. 
     * @param distance The distance to move by.
     */
    public void moveBy(int distance) {
        if((boardPosition.getValue() + distance) > 39) {
            boardPosition.setValue((boardPosition.getValue() + distance) - 40);
        }
        else if((boardPosition.getValue() + distance) < 0) {
            // Adding 39 only moves to the location before the intended one
            boardPosition.setValue((boardPosition.getValue() + distance) + 40);
        }
        else {
            boardPosition.setValue(boardPosition.getValue() + distance);
        }
    }
    
    /**
     * Moves the player's board position to the specified position. Must be in 
     * the range of 0 to 39 inclusive.
     * @param location The location to move to.
     */
    public void moveTo(int location) {
        if(location >= 0 && location <= 39) {
            boardPosition.setValue(location);
        }
    }
    
    /**
     * Retrieves the jail status of the player.
     * @return True if in jail, false otherwise.
     */
    public boolean isInJail() {
        return inJail.getValue();
    }
    
    /**
     * Retrieves the in jail property.
     * @return The in jail property.
     */
    public SimpleBooleanProperty getIsInJailProperty() {
        return inJail;
    }
    
    /**
     * Sets the inJail boolean to true.
     */
    public void enterJail() {
        if(!inJail.getValue()) {
            inJail.setValue(true);
        }
    }
    
    /**
     * Sets the inJail boolean to false.
     */
    public void exitJail() {
        if(inJail.getValue()) {
            inJail.setValue(false);
            turnsInJail = 0;
        }
    }
    
    /**
     * Adds cash to the player's total. Negative additions result in a subtraction.
     * @param amount The amount to be added.
     */
    public void addCash(int amount) {
        cash.setValue(cash.getValue() + amount);
    }
    
    /**
     * Retrieves the cash held by this player.
     * @return The cash this player has.
     */
    public int getCash() {
        return cash.getValue();
    }
    
    /**
     * Retrieves the cash property.
     * @return The cash property.
     */
    public SimpleIntegerProperty getCashProperty() {
        return cash;
    }
    
    /**
     * Sets the can roll boolean value to the specified input value.
     * @param status Whether the player can roll the die or not.
     */
    public void setCanRoll(boolean status) {
        canRoll.setValue(status);
    }
    
    /**
     * Retrieves the can roll status.
     * @return True if the player can roll, false otherwise.
     */
    public boolean getCanRoll() {
        return canRoll.getValue();
    }
    
    /**
     * Retrieves the can roll property.
     * @return The can roll property.
     */
    public SimpleBooleanProperty getCanRollProperty() {
        return canRoll;
    }
    
    /**
     * Adds another turn in jail.
     */
    public void anotherTurnInJail() {
        turnsInJail++;
    }
    
    /**
     * Retrieves the turns spent in jail.
     * @return The number of turns spent in jail.
     */
    public int getTurnsInjail() {
        return turnsInJail;
    }
    
    /**
     * Retrieves the full player worth based on locations, cash and GOJFCards held.
     * @return The player worth.
     */
    public int getWorth() {
        int worth = 0;
        
        for(PropertyLocation property : getOwnedProperty()) {
            if(property.getMortgagedStatus()) {
                worth += property.getPrice() /2;
            }
            else if(property.getIsHotel()) {
                worth += (property.getPrice() + (5 * property.getHousePrice()));
            }
            else {
                worth += (property.getPrice() + (property.getHousePrice() * property.getNumberOfHouses()));
            }
        }
        
        for(RailwayLocation railway : getOwnedRailways()) {
            if(railway.getIsMortgaged()) {
                worth += railway.getPrice() / 2;
            }
            else {
                worth += railway.getPrice();
            }
        }
        
        for(UtilityLocation utility : getOwnedUtilities()) {
            if(utility.getIsMortgaged()) {
                worth += utility.getPrice() / 2;
            }
            else {
                worth += utility.getPrice();
            }
        }
        
        worth += cash.getValue();
        
        worth += 50 * ownedGOJFCards.size();
        
        return worth;
    }
}
