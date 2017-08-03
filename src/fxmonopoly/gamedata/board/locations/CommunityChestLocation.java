/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

/**
 * Defines the Community Chest location. Only purpose is for parsing location
 * action by utilising the instanceof operator rather than comparing name strings.
 * @author Sam P. Morrissey
 */
public class CommunityChestLocation extends Location {
    
    /**
     * Creates a new Community Chest location.
     * @param name The name of this location.
     */
    public CommunityChestLocation(String name) {
        super(name);
    }
}
