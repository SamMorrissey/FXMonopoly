/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks.cards;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the Nearest Railway card class.
 * @author Sam P. Morrissey
 */
public class NearestRailwayCardTest {
    
    NearestRailwayCard near = new NearestRailwayCard("Testing");
    
    /**
     * Tests that the default multiplier is the one retrieved.
     */
    @Test
    public void testMultiplier() {
        assertEquals(2, near.getMultiplier());
    }
    
    /**
     * Tests that the default multiplier is the one retrieved.
     */
    @Test
    public void testDescription() {
        assertEquals("Testing", near.getDescription());
    }
}
