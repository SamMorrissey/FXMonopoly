/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

import fxmonopoly.gamedata.players.Player;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)

/**
 * Tests the Utility location class.
 * @author Sam P. Morrissey
 */
public class UtilityLocationTest {
    
    UtilityLocation location = new UtilityLocation("Testing");
    Player player = mock(Player.class);
    
    /**
     * Tests that the default price is properly retrieved.
     */
    @Test
    public void testGetPrice() {
        assertEquals(150, location.getPrice());
    }
    
    /**
     * Tests that the default isOwned value is indeed false.
     */
    @Test
    public void testDefaultIsOwned() {
        assertFalse(location.getIsOwned());
    }

    /**
     * Tests that the ownership (both the isOwned boolean, and the actual owner
     * reference) are correctly set when the setOwner method is called.
     */
    @Test
    public void testSetOwnership() {
        location.setOwner(player);
        assertEquals(player, location.getOwner());
        assertTrue(location.getIsOwned());
    }
    
    /**
     * Tests that the ownership is correctly reset when removeOwner is called.
     */
    @Test
    public void testRemoveOwnership() {
        location.removeOwner();
        assertNull(location.getOwner());
        assertFalse(location.getIsOwned());
    }
    
    /**
     * Tests that the single ownership multiplier is properly retrieved.
     */
    @Test
    public void testSingleMultiplier() {
        assertEquals(4, location.getSingleMultiplier());
    }
    
    /**
     * Tests that the double ownership multiplier is properly retrieved.
     */
    @Test
    public void testDoubleMultiplier() {
        assertEquals(10, location.getDoubleMultiplier());
    }
    
    /**
     * Tests that the specified and retrieved names are equivalent.
     */
    @Test
    public void testName() {
        assertEquals("Testing", location.getName());
    }
}
