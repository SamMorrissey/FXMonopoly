/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks.cards;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sam P. Morrissey
 */
public class DoublePayableCardTest {
    
    DoublePayableCard newCard = new DoublePayableCard("Testing", 40, 100);
    
    @Test
    public void testFirstValue() {
        assertEquals(40, newCard.getFirstValue());
    }
    
    @Test
    public void testSecondValue() {
        assertEquals(100, newCard.getSecondValue());
    }
    
    @Test
    public void testDescription() {
        assertEquals("Testing", newCard.getDescription());
    }
    
}
