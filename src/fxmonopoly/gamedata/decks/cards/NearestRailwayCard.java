/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks.cards;

/**
 * Defines a card that requires the recipient to advance to the next railway,
 * with a 2x multiplier on any payable rent.
 * @author Sam P. Morrissey
 */
public class NearestRailwayCard extends Card {
    
    private final int multiplier;
    
    public NearestRailwayCard(String description) {
        super(description);
        multiplier = 2;
    }
    
    public int getMultiplier() {
        return multiplier;
    }
}
