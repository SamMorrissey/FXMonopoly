/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks.cards;

/**
 * Defines a card that does not specify a board position to move to, merely the
 * distance to move by.
 * @author Sam P. Morrissey
 */
public class MoveByCard extends Card {
    private final int distance;
    
    /**
     * Creates a new MoveBy card, that specified a distance to move.
     * @param description The description of this card.
     * @param distance The distance to be moved.
     */
    public MoveByCard(String description, boolean fromChanceDeck, int distance) {
        super(description, fromChanceDeck);
        this.distance = distance;
    }
    
    /**
     * Returns the distance to be moved.
     * @return The distance to be moved.
     */
    public int getDistance() {
        return distance;
    }
}
