/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.die;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Die class.
 * @author Sam P. Morrissey
 */
public class DieTest {
    
    Die die;
    
    @Before
    public void setUp() {
        die = new Die();
    }
    
    @After
    public void tearDown() {
        die = null;
    }

    /**
     * Tests that the first die returns a value above zero and no greater than
     * six.
     */
    @Test
    public void testRollFirstDie() {
        int i;
        for(i = 0; i < 20; i++) {
            int j = die.rollFirstDie();
            assertTrue(0 < j && j <= 6);
        }
    }

    /**
     * Tests that the first die last value is set and retrieved correctly.
     */
    @Test
    public void testGetDieOneLastValue() {
        int i = die.rollFirstDie();
        assertEquals(i, die.getDieOneLastValue());
    }

    /**
     * Tests that the second die returns a value above zero and no greater than
     * six.
     */
    @Test
    public void testRollSecondDie() {
        int i;
        for(i = 0; i < 20; i++) {
            int j = die.rollSecondDie();
            assertTrue(0 < j && j <= 6);
        }
    }

    /**
     * Tests that the second die last value is set and retrieved correctly.
     */
    @Test
    public void testGetDieTwoLastValue() {
        int i = die.rollSecondDie();
        assertEquals(i, die.getDieTwoLastValue());
    }

    /**
     * Tests that the die roll total is calculated and retrieved correctly.
     */
    @Test
    public void testDieRollTotal() {
        int i = die.rollFirstDie();
        i += die.rollSecondDie();
        
        assertEquals(i, die.dieRollTotal());
    }

    /**
     * Tests that the expected and retrieved strings are equivalent.
     */
    @Test
    public void testSpecificImageString() {
        assertEquals("fxmonopoly/resources/images/die/1.png", die.specificImageString(1));
    }

    /**
     * Tests that the blank image string is retrieved correctly.
     */
    @Test
    public void testBlankImageString() {
        assertEquals("fxmonopoly/resources/images/die/Blank.png", die.blankImageString());
    }
    
    /**
     * Tests that the isDoubles method performs as expected over 50 iterations.
     * 
     * Has been tested over a billion iterations in one go. That's 36.497 seconds
     * gone, vanished into the aether.
     */
    @Test
    public void testIsDoubles() {
        
        int i;
        for(i = 0; i < 50; i++) {
            die.rollFirstDie();
            die.rollSecondDie();
            
            if(die.getDieOneLastValue() == die.getDieTwoLastValue()) {
                
                // This method utilises the first die to generate the random integer
                // used in the string, performed before the assert to ensure that it
                // does not affect the results, as intended.
                die.randomImageString();
                
                assertTrue(die.isDoubles());
            }
            else {
                assertFalse(die.isDoubles());
            }
        }
    }
}
