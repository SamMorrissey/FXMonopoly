/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks.cards;

/**
 * Defines a card that moves the recipient to the next Utility location on the board,
 * with owned locations having a 10x multiplier on the applicable rent.
 * @author Sam P. Morrissey
 */
public class NearestUtilityCard extends Card {
    
    private final int multiplier;
    
    public NearestUtilityCard(String description) {
        super(description);
        multiplier = 10;
    }
    
    public int getMultiplier() {
        return multiplier;
    }
    
}
