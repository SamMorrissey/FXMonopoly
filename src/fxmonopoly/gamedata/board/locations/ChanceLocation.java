/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

/**
 * Defines the Chance location. Only purpose is for parsing location actions
 * by utilising the instanceof operator rather than comparing name strings.
 * @author Sam P. Morrissey
 */
public class ChanceLocation extends Location {
    
    /**
     * Creates a new Chance location.
     * @param name The name of this location.
     */
    public ChanceLocation(String name) {
        super(name);
    }
}
