/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.trade;

import fxmonopoly.gamedata.board.locations.PropertyLocation;
import fxmonopoly.gamedata.board.locations.RailwayLocation;
import fxmonopoly.gamedata.board.locations.UtilityLocation;
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
 * Tests the TradeOffer class.
 * @author Sam P. Morrissey
 */
public class TradeOfferTest {
    
    TradeOffer offer;
    UserPlayer playerTo;
    CPUPlayer playerFrom;
    
    GOJFCard card;
    PropertyLocation property;
    UtilityLocation utility;
    RailwayLocation railway;
    
    @Before
    public void setUp() {
        playerFrom = mock(CPUPlayer.class);
        
        
        card = mock(GOJFCard.class);
        property = mock(PropertyLocation.class);
        utility = mock(UtilityLocation.class);
        railway = mock(RailwayLocation.class);
        
        offer = new TradeOffer(playerFrom);
    }
    
    @After
    public void tearDown() {
        playerFrom = null;
        playerTo = null;
        
        card = null;
        property = null;
        utility = null;
        railway = null;
        
        offer = null;
    }

    @Test
    public void testGetPlayerFrom() {
        assertEquals(playerFrom, offer.getPlayerFrom());
    }
    
    @Test
    public void testPlayerTo() {
        assertNull(offer.getPlayerTo());
        
        playerTo = mock(UserPlayer.class);
        offer.setPlayerTo(playerTo);
        
        assertTrue(offer.hasPlayerTo());
        assertEquals(playerTo, offer.getPlayerTo());
    }
    
    @Test
    public void testOfferLocations() {
        assertFalse(offer.containsOfferLocations());
        
        offer.getOfferList().add(property);
        offer.getOfferList().add(utility);
        offer.getOfferList().add(railway);
        
        assertTrue(offer.containsOfferLocations());
        assertEquals(3, offer.getOfferList().size());
        
        offer.getOfferList().removeAll(offer.getOfferList());
        assertFalse(offer.containsOfferLocations());
    }
    
    @Test
    public void testForLocations() {
        assertFalse(offer.containsForLocations());
        
        offer.getForList().add(property);
        offer.getForList().add(utility);
        offer.getForList().add(railway);
        
        assertTrue(offer.containsForLocations());
        assertEquals(3, offer.getForList().size());
        
        offer.getForList().removeAll(offer.getForList());
        assertFalse(offer.containsForLocations());
    }
    
    @Test
    public void testGOJFMethods() {
        assertFalse(offer.containsGOJFCard());
        
        offer.getGOJFListTo().add(card);
        assertTrue(offer.containsGOJFCard());
        
        offer.getGOJFListFrom().add(card);
        assertFalse(offer.containsGOJFCard());
        
        assertEquals(1, offer.getGOJFListTo().size());
        assertEquals(1, offer.getGOJFListFrom().size());
        
        offer.getGOJFListTo().removeAll(offer.getGOJFListTo());
        assertTrue(offer.containsGOJFCard());
        
        offer.getGOJFListFrom().removeAll(offer.getGOJFListFrom());
        assertFalse(offer.containsGOJFCard());
    }
    
    @Test
    public void testCash() {
        assertFalse(offer.containsCash());
        
        offer.addCashTo(0);
        assertFalse(offer.containsCash());
        
        offer.addCashFrom(100);
        assertTrue(offer.containsCash());
        
        offer.addCashTo(100);
        assertFalse(offer.containsCash());
    }
    
}
