/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks.cards;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the Payable card class.
 * @author Sam P. Morrissey
 */
public class PayableCardTest {
    
    PayableCard pay = new PayableCard("Testing", false, -200, false);
    
    /**
     * Tests that the specified and retrieved values are equivalent.
     */
    @Test
    public void testValue() {
        assertEquals(-200, pay.getValue());
    }
    
    /**
     * Tests that the specified and retrieved values are equivalent.
     */
    @Test
    public void testDescription() {
        assertEquals("Testing", pay.getDescription());
    }
    
    /**
     * Tests that the specified and retrieved values are equivalent.
     */
    @Test
    public void testGetPerPlayer() {
        assertEquals(false, pay.getPerPlayer());
    }
    
    /**
     * Tests that the from chance deck boolean is retrieved as expected.
     */
    @Test
    public void testFromChanceDeck() {
        assertFalse(pay.getFromChanceDeck());
    }
}
