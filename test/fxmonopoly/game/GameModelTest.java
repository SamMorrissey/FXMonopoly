/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game;

import fxmonopoly.gamedata.board.locations.*;
import fxmonopoly.gamedata.decks.cards.*;
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
 * Tests that the GameModel functions and manipulates as expected.
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
        
        model.reorderList(10);
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
        model.reorderList(10);
        
        boolean userActive = model.userIsActive();
        
        model.nextPlayer();
        
        assertNotSame(userActive, model.userIsActive());
        
        model.nextPlayer();
        
        assertEquals(userActive, model.userIsActive());
    }
    
    /**
     * Tests that the active player pay to exit jail function, works as expected.
     */
    @Test
    public void testActivePlayerPayToExitJail() {
        model.reorderList(10);
        
        assertFalse(model.getActivePlayer().isInJail());
        
        model.getReorderedList().get(0).enterJail();
        
        assertTrue(model.getActivePlayer().isInJail());
        
        model.activePlayerPayToExitJail();
        
        assertEquals(1450, model.getActivePlayer().getCash());
        assertFalse(model.getActivePlayer().isInJail());
    }
    
    /**
     * Tests that all methods pertaining to the active player GOJFCards function
     * as expected.
     */
    @Test
    public void testActivePlayerGOJFCard() {
        model.reorderList(0);
        
        assertFalse(model.getActivePlayer().hasGOJFCard());
        
        Card card = mock(GOJFCard.class);
        model.getReorderedList().get(0).addGOJFCard((GOJFCard) card);
        
        assertTrue(model.getActivePlayer().hasGOJFCard());
        
        model.getReorderedList().get(0).enterJail();
        
        assertTrue(model.getActivePlayer().isInJail());
        
        model.useActivePlayerGOJFCard();
        
        assertFalse(model.getActivePlayer().hasGOJFCard());
        assertFalse(model.getActivePlayer().isInJail());
    }
    
    /**
     * Tests that the active player die roll and move functions as expected.
     */
    @Test
    public void testActivePlayerRollDieAndMove() {
        model.reorderList(10);
        
        assertTrue(model.getActivePlayer().getCanRoll());
        
        int[] i = model.rollDie();
        
        model.diceMove(i);
        
        if(i[0] == i[1]) {
            assertTrue(model.getActivePlayer().getCanRoll());
        }
        else {
            assertFalse(model.getActivePlayer().getCanRoll());
        }
        
    }
    
    /**
     * Tests that the user is active function performs as intended.
     */
    @Test
    public void testUserIsActive() {
        model.reorderList(12);
        
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
        model.reorderList(12);
        
        while(!(model.retrieveLocation(model.getActivePlayer().getPosition()) instanceof PropertyLocation) &&
              !(model.retrieveLocation(model.getActivePlayer().getPosition()) instanceof UtilityLocation) &&
              !(model.retrieveLocation(model.getActivePlayer().getPosition()) instanceof RailwayLocation)) {
            
            int[] i = model.rollDie();
            model.diceMove(i);
            model.nextPlayer(); 
        }
        
        Player player;
        
        for(Player temp : model.getReorderedList()) {
            if(temp.getPosition() == model.getActivePlayer().getPosition()) {
                player = temp;
                assertTrue(player.getOwnedLocations().isEmpty());
                model.activePlayerBuyLocation();
                assertFalse(player.getOwnedLocations().isEmpty());
                assertTrue(player.getOwnedLocations().get(0) == model.retrieveLocation(model.getActivePlayer().getPosition()));
                break;
            }
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
        model.reorderList(10);
        
        assertEquals(model.getActivePlayer(), model.getReorderedList().get(0));
        
        model.nextPlayer();
        
        assertEquals(model.getActivePlayer(), model.getReorderedList().get(1));
    }
    
    /**
     * Tests that the active player bankruptcy boolean is retrieved as expected.
     */
    @Test
    public void testIsActivePlayerBankrupt() {
        model.reorderList(10);
        
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
        model.reorderList(12);
        
        if(model.userIsActive()) {
            model.nextPlayer();
        }
        
        model.getUser().addCash(-1500);
        PropertyLocation location = (PropertyLocation) model.retrieveLocation(1);
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
        model.reorderList(12);
        
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
    
    /**
     * Tests that a trade is started and retrieved as expected.
     */
    @Test
    public void testStartTrade() {
        assertNull(model.getActiveTrade());
        
        Player playerFrom = model.getInitialList().get(0);
        model.startTrade(playerFrom);
                
        assertNotNull(model.getActiveTrade());
    }
    
    /**
     * Tests that the trades resolve in the expected manner.
     */
    @Test
    public void testResolveTrade() {
        model.startTrade(model.getInitialList().get(0));
        model.getActiveTrade().setPlayerTo(model.getInitialList().get(1));
        
        assertNotNull(model.getActiveTrade());
        
        PropertyLocation property = (PropertyLocation) model.retrieveLocation(1);
        model.getActiveTrade().getOfferList().add(property);
        
        PropertyLocation property2 = (PropertyLocation) model.retrieveLocation(3);
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
    
    /**
     * Tests that the trade is nullified as expected.
     */
    @Test
    public void testCancelTrade() {
        model.startTrade(model.getInitialList().get(0));
        
        assertNotNull(model.getActiveTrade());
        
        model.cancelActiveTrade();
        
        assertNull(model.getActiveTrade());
    }
    
    /**
     * Tests that a bid is created and retrieved as expected.
     */
    @Test
    public void testStartBid() {
        assertNull(model.getActiveBid());
        
        model.startBid();
        
        assertNotNull(model.getActiveBid());
    }
    
    /**
     * Tests that a bid is resolved in the manner expected.
     */
    @Test
    public void testResolveBid() {
        model.startBid();
        
        model.getActiveBid().addBid(model.getInitialList().get(0), 1);
        model.getActiveBid().addBid(model.getInitialList().get(1), 2);
        
        Location location = model.retrieveLocation(1);  
        model.getActiveBid().setLocation(location);
        
        assertTrue(model.getActiveBid().containsLocation());
        
        model.resolveActiveBid();
        
        ((PropertyLocation) location).transferOwnership(model.getInitialList().get(1));
        model.getInitialList().get(1).addLocation(location);
        
        
        assertEquals(1498, model.getInitialList().get(1).getCash());
        assertEquals(location, model.getInitialList().get(1).getOwnedLocations().get(0));
        assertEquals(((PropertyLocation) location).getOwner(), model.getInitialList().get(1));
        assertEquals(1500, model.getInitialList().get(0).getCash());
    }
    
    /**
     * Tests that the bid is nullified as expected.
     */
    @Test
    public void testCancelBid() {
        model.startBid();
        
        assertNotNull(model.getActiveBid());
        
        model.clearActiveBid();
        
        assertNull(model.getActiveBid());
    }
    
    /**
     * Tests that card and required position actions are performing as expected.
     */
    @Test
    public void testProcessCardAndRequiredLocationActions() {
        model.reorderList(12);
        model.getActivePlayer().moveTo(1);
        
        assertEquals(1500, model.getActivePlayer().getCash());
        
        model.processRequiredPositionAction();
        
        assertEquals(1500, model.getActivePlayer().getCash());
        
        model.nextPlayer();
        
        PropertyLocation location = (PropertyLocation) model.retrieveLocation(3);
        location.transferOwnership(model.getActivePlayer());
        model.getActivePlayer().addLocation(location);
        
        model.nextPlayer();
        
        model.getActivePlayer().moveTo(3);
        model.processRequiredPositionAction();
        
        assertEquals((1500 - location.getRent()), model.getActivePlayer().getCash());
        
        model.getActivePlayer().moveTo(2);
        model.processRequiredPositionAction();
        
        assertEquals(false, model.getActiveCard().getFromChanceDeck());
        
        // If position is set to a chance location then the first assert should be
        // flipped, but the rest can remain the same.
        int i;
        for(i = 0; i < 16; i++) {
            model.getActivePlayer().moveTo(2);
            model.processRequiredPositionAction();
            
            assertEquals(false, model.getActiveCard().getFromChanceDeck());
            
            model.processCardActions();
            
            if(model.getActiveCard() instanceof GOJFCard) {
                assertTrue(model.getActivePlayer().hasGOJFCard());
                assertEquals(model.getActiveCard(), model.getActivePlayer().removeGOJFCard());
            }
            else if(model.getActiveCard() instanceof MoveToCard) {
            
                if(model.getActiveCard().getDescription().contains("Go to Jail")) {
                    assertTrue(model.getActivePlayer().isInJail());
                }
                else {
                    assertEquals(model.getActivePlayer().getPosition(), ((MoveToCard) model.getActiveCard()).getMoveLocation());
                }
            }
            else if(model.getActiveCard() instanceof NearestRailwayCard) {
                assertTrue(model.retrieveLocation(model.getActivePlayer().getPosition()) instanceof RailwayLocation);
            }
            else if(model.getActiveCard() instanceof NearestUtilityCard) {
                assertTrue(model.retrieveLocation(model.getActivePlayer().getPosition()) instanceof UtilityLocation);
            }
            
             
        }
        
    }
}
