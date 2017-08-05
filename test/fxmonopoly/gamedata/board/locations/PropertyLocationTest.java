/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

import fxmonopoly.gamedata.players.Player;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)

/**
 * Tests the Property location class.
 * @author Sam P. Morrissey
 */
public class PropertyLocationTest {
    
    PropertyLocation property;
    Player player;

    @Before
    public void setUp() {
        
        property = new PropertyLocation("Testing", 200, 
                                        new int[]{2, 10, 20, 40, 80, 160}, 
                                        200);
    
        player = mock(Player.class);
        
    }
    
    @After
    public void tearDown() {
        property = null;
        player = null;
    }
    
    /**
     * Tests that the specified and retrieved prices are equivalent.
     */
    @Test
    public void testGetPrice() {
        assertEquals(200, property.getPrice());
    }

    /**
     * Tests that the mortgage value has been set correctly.
     */
    @Test
    public void testGetMortgageValue() {
        assertEquals(property.getPrice() / 2, property.getMortgageValue());
    }

    /**
     * Tests that the mortgage value is set correctly.
     */
    @Test
    public void testSetMortgaged() {
        property.setDevelopableStatus(true);
        property.setMortgaged(true);
        assertTrue(property.getMortgagedStatus());
        assertFalse(property.getDevelopableStatus());
    }

    /**
     * Tests that at each point the rent is returned correctly. Including boundary
     * testing.
     */
    @Test
    public void testGetRent() {
        
        // Tests that the minimum number of houses is 0 and rent is retrieved properly.
        property.addHouses(-1);
        assertEquals(2, property.getRent());
        
        property.setInColourMonopolyStatus(true);
        assertEquals(4, property.getRent());
        
        property.addHouses(1);
        assertEquals(10, property.getRent());
        
        property.addHouses(1);
        assertEquals(20, property.getRent());
        
        property.addHouses(1);
        assertEquals(40, property.getRent());
        
        property.addHouses(1);
        assertEquals(80, property.getRent());
        
        // Tests that the upper bound of houses is 4 and rent is the same as the previous assert.
        property.addHouses(1);
        assertEquals(80, property.getRent());
        
        property.setInColourMonopolyStatus(true);
        property.setUpgradeableToHotel(true);
        property.upgradeToHotel();
        assertEquals(160, property.getRent());
        
        property.downgradeFromHotel();
    }

    /**
     * Tests the bounds on the number of houses the property can have.
     */
    @Test
    public void testNumberOfHouses() {
        
        property.addHouses(-1);
        assertEquals(0, property.getNumberOfHouses());
        
        property.addHouses(5);
        assertEquals(4, property.getNumberOfHouses());
        
        property.addHouses(-4);
    }

    /**
     * Tests the pre-qualifier boolean regarding whether the property can be
     * upgraded to a Hotel.
     */
    @Test
    public void testSetUpgradeableToHotel() {
        
        property.setUpgradeableToHotel(true);
        assertFalse(property.getUpgradeableToHotel());
        
        property.addHouses(4);
        property.setUpgradeableToHotel(true);
        assertTrue(property.getUpgradeableToHotel());
   
    }

    /**
     * Tests to ensure that the upgrade to hotel method functions correctly.
     */
    @Test
    public void testUpgradeToHotel() {
        
        property.setUpgradeableToHotel(false);
        property.upgradeToHotel();
        assertFalse(property.getIsHotel());
        
        property.addHouses(4);
        property.setUpgradeableToHotel(true);
        property.upgradeToHotel();
        assertTrue(property.getIsHotel());
        
    }

    /**
     * Tests that the downgrade from hotel method functions correctly.
     */
    @Test
    public void testDowngradeFromHotel() {
        
        property.addHouses(4);
        property.setUpgradeableToHotel(true);
        property.upgradeToHotel();
        assertTrue(property.getIsHotel());
        
        property.downgradeFromHotel();
        assertFalse(property.getIsHotel());
        
    }

    /**
     * Tests that the specified and retrieved owners are equivalent.
     */
    @Test
    public void testOwnership() {
        
        assertNull(property.getOwner());
        
        property.transferOwnership(player);
        assertEquals(player, property.getOwner());
        
        property.removeOwnership();
        assertNull(property.getOwner());
        
    }

    /**
     * Tests that the developable status is correctly set and un-set.
     */
    @Test
    public void testSetDevelopableStatus() {
        assertFalse(property.getDevelopableStatus());
        
        property.setDevelopableStatus(true);
        assertTrue(property.getDevelopableStatus());
    }
    
}
