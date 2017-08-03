/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

/**
 * Defines an abstract class for all board locations to utilise, with common
 * methods and variables provided.
 * @author Sam P. Morrissey
 */
public abstract class Location {
    private final String name;
    
    /**
     * Defines a generic Location object.
     * @param name The name of the location.
     */
    public Location(String name) {
        this.name = name;
    }
    
    /**
     * Retrieves the name of the created location.
     * @return The name of the location.
     */
    public String getName() {
        return name;
    }
}
