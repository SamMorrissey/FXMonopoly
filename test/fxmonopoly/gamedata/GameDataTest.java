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
 *
 * @author Sam P. Morrissey
 */
public class GameDataTest {
    
    GameData data;
    
    UserPlayer user;
    CPUPlayer cpu;
    
    ArrayList<Player> array;
    
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
     * Tests that the next player method, does swap to the next player.
     */
    @Test
    public void testNextPlayer() {
        assertEquals("Testing", data.getActivePlayerName());
        
        data.nextPlayer();
        
        assertNotEquals("Testing", data.getActivePlayerName());
    }

    /**
     * Test of getActivePlayerLocation method, of class GameData.
     */
    @Test
    public void testGetActivePlayerLocation() {
        assertTrue(data.getActivePlayerLocation() instanceof GoLocation);
    }

    /**
     * Test of getActivePlayerCash method, of class GameData.
     */
    @Test
    public void testGetActivePlayerCash() {
        assertEquals(1500, data.getActivePlayerCash());
    }

    /**
     * Test of getActivePlayerName method, of class GameData.
     */
    @Test
    public void testGetActivePlayerName() {
        assertEquals("Testing", data.getActivePlayerName());
    }

    /**
     * Test of getActivePlayerJailStatus method, of class GameData.
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
     * Test of getActivePlayerDieRollStatus method, of class GameData.
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
     * Test of moveActivePlayerBy method, of class GameData.
     */
    @Test
    public void testMoveActivePlayerBy() {
        assertEquals(0, data.getActivePlayerPosition());
        
        data.moveActivePlayerBy(10);
        
        assertEquals(10, data.getActivePlayerPosition());
    }

    /**
     * Test of moveActivePlayerTo method, of class GameData.
     */
    @Test
    public void testMoveActivePlayerTo() {
        assertEquals(0, data.getActivePlayerPosition());
        
        data.moveActivePlayerTo(20);
        
        assertEquals(20, data.getActivePlayerPosition());
    }

    /**
     * Test of getUserPlayerCash method, of class GameData.
     */
    @Test
    public void testGetUserPlayerCash() {
        assertEquals(1500, data.getUserPlayerCash());
    }

    /**
     * Test of getUserPlayer method, of class GameData.
     */
    @Test
    public void testGetUserPlayer() {
        assertEquals(user, data.getUserPlayer());
    }

    /**
     * Test of getNextChanceCard method, of class GameData.
     */
    @Test
    public void testGetNextChanceCard() {
        assertNotNull(data.getNextChanceCard());
        assertNotEquals(data.getNextChanceCard(), data.getNextChanceCard());
    }

    /**
     * Test of getNextCommunityChestCard method, of class GameData.
     */
    @Test
    public void testGetNextCommunityChestCard() {
        assertNotNull(data.getNextCommunityChestCard());
        assertNotEquals(data.getNextCommunityChestCard(), data.getNextCommunityChestCard());
    }

    /**
     * Test of newTrade method, of class GameData.
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
     * Test of newBid method, of class GameData.
     */
    @Test
    public void testBid() {
        assertNull(data.getActiveBid());
        
        data.newBid();
        
        assertNotNull(data.getActiveBid());
        
        data.clearActiveBid();
        
        assertNull(data.getActiveBid());
    }
    
}
