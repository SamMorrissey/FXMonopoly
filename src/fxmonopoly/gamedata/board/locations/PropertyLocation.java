/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

import fxmonopoly.gamedata.players.Player;

/**
 * Defines the Property location class.
 * <p>
 * The boolean statuses with setters are meant to be parsed by the Board, which
 * can manipulate colour groups, since the colour monopolies and mortgaged statuses
 * of any property in a colour group affects all the others. However colour groups
 * are a higher level data structure, that should be manipulated elsewhere.
 * <p>
 * Like all other location classes, the rent/purchase/mortgage values are not added
 * to a player 
 * @author Sam P. Morrissey
 */
public class PropertyLocation extends Location{
    
    private final int price;
    private final int mortgageValue;
    private final int housePrice;
    
    private final int baseRent;
    private final int oneHouseRent;
    private final int twoHouseRent;
    private final int threeHouseRent;
    private final int fourHouseRent;
    private final int hotelRent;
    
    private int numberOfHouses;
    private boolean upgradeableToHotel;
    private boolean isHotel;
    
    private boolean isDevelopable;
    private boolean isOwned;
    private boolean isMortgaged;
    private boolean inColourMonopoly;
    
    private Player owner;
    
    /**
     * Creates a Property location utilising the name, price, rent value array and
     * house price values provided. Mortgage value is always equal to half the 
     * price, and all booleans are initialised as false.
     * @param name The name of the property.
     * @param price The price of the property.
     * @param rents The rent value array of the property.
     * @param housePrice The cost per house or hotel on this property.
     */
    public PropertyLocation(String name, int price, int[] rents, int housePrice) {
        super(name);
        
        this.price = price;
        mortgageValue = price / 2;
        
        baseRent = rents[0];
        oneHouseRent = rents[1];
        twoHouseRent = rents[2];
        threeHouseRent = rents[3];
        fourHouseRent = rents[4];
        hotelRent = rents[5];
        
        numberOfHouses = 0;
        upgradeableToHotel = false;
        isHotel = false;
        
        isDevelopable = false;
        isOwned = false;
        isMortgaged = false;
        inColourMonopoly = false;
        
        this.housePrice = housePrice;
    }
    
    /**
     * Retrieves the price to purchase this property.
     * @return The price of this property.
     */
    public int getPrice() {
        return price;
    }
    
    /**
     * Retrieves the mortgage value of this property. Always half the price.
     * @return The mortgage value of this property.
     */
    public int getMortgageValue() {
        return mortgageValue;
    }
    
    /**
     * Sets the mortgaged status of this property. Automatically sets the 
     * isDevelopable boolean to false if the input parameter is true.
     * @param mortgaged True if to be mortgaged, false otherwise.
     */
    public void setMortgaged(boolean mortgaged) {
        isMortgaged = mortgaged;
        if(isMortgaged) {
            isDevelopable = false;
        }
    }
    
    /**
     * Retrieves the mortgaged status.
     * @return The mortgaged status.
     */
    public boolean getMortgagedStatus() {
        return isMortgaged;
    }
    /**
     * Gets the applicable rent value, including double rent on any undeveloped
     * property that is part of a colour group monopoly. Naturally this requires
     * that the inColourMonopoly boolean has been set correctly.
     * @return The rent value of this property.
     */
    public int getRent() {
        int i = 0;
        
        if(numberOfHouses == 0 && inColourMonopoly == false) {
            i = baseRent;
        }
        else if(numberOfHouses == 0 && inColourMonopoly == true && !isHotel) {
            i = baseRent * 2;
        }
        else if(numberOfHouses == 1) {
            i = oneHouseRent;
        }
        else if(numberOfHouses == 2) {
            i = twoHouseRent;
        }
        else if(numberOfHouses == 3) {
            i = threeHouseRent;
        }
        else if(numberOfHouses == 4) {
            i = fourHouseRent;
        }
        else if(isHotel){
            i = hotelRent;
        }
        
        return i;
    }
    
    /**
     * Adds the specified number of houses to this property. Up to a maximum of four
     * total on this property, or down to a minimum of 0. Any above four or below zero
     * will "disappear".
     * @param houses The houses to be added.
     */
    public void addHouses(int houses) {
        numberOfHouses += houses;
        if(numberOfHouses > 4) {
            numberOfHouses = 4;
        }
        else if(numberOfHouses < 0) {
            numberOfHouses = 0;
        }
    }
    
    /**
     * Retrieves the number of houses on this property.
     * @return The number of houses on this property.
     */
    public int getNumberOfHouses() {
        return numberOfHouses;
    }
    
    /**
     * Retrieves the boolean value of whether this property is currently a hotel.
     * @return True if a hotel, false otherwise.
     */
    public boolean getIsHotel() {
        return isHotel;
    }
    
    /**
     * Retrieves the value of the upgradeable to hotel boolean.
     * @return True is upgradeable to hotel, false otherwise.
     */
    public boolean getUpgradeableToHotel() {
        return upgradeableToHotel;
    }
    
    /**
     * Sets the upgradeableToHotel status to match the input. If the input is true,
     * the numberOfHouses must have been set to 4 prior, otherwise this method
     * will do nothing. No such constraints on false.
     * @param upgradeable The upgradeableToHotel status of this property.
     */
    public void setUpgradeableToHotel(boolean upgradeable) {
        if(numberOfHouses == 4 && upgradeable) {
            upgradeableToHotel = upgradeable;
        }
        else if(!upgradeable) {
            upgradeableToHotel = upgradeable;
        }
    }
    
    /**
     * Upgrades the property to a hotel, as long as upgradeableToHotel is true,
     * and numberOfHouses is 4, otherwise does nothing.
     */
    public void upgradeToHotel() {
        if(upgradeableToHotel && numberOfHouses == 4) {
            isHotel = true;
            numberOfHouses = 0;
        }
    }
    
    /**
     * Downgrades the property from hotel, as long as it already was a hotel. This
     * includes setting the number of houses to 4. Otherwise this method does nothing.
     */
    public void downgradeFromHotel() {
        if(isHotel) {
            isHotel = false;
            numberOfHouses = 4;
        }
    }
    
    /**
     * Transfers the ownership of this property to the specified player.
     * @param player The player to transfer ownership to.
     */
    public void transferOwnership(Player player) {
        owner = player;
        isOwned = true;
    }
    
    /**
     * Resets the owner to a null value, and resets all booleans to false, i.e.
     * their initial state. Also checks to ensure numberOfHouses is 0, if not then
     * resets to 0. As such this can be used as a hard reset. 
     * <p>
     * These values should be set and unset properly before removing ownership,
     * but this is to guarantee the reset in full.
     */
    public void removeOwnership() {
        owner = null;
        isOwned = false;
        isDevelopable = false;
        upgradeableToHotel = false;
        isHotel = false;
        isMortgaged = false;
        inColourMonopoly = false;
        if(numberOfHouses != 0) {
            numberOfHouses = 0;
        }
    }
    
    /**
     * Retrieves the owner of this property.
     * @return The property owner.
     */
    public Player getOwner() {
        return owner;
    }
     
    /**
     * Sets the in colour monopoly status to the input parameter.
     * @param status True if part of a colour monopoly, false otherwise.
     */
    public void setInColourMonopolyStatus(boolean status) {
        inColourMonopoly = status;
    }
    
    /**
     * Retrieves the developable status of this property.
     * @return The developable status of this property.
     */
    public boolean getDevelopableStatus() {
        return isDevelopable;
    }
    
    /**
     * Sets the developable status of this property to match the input parameter.
     * @param status True if developable, false otherwise.
     */
    public void setDevelopableStatus(boolean status) {
        isDevelopable = status;
    }
}
