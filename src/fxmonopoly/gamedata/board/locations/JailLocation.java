/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

/**
 * Defines the Jail location. Does not contain a method for placing a Player in 
 * jail, since the purpose of this class is to have the action enacted when parsed
 * via the instanceof operator.
 * @author Sam P. Morrissey
 */
public class JailLocation extends Location {
    
    /**
     * Creates a Jail location with the specified name.
     * @param name The name of this location.
     */
    public JailLocation(String name) {
        super(name);
    }
}
