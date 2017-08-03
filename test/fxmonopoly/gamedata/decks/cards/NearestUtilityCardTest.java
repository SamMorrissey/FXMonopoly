/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks.cards;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the Nearest Utility card class.
 * @author Sam P. Morrissey
 */
public class NearestUtilityCardTest {
    
    NearestUtilityCard near = new NearestUtilityCard("Testing");
    
    /**
     * Tests that the default multiplier is the one returned.
     */
    @Test
    public void testMultiplier() {
        assertEquals(10, near.getMultiplier());
    }
    
    /**
     * Tests that the specified and retrieved descriptions are equivalent.
     */
    @Test
    public void testDescription() {
        assertEquals("Testing", near.getDescription());
    }
}
