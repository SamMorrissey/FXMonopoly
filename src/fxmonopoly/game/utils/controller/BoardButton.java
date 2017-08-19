/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.controller;


import fxmonopoly.gamedata.board.locations.*;
import javafx.scene.control.Button;

/**
 *
 * @author Sam P. Morrissey
 */
public class BoardButton extends Button {
    
    private boolean ownable;
    private Location location; 
    
    public void setOwnable(boolean ownable) {
        this.ownable = ownable;
    }
    
    public void setLocation(Location location) {
        if(ownable) {
            this.location = location;
        }
    }

    public Location getLocation() {
        return location;
    }
    
}
