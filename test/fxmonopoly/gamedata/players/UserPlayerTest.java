/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.players;

import fxmonopoly.gamedata.board.locations.PropertyLocation;
import fxmonopoly.gamedata.board.locations.RailwayLocation;
import fxmonopoly.gamedata.board.locations.UtilityLocation;
import fxmonopoly.gamedata.decks.cards.GOJFCard;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)

/**
 * Tests the user player class. Although at current all methods are inherited,
 * since the extended class is abstract these must be tested via the concrete
 * instantiations.
 * @author Sam P. Morrissey
 */
public class UserPlayerTest {
    
    UserPlayer player;
    
    PropertyLocation property;
    UtilityLocation utility;
    RailwayLocation railway;
    GOJFCard gojf;
    
    /**
     * Sets the fields to the correct values.
     */
    @Before
    public void setUp() {
        player = new UserPlayer("Testing");
        
        property = mock(PropertyLocation.class);
        utility = mock(UtilityLocation.class);
        railway = mock(RailwayLocation.class);
        gojf = mock(GOJFCard.class);
    }
    
    /**
     * Resets the fields to null.
     */
    @After
    public void tearDown() {
        player = null;
        
        property = null;
        utility = null;
        railway = null;
        gojf = null;
    }
    
    /**
     * Tests that the designated and retrieved names are equivalent.
     */
    @Test
    public void testGetName() {
        assertEquals("Testing", player.getName());
    }
    
    /**
     * Tests that the add location method functions as expected.
     */
    @Test
    public void testAddLocation() {
        assertEquals(0, player.getOwnedLocations().size());
        
        player.addLocation(property);
        assertEquals(1, player.getOwnedLocations().size());
        
        player.addLocation(utility);
        player.addLocation(railway);
        assertEquals(3, player.getOwnedLocations().size());
    }
    
    /**
     * Tests that the remove location method functions as expected.
     */
    @Test
    public void testRemoveLocation() {
        assertEquals(0, player.getOwnedLocations().size());
        
        player.addLocation(property);
        assertEquals(1, player.getOwnedLocations().size());
        
        player.removeLocation(property);
        assertEquals(0, player.getOwnedLocations().size());
    }
    
    /**
     * Tests that only the Properties are retrieved via the getOwnedProperties method.
     */
    @Test
    public void testGetProperties() {
        assertEquals(0, player.getOwnedProperty().size());
        
        player.addLocation(property);
        player.addLocation(utility);
        player.addLocation(railway);
        
        assertEquals(1, player.getOwnedProperty().size());
    }
    
    /**
     * Tests that only the Utilities are retrieved via the getOwnedUtilities method.
     */
    @Test
    public void testGetUtilities() {
        assertEquals(0, player.getOwnedUtilities().size());
        
        player.addLocation(property);
        player.addLocation(utility);
        player.addLocation(railway);
        
        assertEquals(1, player.getOwnedUtilities().size());
    }
    
    /**
     * Tests that only the Railways are retrieved via the getOwnedRailways method.
     */
    @Test
    public void testGetRailways() {
        assertEquals(0, player.getOwnedRailways().size());
        
        player.addLocation(property);
        player.addLocation(utility);
        player.addLocation(railway);
        
        assertEquals(1, player.getOwnedRailways().size());
    }
    
    /**
     * Tests that the methods related to the GOJF cards of the player are
     * working as expected.
     */
    @Test
    public void testGOJFCardMethods() {
        assertFalse(player.hasGOJFCard());
        
        player.addGOJFCard(gojf);
        assertTrue(player.hasGOJFCard());
        
        player.removeGOJFCard();
        assertFalse(player.hasGOJFCard());
    }
    
    /**
     * Tests that the expected and retrieved position are equivalent.
     */
    @Test
    public void testGetPosition() {
        assertEquals(0, player.getPosition());
    }
    
    /**
     * Tests that the move by method works as intended.
     */
    @Test
    public void testMoveBy() {
        assertEquals(0, player.getPosition());
        
        player.moveBy(-1);
        assertEquals(39, player.getPosition());
        
        player.moveBy(10);
        assertEquals(9, player.getPosition());
    }
    
    /**
     * Tests that the move to method works as intended.
     */
    @Test
    public void testMoveTo() {
        assertEquals(0, player.getPosition());
        
        player.moveTo(-1);
        assertEquals(0, player.getPosition());
        
        player.moveTo(40);
        assertEquals(0, player.getPosition());
        
        player.moveTo(20);
        assertEquals(20, player.getPosition());
    }
    
    /**
     * Tests that the jail status is updated and retrieved as expected.
     */
    @Test
    public void testJailStatus() {
        assertFalse(player.isInJail());
        
        player.enterJail();
        assertTrue(player.isInJail());
        
        player.exitJail();
        assertFalse(player.isInJail());
    }
    
    /**
     * Tests that the addCash and getCash methods track the values as expected.
     */
    @Test
    public void testCash() {
        assertEquals(1500, player.getCash());
        
        player.addCash(100);
        assertEquals(1600, player.getCash());
        
        player.addCash(-1600);
        assertEquals(0, player.getCash());
    }
}
