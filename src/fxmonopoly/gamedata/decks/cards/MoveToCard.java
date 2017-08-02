/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.decks.cards;

/**
 * Details a Community Chest Card that specifies that the player move to a
 * determined location on the board. 
 * @author Sam P. Morrissey
 */
public class MoveToCard extends Card{
    private final int moveLocation;
    
    public MoveToCard(String description, int location) {
        super(description);
        if(location > 39) {
            this.moveLocation = 39;
        }
        else if(location < 0) {
            this.moveLocation = 0;
        }
        else {
            this.moveLocation = location;
        }
    }
    
    public int getMoveLocation() {
        return moveLocation;
    }
}
