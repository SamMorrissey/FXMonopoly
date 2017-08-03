/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

/**
 * Defines a Tax location. Does not contain a method for adding the value to a 
 * player's cash, since the purpose of this class is for its action to be parsed
 * via the instanceof operator.
 * @author Sam P. Morrissey
 */
public class TaxLocation extends Location {
    
    private final int value;
    
    /**
     * Creates a Tax location. Will invert any number above the value of 0 to its
     * negative form.
     * @param name The name of this location.
     * @param value The value of the tax at this location.
     */
    public TaxLocation(String name, int value) {
        super(name);
        
        if(value > 0) {
            int i = value - (2 * value);
            this.value = i;
        }
        else {
            this.value = value;
        }
    }
    
    /**
     * Returns the taxation value of this location.
     * @return The tax value of this location.
     */
    public int getValue() {
        return value;
    }
}
