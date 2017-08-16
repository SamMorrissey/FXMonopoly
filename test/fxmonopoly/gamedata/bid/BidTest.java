/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.bid;

import fxmonopoly.gamedata.board.locations.Location;
import fxmonopoly.gamedata.decks.cards.GOJFCard;
import fxmonopoly.gamedata.players.CPUPlayer;
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
 * Tests the Bid class.
 * @author Sam P. Morrissey
 */
public class BidTest {
    
    Bid bid;
    UserPlayer player;
    CPUPlayer cpu;
    
    @Before
    public void setUp() {
        bid = new Bid();
        
        player = mock(UserPlayer.class);
        cpu = mock(CPUPlayer.class);
    }
    
    @After
    public void tearDown() {
        bid = null;
        player = null;
        cpu = null;
    }

    /**
     * Test the addBid and containsBid methods.
     */
    @Test
    public void testAddBid() {
        assertFalse(bid.containsBid());
        
        bid.addBid(player, 0);
        
        assertFalse(bid.containsBid());
        
        bid.addBid(player, 100);
        
        assertTrue(bid.containsBid());
    }

    /**
     * Tests the getHighestBid method. Also tests reference integrity, since
     * due to Integer being a class, JVM caching only makes reference equality
     * operators only viable between -128 and 127.
     */
    @Test
    public void testGetHighestBid() {
        bid.addBid(player, 100);
        bid.addBid(cpu, 150);
        
        assertEquals((int) 150, (int) bid.getHighestBid());
    }
    
    /**
     * Tests the getHighestBidder method.
     */
    @Test
    public void testGetHighestBidder() {
        bid.addBid(player, 100);
        bid.addBid(cpu, 150);
        
        assertEquals(cpu, bid.getHighestBidder().get(0));
    }
    
    /**
     * Tests that the second highest bid is retrieved correctly.
     */
    @Test
    public void testGetSecondHighestBid() {
        bid.addBid(player, 10);
        bid.addBid(cpu, 11);
        
        assertEquals(10, bid.getSecondHighestBid());
    }
    
    /**
     * Tests that the Location methods function as expected.
     */
    @Test
    public void testLocationMethods() {
        Location location = mock(Location.class);
        
        assertNull(bid.getLocation());
        assertFalse(bid.containsLocation());
        
        bid.setLocation(location);
        
        assertEquals(location, bid.getLocation());
        assertTrue(bid.containsLocation());
    }
    
    /**
     * Tests that the GOJFCard methods function as expected.
     */
    @Test
    public void testGOJFMethods() {
        GOJFCard card = mock(GOJFCard.class);
        
        assertNull(bid.getGOJFCard());
        assertFalse(bid.containsGOJFCard());
        
        bid.setGOJFCard(card);
        
        assertEquals(card, bid.getGOJFCard());
        assertTrue(bid.containsGOJFCard());
    }
    
}
