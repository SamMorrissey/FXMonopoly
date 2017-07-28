/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.utils.interfacing;

import fxmonopoly.utils.StageManager;

/**
 * Enforces a method to set the StageManager of any class implementing this interface.
 * This is utilised by the StageManager to automatically pass itself to a newly
 * instantiated controller class, although the StageManager usage is to be defined in 
 * the implementing class.
 * <p>
 * Bear in mind that this interface is designed as an interface to be utilised by
 * the Controller classes, and manipulated only via the StageManager. No other classes
 * should need to implement or deal with any object of the Manageable type.
 * 
 * @author Sam P. Morrissey
 */
public interface Manageable {
    
    abstract void setStageManager(StageManager manager); 
    
}
