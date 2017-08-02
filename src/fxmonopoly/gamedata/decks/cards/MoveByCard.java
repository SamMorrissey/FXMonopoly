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
    
    public MoveByCard(String description, int distance) {
        super(description);
        this.distance = distance;
    }
    
    public int getDistance() {
        return distance;
    }
}
