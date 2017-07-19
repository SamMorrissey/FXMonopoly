/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMonopoly.Utils;

/**
 *
 * @author Sam P. Morrissey
 */
public enum View {
    
    MAIN_MENU {
        @Override
        String getFXMLPath() {
            return "/MainMenu/MainMenuLayout.fxml";
        }
    },
    GAME_INIT {
        @Override
        String getFXMLPath() {
            return "/GameInitSettings/GameInitSettingsLayout.fxml";
        }
    },
    GAME {
        @Override
        String getFXMLPath() {
            return "/Game/GameLayout.fxml";
        }
    },
    TRADE {
        @Override
        String getFXMLPath() {
            return "/Trade/TradeLayout.fxml";
        }
    };
    
    abstract String getFXMLPath();
}
