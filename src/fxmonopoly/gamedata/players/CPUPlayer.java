/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.gamedata.players;

/**
 * Extends the Player abstract class, providing a concrete instantiation.
 * <p>
 * The main purpose of this class is to have actions and responses parsed via
 * the instanceof operator, since it provides no extra methods over Player.
 * @author Sam P. Morrissey
 */
public class CPUPlayer extends Player{
    
    public CPUPlayer(String name) {
        super(name);
    }
}
