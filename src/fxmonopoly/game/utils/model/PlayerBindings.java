/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.model;

import fxmonopoly.game.GameModel;

/**
 * Creates and removes bindings that enforce the correct state of the active player
 * variables.
 * <p>
 * Exclusively contains static methods since this has no reason to own any game
 * state, as a purely functional utility.
 * @author Sam P. Morrissey
 */
public class PlayerBindings {
    
    /**
     * Prevents this class from being unnecessarily instantiated.
     */
    private PlayerBindings() {}
    
    /**
     * Creates the property bindings. Binds must be cleared at the end of the turn
     * to ensure that no unnecessary binds remain.
     * @param model The model to operate on.
     */
    public static void generateBindings(GameModel model) {
        model.getActivePlayerLocationNameProperty()
             .bind(model.retrieveLocation(model.getActivePlayer().getPosition()).getNameProperty());
        
        model.getActivePlayerCashProperty()
             .bind(model.getActivePlayer().getCashProperty());
        
        model.getActivePlayerNameProperty()
             .setValue(model.getActivePlayer().getName());
        
        model.getUserIsActiveProperty()
             .setValue(model.userIsActive());
        
    } 
    
    /**
     * Removes the property bindings.
     * @param model The model to operate on.
     */
    public static void clearBindings(GameModel model) {
        if(model.getActivePlayerLocationNameProperty() != null &&
           model.getActivePlayerCashProperty() != null) {
            
            model.getActivePlayerLocationNameProperty().unbind();
            model.getActivePlayerCashProperty().unbind();
        }
    }
    
    
}
