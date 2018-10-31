/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

import fxmonopoly.gamedata.players.Player;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

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
        
        property = new PropertyLocation("Testing", 
                                        200, 
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
        assertTrue(property.getMortgaged());
        assertFalse(property.getDevelopableStatus());
    }

    /**
     * Tests that at each point the rent is returned correctly. Including boundary
     * testing.
     */
    @Test
    public void testGetRent() {
        
        assertEquals(2, property.getRent());
        
        property.setDevelopableStatus(true);
        property.setInColourMonopolyStatus(true);
        assertEquals(4, property.getRent());
        
        property.addHouse();
        assertEquals(10, property.getRent());
        
        property.addHouse();
        assertEquals(20, property.getRent());
        
        property.addHouse();
        assertEquals(40, property.getRent());
        
        property.addHouse();
        assertEquals(80, property.getRent());
        
        
        property.setUpgradeableToHotel(true);
        property.addHouse();
        assertTrue(property.getIsHotel());
        assertEquals(160, property.getRent());
        
        property.removeHouse();
        
        assertFalse(property.getIsHotel());
        assertEquals(80, property.getRent());
    }

    /**
     * Tests the bounds on the number of houses the property can have.
     */
    @Test
    public void testNumberOfHouses() {
        
        property.setDevelopableStatus(true);
        property.addHouse();
        assertEquals(1, property.getNumberOfHouses());
        
        property.addHouse();
        assertEquals(2, property.getNumberOfHouses());
        
        property.addHouse();
        assertEquals(3, property.getNumberOfHouses());
        
        property.addHouse();
        assertEquals(4, property.getNumberOfHouses());
        
        property.setUpgradeableToHotel(true);
        property.addHouse();
        assertEquals(0, property.getNumberOfHouses());
        
        property.removeHouse();
        assertEquals(4, property.getNumberOfHouses());
        
    }

    /**
     * Tests the pre-qualifier boolean regarding whether the property can be
     * upgraded to a Hotel.
     */
    @Test
    public void testSetUpgradeableToHotel() {
        
        property.setUpgradeableToHotel(true);
        assertFalse(property.getUpgradeableToHotel());
        
        property.setDevelopableStatus(true);
        property.addHouse();
        property.addHouse();
        property.addHouse();
        property.addHouse();
        property.setUpgradeableToHotel(true);
        assertTrue(property.getUpgradeableToHotel());
   
    }

    /**
     * Tests to ensure that the upgrade to hotel method functions correctly.
     */
    @Test
    public void testUpgradeToHotel() {
        
        assertFalse(property.getIsHotel());
        
        property.setDevelopableStatus(true);
        property.addHouse();
        property.addHouse();
        property.addHouse();
        property.addHouse();
        property.setUpgradeableToHotel(true);
        property.addHouse();
        assertTrue(property.getIsHotel());
        
    }

    /**
     * Tests that the downgrade from hotel method functions correctly.
     */
    @Test
    public void testDowngradeFromHotel() {
        
        property.setDevelopableStatus(true);
        property.addHouse();
        property.addHouse();
        property.addHouse();
        property.addHouse();
        property.setUpgradeableToHotel(true);
        property.addHouse();
        assertTrue(property.getIsHotel());
        
        property.removeHouse();
        assertFalse(property.getIsHotel());
        
    }

    /**
     * Tests that the specified and retrieved owners are equivalent.
     */
    @Test
    public void testOwnership() {
        
        assertNull(property.getOwner());
        assertFalse(property.getIsOwned());
        
        property.transferOwnership(player);
        assertEquals(player, property.getOwner());
        assertTrue(property.getIsOwned());
        
        property.removeOwnership();
        assertNull(property.getOwner());
        assertFalse(property.getIsOwned());
        
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
