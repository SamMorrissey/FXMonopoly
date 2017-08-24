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
    
    /**
     * Creates a Nearest Utility card, which as the name suggests is intended to
     * cause an action that advances the player to the next utility.
     * @param description The card description.
     * @param fromChanceDeck True if from the chance deck, false otherwise.
     */
    public NearestUtilityCard(String description, boolean fromChanceDeck) {
        super(description, fromChanceDeck);
        multiplier = 10;
    }
    
    /**
     * Retrieves the rent multiplier applicable when the utility is owned by 
     * another player.
     * @return The rent multiplier.
     */
    public int getMultiplier() {
        return multiplier;
    }
    
}
