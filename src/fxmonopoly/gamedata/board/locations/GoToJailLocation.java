/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

/**
 * Defines a Go To Jail location. Does not contain a method for moving a player
 * to Jail itself since the purpose of this class is for parsing the applicable
 * action utilising the instanceof operator.
 * @author Sam P. Morrissey
 */
public class GoToJailLocation extends Location {
    
    private final int jailPosition;
    
    /**
     * Creates a new Go To Jail location.
     * @param name The name of the location
     * @param jailPosition The board position of the Jail location
     */
    public GoToJailLocation(String name, int jailPosition) {
        super(name);
        this.jailPosition = jailPosition;
    }
    
    /**
     * Returns the board position of the Jail location. 
     * @return The board position of Jail.
     */
    public int getJailPosition() {
        return jailPosition;
    }
}
