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
    
    MAIN_MENU ("fxmonopoly/mainmenu/MainMenuLayout.fxml"),
    GAME_INIT ("fxmonopoly/gameinitsettings/GameInitSettingsLayout.fxml"),
    GAME ("fxmonopoly/game/GameLayout.fxml"),
    TRADE ("fxmonopoly/trade/TradeLayout.fxml");
    
    /**
     * A string format relative URL path to the applicable fxml file.
     * @return The applicable fxml filepath.
     */
    String getFXMLPath() { return fxmlPath; }

    View(String fxmlPath) { this.fxmlPath = fxmlPath; }

    private String fxmlPath;
}
