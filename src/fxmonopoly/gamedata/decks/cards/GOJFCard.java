/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks.cards;


/**
 * Defines the Get Out Of Jail Free card, which has no methods since it does
 * not act on the game itself, rather its type is parsed and the action taken.
 * @author Sam P. Morrissey
 */
public class GOJFCard extends Card {
    
    /**
     * Creates a Get Out Of Jail Free card.
     * @param fromChanceDeck True if from the chance deck, false otherwise.
     */
    public GOJFCard(boolean fromChanceDeck) {
        super("Get Out Of Jail Free", fromChanceDeck);
    }
}
