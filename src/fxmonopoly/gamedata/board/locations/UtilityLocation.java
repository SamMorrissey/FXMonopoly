/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

import fxmonopoly.gamedata.players.Player;

/**
 * Defines the Utility location class. Contains multipliers for the rent values,
 * however the rent is to be determined by the game itself, not within this class.
 * Like the other location classes, the purpose is to be parsed via the instanceof
 * operator, whilst containing relevant information.
 * @author Sam P. Morrissey
 */
public class UtilityLocation extends Location {
    
    private final int singleMultiplier;
    private final int doubleMultiplier;
    private final int price;
    
    private boolean isOwned;
    private Player owner;
    
    /**
     * Creates a Utility location object, with the specified name. Instantiates
     * the default values of the multipliers too.
     * @param name The name of the location.
     */
    public UtilityLocation(String name) {
        super(name);
        
        price = 150;
        singleMultiplier = 4;
        doubleMultiplier = 10;
    }
    
    /**
     * Retrieves the price of this location.
     * @return The price of the location.
     */
    public int getPrice() {
        return price;
    }
    
    /**
     * Retrieves the value of the single Utility ownership rent multiplier.
     * @return The single ownership multiplier.
     */
    public int getSingleMultiplier() {
        return singleMultiplier;
    }
    
    /**
     * Retrieves the value of the double Utility ownership rent multiplier.
     * @return The double ownership multiplier.
     */
    public int getDoubleMultiplier() {
        return doubleMultiplier;
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
        return owner;
    }
    
    /**
     * Sets the owner of the location based on the input parameter.
     * @param player The player to own this location.
     */
    public void setOwner(Player player) {
        owner = player;
        setIsOwned(true);
    }
    
    /**
     * Removes the current ownership (both the owner and the isOwned boolean).
     */
    public void removeOwner() {
        owner = null;
        setIsOwned(false);
    }
    
}
