/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks.cards;

/**
 * Details a Community Chest Card that specifies that the player move to a
 * determined location on the board. 
 * @author Sam P. Morrissey
 */
public class MoveToCard extends Card{
    private final int moveLocation;
    
    /**
     * Creates a card that designates a specific board location for the player
     * to move to.
     * @param description The card description.
     * @param location The board position to be moved to.
     */
    public MoveToCard(String description, boolean fromChanceDeck, int location) {
        super(description, fromChanceDeck);
        if(location > 39) {
            this.moveLocation = 39;
        }
        else if(location < 0) {
            this.moveLocation = 0;
        }
        else {
            this.moveLocation = location;
        }
    }
    
    /**
     * Retrieves the location to be moved to.
     * @return The location to be moved to.
     */
    public int getMoveLocation() {
        return moveLocation;
    }
}
