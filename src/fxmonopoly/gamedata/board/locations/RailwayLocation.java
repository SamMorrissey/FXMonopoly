/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

import fxmonopoly.gamedata.players.Player;
import javafx.beans.property.SimpleObjectProperty;


/**
 * Defines the Railway location class. Provides multipliers for the rent value,
 * but does not implement a method to charge rent. The purpose is the same as all
 * other location classes, to be parsed via the instanceof operator.
 * @author Sam P. Morrissey
 */
public class RailwayLocation extends Location {
    
    private final int baseRent;
    private final int price;
    
    private boolean isOwned;
    private boolean isMortgaged;
    private final SimpleObjectProperty<Player> owner;
    
    /**
     * Creates a new Railway location object with the specified name, and 
     * instantiates the base rent.
     * @param name The name of this location.
     */
    public RailwayLocation(String name) {
        super(name);
        
        price = 200;
        baseRent = 25;
        
        owner = new SimpleObjectProperty(this, "owner", null);
    }
    
    /**
     * Retrieves the price of this location.
     * @return The price of the location.
     */
    public int getPrice() {
        return price;
    }
    
    public int getBaseRent() {
        return baseRent;
    }
    
    /**
     * Determines the multiplier to be placed on the base rent. This method could
     * utilise a method specified in the player class (getOwnedRailways) however
     * this would increase coupling by having each class rely on the implementation
     * of each other. Thus specifying an integer is the preferred approach.
     * @param owned The number of Railways owned.
     * @return The multiplier for the base rent.
     */
    public int getRentMultiplier(int owned) {
        int multiplier;
        
        switch (owned) {
            case 1:
                multiplier = 1;
                break;
            case 2:
                multiplier = 2;
                break;
            case 3:
                multiplier = 4;
                break;
            case 4:
                multiplier = 8;
                break;
            default:
                multiplier = 0;
                break;
        }
        
        
        
        return multiplier;
    }
    
    /**
     * Retrieves the boolean ownership status of this location.
     * @return True if owned, false otherwise.
     */
    public boolean getIsOwned() {
        return isOwned;
    }
    
    /**
     * Sets the ownership status based on the input parameter.
     * @param status The ownership status of this location.
     */
    private void setIsOwned(boolean status) {
        isOwned = status;
    }
    
    /**
     * Retrieves the owner of this location.
     * @return The location owner.
     */
    public Player getOwner() {
        return owner.getValue();
    }
    
    /**
     * Retrieves the owner (JavaBeans) property of this railway.
     * @return The owner property.
     */
    public SimpleObjectProperty getOwnerProperty() {
        return owner;
    }
    
    /**
     * Sets the owner of the location based on the input parameter.
     * @param player The player to own this location.
     */
    public void setOwner(Player player) {
        owner.setValue(player);
        if(owner.getValue() != null)
            setIsOwned(true);
        else
            setIsOwned(false);
    }
    
    /**
     * Removes the current ownership (both the owner and the isOwned boolean).
     */
    public void removeOwner() {
        owner.setValue(null);
        setIsOwned(false);
    }
    
    /**
     * Sets the mortgaged status to the specified input.
     * @param status The mortgaged status.
     */
    public void setIsMortgaged(boolean status) {
        isMortgaged = status;
    }
    
    /**
     * Retrieves the mortgaged status of this location.
     * @return True if mortgaged, false otherwise.
     */
    public boolean getIsMortgaged() {
        return isMortgaged;
    }
    
}
