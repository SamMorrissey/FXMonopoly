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
public class MoveToCardTest {
    
    MoveToCard move = new MoveToCard("Testing", 41);
    MoveToCard move2 = new MoveToCard("Testing", 39);
    
    MoveToCard move3 = new MoveToCard("Testing", -3);
    MoveToCard move4 = new MoveToCard("Testing", 0);
    
    MoveToCard move5 = new MoveToCard("Testing", 22);
    
    @Test
    public void testMaximumPosition() {
        assertEquals(move.getMoveLocation(), move2.getMoveLocation());
    }
    
    @Test
    public void testMinimumPosition() {
        assertEquals(move3.getMoveLocation(), move4.getMoveLocation());
    }
    
    @Test
    public void testPosition() {
        assertEquals(22, move5.getMoveLocation());
    }
    
    @Test
    public void testDescription() {
        assertEquals(move.getDescription(), move2.getDescription());
    }
}
