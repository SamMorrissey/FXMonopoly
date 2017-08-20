/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.utils.interfacing;

import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 * This interface is aimed at enforcing a late data passing potential within
 * any implementing class. By this it refers to data that cannot be passed via
 * a constructor, but is necessary to be passed between different Scenes.
 * <p>
 * Within the context of this program the main usage is that of the GameController
 * class, which is passed initial data from the GameInitSettingsController. By 
 * implementing a method that returns a LateData object, the StageManager is able
 * to avoid exposing the Controller class to any unwanted manipulation.
 * 
 * @author Sam P. Morrissey
 */
public interface LateData {
    abstract void lateDataPass(String sprite, String colourName, String name, ArrayList<ObservableList<String>> array);
}
