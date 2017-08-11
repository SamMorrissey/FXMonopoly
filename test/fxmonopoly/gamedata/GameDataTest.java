/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata;

import fxmonopoly.gamedata.board.locations.GoLocation;
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
        when(user.getCash()).thenReturn(1500);
        
        when(user.getPosition()).thenCallRealMethod();
        doCallRealMethod().when(user).moveBy(10);
        doCallRealMethod().when(user).moveTo(20);
        
        when(user.getName()).thenReturn("Testing");
        
        when(user.isInJail()).thenCallRealMethod();
        doCallRealMethod().when(user).enterJail();
        doCallRealMethod().when(user).exitJail();
        
        when(user.getCanRoll()).thenCallRealMethod();
        doCallRealMethod().when(user).setCanRoll(true);
        doCallRealMethod().when(user).setCanRoll(false);
        
        cpu = mock(CPUPlayer.class);
        when(cpu.getName()).thenReturn("Testing1");
        
        array = new ArrayList<>();
        
        array.add(user);
        array.add(cpu);
        
        data = new GameData(array);
        
        data.setUserPlayer(user);
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
        assertEquals("Testing", data.getActivePlayerName());
        
        data.nextPlayer();
        
        assertNotEquals("Testing", data.getActivePlayerName());
    }

    /**
     * Tests that the getActivePlayerLocation method retrieves a Location of the 
     * specified instantiation type. In this case of the GoLocation type, as all 
     * players are initially located at position zero, corresponding to the GoLocation.
     */
    @Test
    public void testGetActivePlayerLocation() {
        assertTrue(data.getActivePlayerLocation() instanceof GoLocation);
    }

    /**
     * Tests that the getActivePlayerCash method retrieves the expected cash value.
     */
    @Test
    public void testGetActivePlayerCash() {
        assertEquals(1500, data.getActivePlayerCash());
    }

    /**
     * Tests that the expected and retrieved names are equivalent.
     */
    @Test
    public void testGetActivePlayerName() {
        assertEquals("Testing", data.getActivePlayerName());
    }

    /**
     * Tests that the active player jail status manipulations are performed
     * correctly.
     */
    @Test
    public void testActivePlayerJailStatus() {
        assertFalse(data.getActivePlayerJailStatus());
        
        data.activePlayerEnterJail();
        
        assertTrue(data.getActivePlayerJailStatus());
        
        data.activePlayerExitJail();
        
        assertFalse(data.getActivePlayerJailStatus());
    }

    /**
     * Tests that the active player die roll status manipulations are 
     * performed correctly.
     */
    @Test
    public void testActivePlayerDieRollStatus() {
        assertFalse(data.getActivePlayerDieRollStatus());
        
        data.setActivePlayerDieRollStatus(true);
        
        assertTrue(data.getActivePlayerDieRollStatus());
        
        data.setActivePlayerDieRollStatus(false);
        
        assertFalse(data.getActivePlayerDieRollStatus());
    }

    /**
     * Tests that the move active player by method functions correctly
     */
    @Test
    public void testMoveActivePlayerBy() {
        assertEquals(0, data.getActivePlayerPosition());
        
        data.moveActivePlayerBy(10);
        
        assertEquals(10, data.getActivePlayerPosition());
    }

    /**
     * Tests that the move active player to method functions correctly.
     */
    @Test
    public void testMoveActivePlayerTo() {
        assertEquals(0, data.getActivePlayerPosition());
        
        data.moveActivePlayerTo(20);
        
        assertEquals(20, data.getActivePlayerPosition());
    }

    /**
     * Tests that the UserPlayer's cash is retrieved as expected.
     */
    @Test
    public void testGetUserPlayerCash() {
        assertEquals(1500, data.getUserPlayerCash());
    }

    /**
     * Tests that the UserPlayer instance is set correctly.
     */
    @Test
    public void testGetUserPlayer() {
        assertEquals(user, data.getUserPlayer());
    }

    /**
     * Tests the method to retrieve the next chance card. Asserts that they are
     * not null, and that consecutive cards are not the same reference.
     */
    @Test
    public void testGetNextChanceCard() {
        assertNotNull(data.getNextChanceCard());
        assertNotEquals(data.getNextChanceCard(), data.getNextChanceCard());
    }

    /**
     * Tests the method to retrieve the next community chest card. Asserts that
     * they are not null, and that consecutive cards are not the same reference.
     */
    @Test
    public void testGetNextCommunityChestCard() {
        assertNotNull(data.getNextCommunityChestCard());
        assertNotEquals(data.getNextCommunityChestCard(), data.getNextCommunityChestCard());
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
