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
public class MoveByCardTest {
    
    MoveByCard move = new MoveByCard("Testing", -3);
    
    @Test
    public void testDistance() {
        assertEquals(-3, move.getDistance());
    }
    
    @Test
    public void testDescription() {
        assertEquals("Testing", move.getDescription());
    }
    
}
