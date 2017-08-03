/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks.cards;


/**
 * Defines a Card which has two values to be used as multipliers when processed.
 * Currently only two cards need to implement this class, and both those cards
 * utilise firstValue against the number of Houses a player owns, and secondValue
 * against the number of Hotels a player owns.
 * @author Sam P. Morrissey
 */
public class DoublePayableCard extends Card{
    
    private final int firstValue;
    private final int secondValue;
    
    /**
     * Creates a Double Payable Card, which defines two specific values to be 
     * utilised against specific multipliers (Houses and Hotels in the current
     * Monopoly game).
     * @param description The card description.
     * @param firstValue The first value mentioned on the card.
     * @param secondValue The second value mentioned on the card.
     */
    public DoublePayableCard(String description, int firstValue, int secondValue) {
        super(description);
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }
    
    /**
     * Retrieves the first value on the card.
     * @return The first value on the card.
     */
    public int getFirstValue() {
        return firstValue;
    }
    
    /**
     * Retrieves the second value on the card.
     * @return The second value on the card.
     */
    public int getSecondValue() {
        return secondValue;
    }
}
