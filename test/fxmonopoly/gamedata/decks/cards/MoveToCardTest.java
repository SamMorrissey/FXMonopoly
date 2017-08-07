/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks.cards;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the Move To card class.
 * @author Sam P. Morrissey
 */
public class MoveToCardTest {
    
    // Used for testing that the board position upper bound works correctly
    MoveToCard move = new MoveToCard("Testing", 40);
    MoveToCard move2 = new MoveToCard("Testing", 39);

    // Used for testing that the board position lower bound works correctly
    MoveToCard move3 = new MoveToCard("Testing", -1);
    MoveToCard move4 = new MoveToCard("Testing", 0);
    
    MoveToCard move5 = new MoveToCard("Testing", 22);
    
    /**
     * Tests that the upper bound condition is effective.
     */
    @Test
    public void testMaximumPosition() {
        assertEquals(move.getMoveLocation(), move2.getMoveLocation());
    }
    
    /**
     * Tests that the lower bound condition is effective.
     */
    @Test
    public void testMinimumPosition() {
        assertEquals(move3.getMoveLocation(), move4.getMoveLocation());
    }
    
    /**
     * Tests that a value inbetween the upper and lower bounds returns a value
     * equal to the one originally specified.
     */
    @Test
    public void testPosition() {
        assertEquals(22, move5.getMoveLocation());
    }
    
    /**
     * Tests that the specified and retrieved descriptions are equivalent.
     */
    @Test
    public void testDescription() {
        assertEquals(move.getDescription(), move2.getDescription());
    }
}
