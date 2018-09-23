/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe2.main;

import tictactoe2.player.AIPlayer;
import tictactoe2.player.HumanPlayer;
import tictactoe2.settings.GameSettingsLoaderFactory;
import tictactoe2.settings.SettingsLoaderInterface;
import tictactoe2.settings.SettingsModel;
import tictactoe2.settings.ValidateSettingsModel;

/**
 *
 * @author Al3x3i
 */
public class Tictactoe2 {

    /**
     * @param args the command line arguments
     */
    private static final String SETTINS_FILE_NAME = "gameSettings.xml";

    public static void main(String[] args) {

        SettingsLoaderInterface loader = GameSettingsLoaderFactory.getInstance().makeFactory(GameSettingsLoaderFactory.FILETYPE.XML);
        SettingsModel gameSettings = loader.loadSettings(SETTINS_FILE_NAME);

        boolean isValid = false;
        GameModel gameModel = null;
        if (ValidateSettingsModel.validateSettingsModel(gameSettings)) {

            gameModel = new GameModel(gameSettings.getTotalPlayers(), gameSettings.getBoardSize());
            isValid = gameModel.addPlayer(new HumanPlayer(0, gameSettings.getPlayerOneChar(), gameSettings.getBoardSize()));
            isValid = isValid && gameModel.addPlayer(new HumanPlayer(1, gameSettings.getPlayerTwoChar(), gameSettings.getBoardSize()));
            isValid = isValid && gameModel.addPlayer(new AIPlayer(2, gameSettings.getAiPlayerChar(), gameSettings.getBoardSize()));
        }

        if (!isValid) {
            System.out.println("Error, occured error while creating a game. Please check game settings");
        } else {
            System.out.println("Welcome to Tic Tac Toe 2.0 game");
            System.out.println("To quit the game please enter 'Q'");
            GameEngine engine = new GameEngine(gameModel);
        }
    }

}
