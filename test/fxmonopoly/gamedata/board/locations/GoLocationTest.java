/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the Go location class.
 * @author Sam P. Morrissey
 */
public class GoLocationTest {
    
    GoLocation go = new GoLocation("Testing", -200);
    
    /**
     * Ensures that the Go location will not allow negative value assignments, and
     * any attempt to do so results in the positive form being implemented instead.
     */
    @Test
    public void testValueRevert() {
        assertEquals(200, go.getValue());
    }
    
    /**
     * Ensures that the specified name and the retrieved name are equivalent.
     */
    @Test
    public void testGetName() {
        assertEquals("Testing", go.getName());
    }
    
}
