/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

import fxmonopoly.gamedata.players.Player;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Defines the Utility location class. Contains multipliers for the rent values,
 * however the rent is to be determined by the game itself, not within this class.
 * Like the other location classes, the purpose is to be parsed via the instanceof
 * operator, whilst containing relevant information.
 * @author Sam P. Morrissey
 */
public class UtilityLocation extends BaseOwnableLocation {
    
    private final int singleMultiplier;
    private final int doubleMultiplier;
    
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
     * Retrieves the String providing information to the user owner of the property.
     * @return The text String.
     */
    public String getUserOwnedString() {
        return "Rent is " + singleMultiplier + " multiplied by the dice \n" +
               "roll that landed a player on this property, if the owner \n" +
               "only has a single Utility, if the owner has both, the \n" +
               "multiplier is " + doubleMultiplier + ".";
    }
}
