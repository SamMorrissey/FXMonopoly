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
    
    public PayableCard(String description, int value, boolean perPlayer) {
        super(description);
        this.value = value;
        this.perPlayer = perPlayer;
    }
    
    public int getValue() {
        return value;
    }
    
    public boolean getPerPlayer() {
        return perPlayer;
    }
}
