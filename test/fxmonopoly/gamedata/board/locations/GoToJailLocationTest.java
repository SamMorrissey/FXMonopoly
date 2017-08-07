/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the Go To Jail location.
 * @author Sam P. Morrissey
 */
public class GoToJailLocationTest {
    
    GoToJailLocation location = new GoToJailLocation("Testing", 10);
    
    /**
     * Tests that the specified location and the retrieved location are equivalent.
     */
    @Test
    public void testGetJailPosition() {
        assertEquals(10, location.getJailPosition());
    }
    
    /**
     * Tests that the specified name and the retrieved name are equivalent.
     */
    @Test
    public void testGetName() {
        assertEquals("Testing", location.getName());
    }
    
}
