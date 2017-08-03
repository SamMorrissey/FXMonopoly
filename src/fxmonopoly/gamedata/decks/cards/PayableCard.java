/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks.cards;


/**
 * Defines a class of Community Chest Card that either have fines, or payouts,
 * where the value is simply added to a player's total, with negative value 
 * indicating a fine, and positive value indicating a payout.
 * @author Sam P. Morrissey
 */
public class PayableCard extends Card {
    
    private final int value;
    private final boolean perPlayer;
    
    /**
     * Creates a Payable card that specifies a single value (positive or negative)
     * that must be paid, as well as a boolean determining whether this value is
     * per player or not.
     * @param description The card description.
     * @param value The value specified by the card.
     * @param perPlayer Whether the value is per player in the game.
     */
    public PayableCard(String description, int value, boolean perPlayer) {
        super(description);
        this.value = value;
        this.perPlayer = perPlayer;
    }
    
    /**
     * Retrieves the value specified by the card.
     * @return The value specified by the card.
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Retrieves the boolean determining whether the specified value is per 
     * player currently in the game or not.
     * @return True if the value is per player, false otherwise.
     */
    public boolean getPerPlayer() {
        return perPlayer;
    }
}
