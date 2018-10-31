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
public class RailwayLocation extends BaseOwnableLocation {
    
    /**
     * Creates a new Railway location object with the specified name, and 
     * instantiates the base rent.
     * @param name The name of this location.
     */
    public RailwayLocation(String name) {
        super(name);
        
        price = 200;
        baseRent = 25;
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
     * Retrieves the String providing information to the user owner of the property.
     * @return The text String.
     */
    public String getUserOwnedString() {
        return "Rent is " + baseRent + ", doubled for each Railway owned.";
    }
    
}
