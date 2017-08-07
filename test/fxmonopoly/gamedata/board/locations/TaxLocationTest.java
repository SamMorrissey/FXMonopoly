/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.board.locations;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests the TaxLocaiton class.
 * @author Sam P. Morrissey
 */
public class TaxLocationTest {
    
    TaxLocation tax = new TaxLocation("Testing", 200);
    
    /**
     * Tests to ensure that the tax value is reverted to the equivalent negative
     * value if a positive value is entered, since this value will be added to a
     * player's cash and tax doesn't increase money.
     */
    @Test
    public void testValueRevert() {
        assertEquals(-200, tax.getValue());
    }
    
    /**
     * Ensures that the name retrieved via the getName method and the entered name
     * are actually equivalent.
     */
    @Test
    public void testGetName() {
        assertEquals("Testing", tax.getName());
    }
    
}
