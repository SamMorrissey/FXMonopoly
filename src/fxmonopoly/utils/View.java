/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.utils;


/**
 * Each enum defines a specific Scene (or more specifically Parent/Root node) to
 * be generated via the specified fxml paths.
 * <p>
 * Exclusively utilised by the StageManager changeScene(View) method, which itself
 * can be called from any class that has its own StageManager reference.
 * @author Sam P. Morrissey
 */
public enum View {
    
    MAIN_MENU {
        @Override
        String getFXMLPath() { 
            return "fxmonopoly/mainmenu/MainMenuLayout.fxml";
        }
    },
    GAME_INIT {
        @Override
        String getFXMLPath() {
            return "fxmonopoly/gameinitsettings/GameInitSettingsLayout.fxml";
        }
    },
    GAME {
        @Override
        String getFXMLPath() {
            return "fxmonopoly/game/GameLayout.fxml";
        }
    },
    TRADE {
        @Override
        String getFXMLPath() {
            return "fxmonopoly/trade/TradeLayout.fxml";
        }
    };
    
    /**
     * A string format relative URL path to the applicable fxml file.
     * @return The applicable fxml filepath.
     */
    abstract String getFXMLPath();
}
