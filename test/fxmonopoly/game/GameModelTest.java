/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game;

import fxmonopoly.gamedata.board.locations.*;
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
import static org.mockito.Mockito.*;
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
    
    /**
     * Tests that the user owns location boolean functions as expected.
     */
    @Test
    public void testUserOwnsLocation() {
        model.reorderingList(10);
        
        while(!(model.getLocationByPosition(model.getUserPosition()) instanceof PropertyLocation) &&
              !(model.getLocationByPosition(model.getUserPosition()) instanceof UtilityLocation) &&
              !(model.getLocationByPosition(model.getUserPosition()) instanceof RailwayLocation)) {
            
            model.rollDieAndMove();
            model.nextPlayer();
        }
        
        Location location = model.getLocationByPosition(model.getUserPosition());
        
        if(location instanceof PropertyLocation) {
            assertFalse(model.userOwnsSpecificLocation(location));
            ((PropertyLocation) location).transferOwnership(model.getUser());
            assertTrue(model.userOwnsSpecificLocation(location));
        }
        else if(location instanceof UtilityLocation) {
            assertFalse(model.userOwnsSpecificLocation(location));
            ((UtilityLocation) location).setOwner(model.getUser());
            assertTrue(model.userOwnsSpecificLocation(location));
        }
        else if(location instanceof RailwayLocation) {
            assertFalse(model.userOwnsSpecificLocation(location));
            ((RailwayLocation) location).setOwner(model.getUser());
            assertTrue(model.userOwnsSpecificLocation(location));
        }
    }
    
    /**
     * Tests that the locations are retrieved as expected in terms of type and
     * position.
     */
    @Test
    public void testRetrieveLocation() {
        assertTrue(model.retrieveLocation(0) instanceof GoLocation);
        assertTrue(model.retrieveLocation(1) instanceof PropertyLocation);
        assertTrue(model.retrieveLocation(2) instanceof CommunityChestLocation);
        assertTrue(model.retrieveLocation(4) instanceof TaxLocation);
        assertTrue(model.retrieveLocation(5) instanceof RailwayLocation);
        assertTrue(model.retrieveLocation(7) instanceof ChanceLocation);
        assertTrue(model.retrieveLocation(10) instanceof JailLocation);
        assertTrue(model.retrieveLocation(12) instanceof UtilityLocation);
        assertTrue(model.retrieveLocation(20) instanceof FreeParkingLocation);
        assertTrue(model.retrieveLocation(30) instanceof GoToJailLocation);
    }
    
    /**
     * Tests that the user instance is retrieved as expected.
     */
    @Test
    public void testGetUser() {
        assertTrue(model.containsUser());
        assertTrue(model.getUser() instanceof UserPlayer);
    }
    
    /**
     * Tests that the active player is retrieved as expected.
     */
    @Test
    public void testGetActivePlayer() {
        model.reorderingList(10);
        
        assertEquals(model.getActivePlayer(), model.getReorderedList().get(0));
        
        model.nextPlayer();
        
        assertEquals(model.getActivePlayer(), model.getReorderedList().get(1));
    }
    
    /**
     * Tests that the active player bankruptcy boolean is retrieved as expected.
     */
    @Test
    public void testIsActivePlayerBankrupt() {
        model.reorderingList(10);
        
        assertFalse(model.isActivePlayerBankrupt());
        
        model.getActivePlayer().addCash(-1600);
        
        assertTrue(model.isActivePlayerBankrupt());
        
        Location location = mock(Location.class);
        
        model.getActivePlayer().getOwnedLocations().add(location);
        
        assertFalse(model.isActivePlayerBankrupt());
    }
    
    /**
     * Tests that the extra balance left after a bankruptcy is equalised as expected.
     */
    @Test
    public void testResolveExtraBalance() {
        model.reorderingList(12);
        
        if(model.userIsActive()) {
            model.nextPlayer();
        }
        
        model.getUser().addCash(-1500);
        PropertyLocation location = (PropertyLocation) model.getLocationByPosition(1);
        location.transferOwnership(model.getActivePlayer());
        model.getUser().moveTo(1);
        model.getUser().addCash(-location.getRent());
        
        model.nextPlayer();
        model.resolveExtraBalance();
        
        assertEquals(1500 - location.getRent(), location.getOwner().getCash());
    }
    
    /**
     * Tests that the remove active player from game method functions as expected.
     */
    @Test
    public void testRemoveActivePlayerFromGame() {
        model.reorderingList(12);
        
        if(!model.userIsActive()) {
            model.nextPlayer();
        }
        
        model.getUser().addCash(-1500);
        assertFalse(model.isActivePlayerBankrupt());
        model.removeActivePlayerFromGame();
        assertTrue(model.userIsActive());
        
        model.getUser().addCash(-1);
        assertTrue(model.isActivePlayerBankrupt());
        model.removeActivePlayerFromGame();
        assertTrue(model.userIsActive());
    }
    
    @Test
    public void testStartTrade() {
        assertNull(model.getActiveTrade());
        
        Player playerFrom = model.getInitialList().get(0);
        model.startTrade(playerFrom);
                
        assertNotNull(model.getActiveTrade());
    }
    
    @Test
    public void testResolveTrade() {
        model.startTrade(model.getInitialList().get(0));
        model.getActiveTrade().setPlayerTo(model.getInitialList().get(1));
        
        assertNotNull(model.getActiveTrade());
        
        PropertyLocation property = (PropertyLocation) model.getLocationByPosition(1);
        model.getActiveTrade().getOfferList().add(property);
        
        PropertyLocation property2 = (PropertyLocation) model.getLocationByPosition(3);
        model.getActiveTrade().getForList().add(property2);
        
        GOJFCard card = mock(GOJFCard.class);
        model.getActiveTrade().getGOJFListTo().add(card);
        
        assertTrue(model.getActiveTrade().containsGOJFCard());
        
        model.resolveActiveTrade();
        
        assertEquals(model.getInitialList().get(1).getOwnedLocations().get(0), property);
        assertEquals(model.getInitialList().get(1).removeGOJFCard(), card);
        assertEquals(model.getInitialList().get(0).getOwnedLocations().get(0), property2);
        
        assertNull(model.getActiveTrade());
    }
    
    @Test
    public void testStartBid() {
        assertNull(model.getActiveBid());
        
        model.startBid();
        
        assertNotNull(model.getActiveBid());
    }
    
    @Test
    public void testResolveBid() {
        model.startBid();
        
        
        
        model.getActiveBid().addBid(model.getInitialList().get(0), 1);
        model.getActiveBid().addBid(model.getInitialList().get(1), 2);
        
        Location location = model.getLocationByPosition(1);
        
        
        
        model.getActiveBid().setLocation(location);
        assertTrue(model.getActiveBid().containsLocation());
        
        model.resolveBid();
        
        System.out.println(model.getInitialList().get(1).getOwnedProperty().size());
        
        ((PropertyLocation) location).transferOwnership(model.getInitialList().get(1));
        model.getInitialList().get(1).addLocation(location);
        
        
        
        assertEquals(1498, model.getInitialList().get(1).getCash());
        assertEquals(location, model.getInitialList().get(1).getOwnedLocations().get(0));
        assertEquals(((PropertyLocation) location).getOwner(), model.getInitialList().get(1));
        assertEquals(1500, model.getInitialList().get(0).getCash());
    }
}
