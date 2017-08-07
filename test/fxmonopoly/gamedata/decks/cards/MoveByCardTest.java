/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks.cards;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the Move By card class.
 * @author Sam P. Morrissey
 */
public class MoveByCardTest {
    
    MoveByCard move = new MoveByCard("Testing", -3);
    
    /**
     * Tests that the specified distance and retrieved distance are equivalent.
     */
    @Test
    public void testDistance() {
        assertEquals(-3, move.getDistance());
    }
    
    /**
     * Tests that the specified description and retrieved description are 
     * equivalent.
     */
    @Test
    public void testDescription() {
        assertEquals("Testing", move.getDescription());
    }
    
}
