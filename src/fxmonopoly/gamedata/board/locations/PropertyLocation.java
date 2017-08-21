/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

import fxmonopoly.gamedata.players.Player;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

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
    
    private final SimpleIntegerProperty houses;
    private int numberOfHouses;
    private boolean upgradeableToHotel;
    private boolean isHotel;
    
    private boolean isDevelopable;
    private boolean isOwned;
    private boolean isMortgaged;
    private boolean inColourMonopoly;
    
    private final SimpleObjectProperty<Player> owner;
    
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
        
        houses = new SimpleIntegerProperty(this, "houses", 0);
        upgradeableToHotel = false;
        isHotel = false;
        
        isDevelopable = false;
        isOwned = false;
        isMortgaged = false;
        inColourMonopoly = false;
        
        owner = new SimpleObjectProperty();
        
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
     * isDevelopable boolean to false if the input parameter is true. Only sets
     * the status provided that the number of houses is 0, otherwise has no effect.
     * @param mortgaged True if to be mortgaged, false otherwise.
     */
    public void setMortgaged(boolean mortgaged) {
        if(numberOfHouses == 0) {
            isMortgaged = mortgaged;
            if(isMortgaged) {
                isDevelopable = false;
            }
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
        
        if(houses.getValue() == 0 && inColourMonopoly == false) {
            i = baseRent;
        }
        else if(houses.getValue() == 0 && inColourMonopoly == true && !isHotel) {
            i = baseRent * 2;
        }
        else if(houses.getValue() == 1) {
            i = oneHouseRent;
        }
        else if(houses.getValue() == 2) {
            i = twoHouseRent;
        }
        else if(houses.getValue() == 3) {
            i = threeHouseRent;
        }
        else if(houses.getValue() == 4) {
            i = fourHouseRent;
        }
        else if(isHotel){
            i = hotelRent;
        }
        
        return i;
    }
    
    /**
     * Adds a single house to this property provided that the property currently
     * has fewer than 4 houses already and is developable. If the property is 
     * upgradeable to a hotel then it will be converted to a hotel.
     */
    public void addHouse() {
        if(houses.getValue() < 4 && isDevelopable) 
            houses.setValue(houses.getValue() + 1);
        else if(houses.getValue() == 4 && upgradeableToHotel)
            upgradeToHotel();
    }
    
    /**
     * Removes a single house from this property provided that the property currently
     * is a hotel or has any houses on it. 
     */
    public void removeHouse() {
        if(houses.getValue() == 0 && isHotel) {
            downgradeFromHotel();
        }
        else if(houses.getValue() > 0)
            houses.setValue(houses.getValue() - 1);
    }
    
    /**
     * Retrieves the number of houses on this property.
     * @return The number of houses on this property.
     */
    public int getNumberOfHouses() {
        return houses.getValue();
    }
    
    /**
     * Retrieves the number of houses (JavaBeans) property utilised by this PropertyLocation
     * @return The number of houses property.
     */
    public SimpleIntegerProperty getHousesProperty() {
        return houses;
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
        if(houses.getValue() == 4 && upgradeable) {
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
    private void upgradeToHotel() {
        if(upgradeableToHotel && houses.getValue() == 4) {
            isHotel = true;
            houses.setValue(0);
            upgradeableToHotel = false;
        }
    }
    
    /**
     * Downgrades the property from hotel, as long as it already was a hotel. This
     * includes setting the number of houses to 4. Otherwise this method does nothing.
     */
    private void downgradeFromHotel() {
        if(isHotel) {
            isHotel = false;
            houses.setValue(4);
        }
    }
    
    /**
     * Transfers the ownership of this property to the specified player.
     * @param player The player to transfer ownership to.
     */
    public void transferOwnership(Player player) {
        owner.setValue(player);
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
        owner.setValue(null);
        isOwned = false;
        isDevelopable = false;
        upgradeableToHotel = false;
        isHotel = false;
        isMortgaged = false;
        inColourMonopoly = false;
        if(houses.getValue() != 0) {
            houses.setValue(0);
        }
    }
    
    /**
     * Retrieves the owner of this property.
     * @return The property owner.
     */
    public Player getOwner() {
        return owner.getValue();
    }
    
    /**
     * Retrieves the owner (JavaBeans) property of this property.
     * @return The owner property.
     */
    public SimpleObjectProperty<Player> getOwnerProperty() {
        return owner;
    }
    
    /**
     * Retrieves the colour monopoly status of this property.
     * @return The colour monopoly status of this property.
     */
    public boolean getIinColourMonopolyStatus() {
        return inColourMonopoly;
    }
    /**
     * Sets the in colour monopoly status to the input parameter.
     * @param status True if part of a colour monopoly, false otherwise.
     */
    public void setInColourMonopolyStatus(boolean status) {
        inColourMonopoly = status;
        if(!inColourMonopoly) {
            isDevelopable = false;
        }
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
    
    /**
     * Retrieves the house/hotel price for this location.
     * @return The price of each house/hotel.
     */
    public int getHousePrice() {
        return housePrice;
    }
    
    /**
     * Retrieves the is owned status of this property.
     * @return True if owned, false otherwise.
     */
    public boolean getIsOwned() {
        return isOwned;
    }
}
