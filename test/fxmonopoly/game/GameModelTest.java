/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game;

import fxmonopoly.gamedata.board.locations.GoLocation;
import fxmonopoly.gamedata.board.locations.PropertyLocation;
import fxmonopoly.gamedata.board.locations.RailwayLocation;
import fxmonopoly.gamedata.board.locations.UtilityLocation;
import fxmonopoly.gamedata.decks.cards.Card;
import fxmonopoly.gamedata.decks.cards.GOJFCard;
import fxmonopoly.gamedata.players.CPUPlayer;
import fxmonopoly.gamedata.players.Player;
import fxmonopoly.gamedata.players.UserPlayer;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)

/**
 *
 * @author Sam P. Morrissey
 */
public class GameModelTest {
    
    GameModel model;
    
    @Before
    public void setUp() {
        model = new GameModel();
        model.createAndAddCPU();
        model.createAndAddUser("Testing");
    }
    
    @After
    public void tearDown() {
        model = null;
    }

    /**
     * Tests that the user player is created as expected.
     */
    @Test
    public void testCreateAndAddUser() { 
        assertTrue(model.containsUser());
    }
    
    /**
     * Tests that the cpu players are created as expected.
     */
    @Test
    public void testCreateAndAddCPU() {
        assertTrue(model.containsCPU());
    }
    
    /**
     * Tests that the reordered list contains all elements of the initial list and
     * is the same size.
     */
    @Test
    public void testReorderingList() {
        model.createAndAddCPU();
        model.createAndAddCPU();
        
        model.reorderingList(10);
        assertTrue(model.getReorderedList().containsAll(model.getInitialList()));
        
        // Two are automatically instantiated in the test set up.
        assertEquals(4, model.getReorderedList().size());
    }
    
    /**
     * Tests that the cpu name string number is acting as expected.
     */
    @Test
    public void testCPUNameStringNumber() {
        model.createAndAddCPU();
        model.createAndAddCPU();
        
        int i;
        for(i = 2; i < model.getInitialList().size(); i++) {
            assertEquals("CPU" + i, model.getInitialList().get(i).getName());
        }
    }
    
    /**
     * Tests that the next player function performs as expected.
     */
    @Test
    public void testNextPlayer() {
        model.reorderingList(10);
        
        boolean userActive = model.userIsActive();
        
        model.nextPlayer();
        
        assertNotEquals(userActive, model.userIsActive());
        
        model.nextPlayer();
        
        assertEquals(userActive, model.userIsActive());
    }
    
    /**
     * Tests that the active player location is retrieved as expected.
     */
    @Test
    public void testGetActivePlayerLocation() {
        model.reorderingList(10);
        
        assertTrue(model.getActivePlayerLocation() instanceof GoLocation);
    }
    
    /**
     * Tests that the active player position is retrieved as expected.
     */
    @Test
    public void testGetActivePlayerPosition() {
        model.reorderingList(10);
        
        assertEquals(model.getActivePlayerPosition(), model.getReorderedList().get(0).getPosition());
    }
    
    /**
     * Tests that the active player cash is retrieved as expected.
     */
    @Test
    public void testGetActivePlayerCash() {
        model.reorderingList(10);
        
        assertEquals(model.getActivePlayerCash(), model.getReorderedList().get(0).getCash());
    }
    
    /**
     * Tests that the active player name is retrieved as expected.
     */
    @Test
    public void testGetActivePlayerName() {
        model.reorderingList(10);
        
        if(model.getReorderedList().get(0) instanceof CPUPlayer)
            assertEquals("CPU1", model.getActivePlayerName());
        else 
            assertEquals("Testing", model.getActivePlayerName());
    }
    
    /**
     * Tests that the active player jail status is retrieved as expected.
     */
    @Test
    public void testGetActivePlayerJailStatus() {
        model.reorderingList(10);
        
        assertFalse(model.getActivePlayerJailStatus());
        
        model.getReorderedList().get(0).enterJail();
        
        assertTrue(model.getActivePlayerJailStatus());
        
        model.getReorderedList().get(0).exitJail();
        
        assertFalse(model.getActivePlayerJailStatus());
    }
    
    /**
     * Tests that the active player pay to exit jail function, works as expected.
     */
    @Test
    public void testActivePlayerPayToExitJail() {
        model.reorderingList(10);
        
        assertFalse(model.getActivePlayerJailStatus());
        
        model.getReorderedList().get(0).enterJail();
        
        assertTrue(model.getActivePlayerJailStatus());
        
        model.activePlayerPayToExitJail();
        
        assertEquals(1450, model.getActivePlayerCash());
        assertFalse(model.getActivePlayerJailStatus());
    }
    
    /**
     * Tests that all methods pertaining to the active player GOJFCards function
     * as expected.
     */
    @Test
    public void testActivePlayerGOJFCard() {
        model.reorderingList(0);
        
        assertFalse(model.getActivePlayerHasGOJFCard());
        
        Card card = mock(GOJFCard.class);
        model.getReorderedList().get(0).addGOJFCard((GOJFCard) card);
        
        assertTrue(model.getActivePlayerHasGOJFCard());
        
        model.getReorderedList().get(0).enterJail();
        
        assertTrue(model.getActivePlayerJailStatus());
        
        model.useActivePlayerGOJFCard();
        
        assertFalse(model.getActivePlayerHasGOJFCard());
        assertFalse(model.getActivePlayerJailStatus());
    }
    
    /**
     * Tests that the active player die roll and move functions as expected.
     */
    @Test
    public void testActivePlayerRollDieAndMove() {
        model.reorderingList(10);
        
        assertTrue(model.getActivePlayerDieRollStatus());
        
        int[] i = model.rollDieAndMove();
        
        if(i[0] == i[1]) {
            assertTrue(model.getActivePlayerDieRollStatus());
        }
        else {
            assertFalse(model.getActivePlayerDieRollStatus());
        }
        
    }
    
    /**
     * Tests that the user player cash is retrieved as expected.
     */
    @Test
    public void testUserPlayerGetCash() {
        model.reorderingList(12);
        assertEquals(1500, model.getUserPlayerCash());
        
        for(Player player : model.getReorderedList()) {
            if(player instanceof UserPlayer) {
                player.addCash(-100);
                assertEquals(1400, model.getUserPlayerCash());
            }
        }
    }
    
    /**
     * Tests that the user player name is retrieved as expected.
     */
    @Test
    public void testUserPlayerGetName() {
        assertEquals("Testing", model.getUserPlayerName());
    }
    
    /**
     * Tests that the user is active function performs as intended.
     */
    @Test
    public void testUserIsActive() {
        model.reorderingList(12);
        
        if(model.getReorderedList().get(0) instanceof UserPlayer) {
            assertTrue(model.userIsActive());
        }
        else {
            assertFalse(model.userIsActive());
        }
    }
    
    /**
     * Tests that the active player buy location function performs as expected.
     */
    @Test
    public void testActivePlayerBuyLocation() {
        model.reorderingList(12);
        assertTrue(model.getActivePlayerDieRollStatus());
        
        while(!(model.getLocationByPosition(model.getActivePlayerPosition()) instanceof PropertyLocation) &&
              !(model.getLocationByPosition(model.getActivePlayerPosition()) instanceof UtilityLocation) &&
              !(model.getLocationByPosition(model.getActivePlayerPosition()) instanceof RailwayLocation)) {
            
            model.rollDieAndMove();
            model.nextPlayer(); 
        }
        
        Player player;
        
        for(Player temp : model.getReorderedList()) {
            if(temp.getPosition() == model.getActivePlayerPosition()) {
                player = temp;
                assertTrue(player.getOwnedLocations().isEmpty());
                model.activePlayerBuyLocation();
                assertFalse(player.getOwnedLocations().isEmpty());
                assertTrue(player.getOwnedLocations().get(0) == model.getLocationByPosition(model.getActivePlayerPosition()));
                break;
            }
        }
        
    }
}
