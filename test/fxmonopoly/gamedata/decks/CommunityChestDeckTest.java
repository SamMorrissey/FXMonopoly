/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks;

import fxmonopoly.gamedata.decks.cards.Card;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the Community Chest Deck class.
 * <p>
 * Only one test is provided due to the nature of avoiding unnecessary internal
 * representation exposure. However four tests were part of the initial testing,
 * which can be seen in the "Automated Testing" document available in the GitHub 
 * wiki. 
 * <p>
 * There are two images specifying the code of both the tests and the methods
 * necessary within the class being tested.
 * @author Sam P. Morrissey
 */
public class CommunityChestDeckTest {

    CommunityChestDeck deck = new CommunityChestDeck();
    Card a;

    /**
     * Tests that the next card is not a null reference.
     */
    @Test
    public void testGetNextCard() {
        a = deck.getNextCard();
        assertNotNull(a);
    }
}
