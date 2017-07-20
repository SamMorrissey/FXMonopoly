/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmonopoly.utils;

/**
 *
 * @author Sam P. Morrissey
 */
public enum View {
    
    MAIN_MENU {
        @Override
        String getFXMLPath() {
            return "/mainmenu/MainMenuLayout.fxml";
        }
    },
    GAME_INIT {
        @Override
        String getFXMLPath() {
            return "/gameinitsettings/GameInitSettingsLayout.fxml";
        }
    },
    GAME {
        @Override
        String getFXMLPath() {
            return "/game/GameLayout.fxml";
        }
    },
    TRADE {
        @Override
        String getFXMLPath() {
            return "/trade/TradeLayout.fxml";
        }
    };
    
    abstract String getFXMLPath();
}
