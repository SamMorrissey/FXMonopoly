/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board;

import fxmonopoly.gamedata.board.locations.PropertyLocation;
import fxmonopoly.gamedata.players.Player;
import fxmonopoly.gamedata.players.UserPlayer;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)

/**
 * Tests the board class. Only contains a single test since the rest of the board
 * is relatively inaccessible or is already tested by lower level unit tests.
 * @author Sam P. Morrissey
 */
public class BoardTest {
    
    Board board;
    Player player;
    
    
    @Before
    public void setUp() {
        board = new Board();
        
        // Can be either use implementation, the purpose of the mock is to ensure
        // that this doesnt matter.
        player = mock(UserPlayer.class);
    }
    
    @After
    public void tearDown() {
        board = null;
        player = null;
    }
    
    /**
     * Tests that the Board correctly assimilates property group states.
     */
    @Test
    public void testAssimilateBooleans() {
        
        PropertyLocation property = (PropertyLocation) board.getLocation(1);
        
        board.getGroup(property).forEach(e -> {
            assertNull(e.getOwner());
        });
        
        board.getGroup(property).forEach(e -> {
            e.transferOwnership(player);
        });
        
        board.assimilateColourGroupBooleans(board.getGroup(property));
        
        board.getGroup(property).forEach(e -> {
            assertTrue(e.getIinColourMonopolyStatus());
            assertTrue(e.getDevelopableStatus());
        });
        
        property.setMortgaged(true);
        board.assimilateColourGroupBooleans(board.getGroup(property));
        
        board.getGroup(property).forEach(e -> {
            assertTrue(e.getIinColourMonopolyStatus());
            assertFalse(e.getDevelopableStatus());
        });
        
        property.removeOwnership();
        board.assimilateColourGroupBooleans(board.getGroup(property));
        
        board.getGroup(property).forEach(e -> {
            assertFalse(e.getIinColourMonopolyStatus());
            assertFalse(e.getDevelopableStatus());
        });
    }
    
    @Test
    public void testEvenlyDevelopAndUnDevelop() {
        PropertyLocation property = (PropertyLocation) board.getLocation(1);
        
        board.getGroup(property).forEach(e -> {
            assertEquals(0, e.getNumberOfHouses());
        });
        
        board.getGroup(property).forEach(e -> {
            e.transferOwnership(player);
            e.setDevelopableStatus(true);
        });
        
        board.evenlyDevelop(property);
        
        assertEquals(1, property.getNumberOfHouses());
        
        board.evenlyDevelop(property);
        
        PropertyLocation second = (PropertyLocation) board.getLocation(3);
        
        assertEquals(1, property.getNumberOfHouses());
        assertEquals(1, second.getNumberOfHouses());
        
        board.evenlyDevelop(property);
        assertEquals(2, property.getNumberOfHouses());
        assertEquals(1, second.getNumberOfHouses());
        
        board.evenlyDevelop(property);
        assertEquals(2, property.getNumberOfHouses());
        assertEquals(2, second.getNumberOfHouses());
        
        board.evenlyDevelop(property);
        assertEquals(3, property.getNumberOfHouses());
        assertEquals(2, second.getNumberOfHouses());
        
        board.evenlyDevelop(property);
        assertEquals(3, property.getNumberOfHouses());
        assertEquals(3, second.getNumberOfHouses());
        
        board.evenlyDevelop(property);
        assertEquals(4, property.getNumberOfHouses());
        assertEquals(3, second.getNumberOfHouses());
        
        board.evenlyDevelop(property);
        assertEquals(4, property.getNumberOfHouses());
        assertEquals(4, second.getNumberOfHouses());
        
        board.assimilateColourGroupBooleans(board.getGroup(property));
        
        board.evenlyDevelop(property);
        
        assertTrue(property.getIsHotel());
        
        board.evenlyReduce(property);
        board.assimilateColourGroupBooleans(board.getGroup(property));
        
        assertFalse(property.getIsHotel());
        assertEquals(4, property.getNumberOfHouses());
        
        board.evenlyReduce(property);
        
        assertEquals(3, property.getNumberOfHouses());
        
        board.evenlyReduce(property);
        
        assertEquals(3, property.getNumberOfHouses());
        assertEquals(3, second.getNumberOfHouses());
        
    }
    
    @Test
    public void testNumberOfHousesLeft() {
        
        PropertyLocation property = (PropertyLocation) board.getLocation(1);
        
        board.getGroup(property).forEach(e -> {
            e.transferOwnership(player);
            e.setDevelopableStatus(true);
        });
        
        board.evenlyDevelop(property);
        
        assertEquals(31, board.getNumberOfHousesLeft());
        
        board.evenlyDevelop(property);
        
        assertEquals(30, board.getNumberOfHousesLeft());
        
        board.evenlyReduce(property);
        
        assertEquals(31, board.getNumberOfHousesLeft());
    }
    
    @Test
    public void testNumberOfHotelsLeft() {
        
        PropertyLocation property = (PropertyLocation) board.getLocation(1);
        
        board.getGroup(property).forEach(e -> {
            e.transferOwnership(player);
            e.setDevelopableStatus(true);
        });
        
        assertEquals(10, board.getNumberOfHotelsLeft());
        
        while(!property.getIsHotel()) {
            board.evenlyDevelop(property);
            board.assimilateColourGroupBooleans(board.getGroup(property));
        }
        
        assertEquals(9, board.getNumberOfHotelsLeft());
        
        board.evenlyReduce(property);
        
        assertEquals(10, board.getNumberOfHotelsLeft());
    }
    
}
