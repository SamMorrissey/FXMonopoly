/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks;

import fxmonopoly.gamedata.decks.cards.Card;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Slipshod
 */
public class CommunityChestDeckTest {

    CommunityChestDeck deck = new CommunityChestDeck();
    Card a;

    @Test
    public void testGetNextCard() {
        a = deck.getNextCard();
        assertNotNull(a);
    }
}
