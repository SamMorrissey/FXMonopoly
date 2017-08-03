/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks.cards;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the Double Payable card class.
 * @author Sam P. Morrissey
 */
public class DoublePayableCardTest {
    
    DoublePayableCard newCard = new DoublePayableCard("Testing", 40, 100);
    
    /**
     * Tests that the specified first value and the retrieved first value are
     * equivalent.
     */
    @Test
    public void testFirstValue() {
        assertEquals(40, newCard.getFirstValue());
    }
    
    /**
     * Tests that the specified second value and the retrieved second value are
     * equivalent.
     */
    @Test
    public void testSecondValue() {
        assertEquals(100, newCard.getSecondValue());
    }
    
    /**
     * Tests that the specified description and the retrieved description are
     * equivalent.
     */
    @Test
    public void testDescription() {
        assertEquals("Testing", newCard.getDescription());
    }
    
}
