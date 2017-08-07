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
import static org.mockito.Mockito.mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)

/**
 * Tests the Railway location class.
 * @author Sam P. Morrissey
 */
public class RailwayLocationTest {

    RailwayLocation location = new RailwayLocation("Testing");
    Player player = mock(Player.class);
    
    /**
     * Tests that the default and retrieved price are equivalent.
     */
    @Test
    public void testGetPrice() {
        assertEquals(200, location.getPrice());
    }

    /**
     * Tests the multiplier generation to ensure that the expected cases return
     * the respective values, and any outside the expected return 0.
     */
    @Test
    public void testGetRentMultiplier() {
        assertEquals(0, location.getRentMultiplier(0));
        assertEquals(1, location.getRentMultiplier(1));
        assertEquals(2, location.getRentMultiplier(2));
        assertEquals(4, location.getRentMultiplier(3));
        assertEquals(8, location.getRentMultiplier(4));
        assertEquals(0, location.getRentMultiplier(5));
    }

    /**
     * Tests that the default value of isOwned is the one retrieved.
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
     * Tests that the specified and retrieved names are equivalent.
     */
    @Test
    public void testName() {
        assertEquals("Testing", location.getName());
    }
    
}
