/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.utils;

/**
 * Enforces a method to set the StageManager of any class implementing this interface.
 * This is utilised by the StageManager to automatically pass itself to a newly
 * instantiated controller class, although the StageManager usage is to be defined in 
 * the implementing class.
 * @author Sam P. Morrissey
 */
public interface Manageable {
    
    abstract void setStageManager(StageManager manager);
    
}
