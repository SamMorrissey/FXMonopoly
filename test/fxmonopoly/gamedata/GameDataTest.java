/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata;

import fxmonopoly.gamedata.decks.cards.Card;
import fxmonopoly.gamedata.players.*;
import java.util.ArrayList;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)

/**
 * Tests the GameData class. Utilises mocking for externally instantiated elements
 * i.e. the player list, with all internal instantiation covered as far as possible
 * by asserts of their values.
 * @author Sam P. Morrissey
 */
public class GameDataTest {
    
    GameData data;
    
    UserPlayer user;
    CPUPlayer cpu;
    
    ArrayList<Player> array;
    
    /**
     * Sets the initial state for each test to manipulate. Both players are 
     * mocked utilising the when, thenReturn, thenCallRealMethod and doCallRealMethod 
     * methods of the Mockito library. 
     * <p>
     * The user player is mocked more extensively for the purpose of having the
     * user player set as the active player and thus modified via the GameData
     * method calls. The aim is to test for behaviour, not for implementation of
     * other classes where possible. For example the Board and Card Decks cannot
     * be extensively mocked but also form part of the internal GameData
     * representation, whereas the player list is passed to its constructor.
     */
    @Before
    public void setUp() {
        user = mock(UserPlayer.class); 
        when(user.getName()).thenReturn("Testing");
        
        cpu = mock(CPUPlayer.class);
        when(cpu.getName()).thenReturn("Testing1");
        
        array = new ArrayList<>();
        
        array.add(user);
        array.add(cpu);
        
        data = new GameData();
        data.setPlayerList(array);
        
    }
    
    /**
     * Resets the variables utilised to null values.
     */
    @After
    public void tearDown() {
        data = null;
        
        user = null;
        cpu = null;
        
        array = null;
    }

    /**
     * Tests that the player lists provided and retrieved are equivalent.
     */
    @Test
    public void testGetPlayerList() {
        assertEquals(array, data.getPlayerList());
    }

    /**
     * Tests that the remove player method functions as expected.
     */
    @Test
    public void testRemovePlayer() {
        assertEquals(2, data.getPlayerList().size());
        
        data.removePlayer(user);
        
        assertEquals(1, data.getPlayerList().size());
    }

    /**
     * Tests that the next player method does swap to the next player.
     */
    @Test
    public void testNextPlayer() {
        assertEquals("Testing", data.getActivePlayer().getName());
        
        data.nextPlayer();
        
        assertNotEquals("Testing", data.getActivePlayer().getName());
    }

    /**
     * Tests the method to retrieve the next chance card. Asserts that they are
     * not null, and that consecutive cards are not the same reference.
     */
    @Test
    public void testGetNextChanceCard() {
        data.drawNextChanceCard();
        assertNotNull(data.getActiveCard());
        
        Card card = data.getActiveCard();
        data.drawNextChanceCard();
        assertNotEquals(card, data.getActiveCard());
    }

    /**
     * Tests the method to retrieve the next community chest card. Asserts that
     * they are not null, and that consecutive cards are not the same reference.
     */
    @Test
    public void testGetNextCommunityChestCard() {
        data.drawNextCommunityChestCard();
        assertNotNull(data.getActiveCard());
        
        Card card = data.getActiveCard();
        data.drawNextCommunityChestCard();
        assertNotEquals(card, data.getActiveCard());
    }

    /**
     * Tests the Trade manipulations in the GameData class.
     */
    @Test
    public void testTrade() {
        assertNull(data.getActiveTrade());
        
        data.newTrade(user);
        
        assertNotNull(data.getActiveTrade());
        
        data.clearActiveTrade();
        
        assertNull(data.getActiveTrade());
    }

    /**
     * Tests the Bid manipulations in the GameData class.
     */
    @Test
    public void testBid() {
        assertNull(data.getActiveBid());
        
        data.newBid();
        
        assertNotNull(data.getActiveBid());
        
        data.clearActiveBid();
        
        assertNull(data.getActiveBid());
    }
    
    @Test
    public void testDie() {
        assertNotNull(data.getDie());
        
        int i = data.getDie().rollFirstDie();
        assertTrue(i > 0 && i <= 6);
        
        int j = data.getDie().rollSecondDie();
        assertTrue(j > 0 && j <= 6);
        
        assertTrue(i == data.getDie().getDieOneLastValue() &&
                   j == data.getDie().getDieTwoLastValue());
        
        assertTrue((i + j) == data.getDie().dieRollTotal());
        
        int k;
        for(k = 0; k < 50; k++) {
            data.getDie().rollFirstDie();
            data.getDie().rollSecondDie();
            
            if(data.getDie().getDieOneLastValue() == 
               data.getDie().getDieTwoLastValue()) {
                
                assertTrue(data.getDie().isDoubles());
            }
            else {
                assertFalse(data.getDie().isDoubles());
            }
        }
        
    }
    
}
