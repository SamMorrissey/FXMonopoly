/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.game.utils.controller;


import fxmonopoly.gamedata.board.locations.*;
import fxmonopoly.gamedata.players.UserPlayer;
import javafx.scene.control.Button;

/**
 * Defines the buttons utilised on the board. Have a location and boolean values
 * associated with them to make retrieving a location simpler.
 * @author Sam P. Morrissey
 */
public class BoardButton extends Button {
    
    private boolean ownable;
    private Location location; 
    
    /**
     * Sets the ownable status of this BoardButton to the specified input.
     * @param ownable The ownable status.
     */
    public void setOwnable(boolean ownable) {
        this.ownable = ownable;
    }
    
    /**
     * Sets the location of this BoardButton.
     * @param location The location attached to this BoardButton.
     */
    public void setLocation(Location location) {
        if(ownable) {
            this.location = location;
        }
    }

    /**
     * Retrieves the location of this BoardButton.
     * @return The location of this BoardButton.
     */
    public Location getLocation() {
        return location;
    }
    
    /**
     * Retrieves the isOwnable boolean.
     * @return True if the location can be owned, false otherwise.
     */
    public boolean isOwnable() {
        return ownable;
    }
    
    /**
     * Retrieves whether this property is owned.
     * @return True if the location is owned, false otherwise.
     */
    public boolean isOwned() {
        if(ownable) {
            if(location instanceof PropertyLocation) {
                return ((PropertyLocation) location).getIsOwned();
            }
            else if(location instanceof RailwayLocation) {
                return ((RailwayLocation) location).getIsOwned();
            }
            else if(location instanceof UtilityLocation) {
                return ((UtilityLocation) location).getIsOwned();
            }
        }
        return false;
    }
    
    public boolean userIsOwner(UserPlayer user) {
        if(ownable) {
            if(location instanceof PropertyLocation) {
                return ((PropertyLocation) location).getOwner() == user;
            }
            else if(location instanceof RailwayLocation) {
                return ((RailwayLocation) location).getOwner() == user;
            }
            else if(location instanceof UtilityLocation) {
                return ((UtilityLocation) location).getOwner() == user;
            }
        }
        return false;
    }
    
}
