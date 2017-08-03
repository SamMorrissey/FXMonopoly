/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

/**
 * Defines the Go location. Does not contain a method for adding cash to a player,
 * since the purpose of this class is to cause an action when parsed via the
 * instanceof operator.
 * @author Sam P. Morrissey
 */
public class GoLocation extends Location {
    
    private final int value;
    
    /**
     * Creates a Go location.
     * @param name The name of this location.
     * @param value The cash value of entering the location.
     */
    public GoLocation(String name, int value) {
        super(name);
        
        if(value < 0) {
            int i = value + (value * -2);
            this.value = i;
        }
        else {
            this.value = value;
        }
    }
    
    /**
     * Returns the cash value of the Go location.
     * @return The cash value of the location.
     */
    public int getValue() {
        return value;
    }
}
