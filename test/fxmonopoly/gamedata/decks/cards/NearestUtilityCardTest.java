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
public class NearestUtilityCardTest {
    
    NearestUtilityCard near = new NearestUtilityCard("Testing");
    
    @Test
    public void testMultiplier() {
        assertEquals(10, near.getMultiplier());
    }
    
    @Test
    public void testDescription() {
        assertEquals("Testing", near.getDescription());
    }
}
