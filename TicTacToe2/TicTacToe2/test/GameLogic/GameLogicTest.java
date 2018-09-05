/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameLogic;

import java.util.Arrays;
import java.util.Scanner;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import tictactoe2.main.GameEngine;
import tictactoe2.main.GameLogic;
import tictactoe2.main.GameModel;
import tictactoe2.player.AIPlayer;
import tictactoe2.player.HumanPlayer;
import tictactoe2.player.Player;
import tictactoe2.settings.GameSettingsLoaderFactory;
import tictactoe2.settings.SettingsLoaderInterface;
import tictactoe2.settings.SettingsModel;

/**
 *
 * @author Al3x3i
 */
public class GameLogicTest {

    @Test
    public void testGameLogic() {

        SettingsLoaderInterface loader = GameSettingsLoaderFactory.getInstance().makeFactory(GameSettingsLoaderFactory.FILETYPE.XML);
        SettingsModel settingsModel = loader.loadSettings("gameSettings.xml");

        Player human1 = new HumanPlayer(0, 'Y', settingsModel.getBoardSize());

        GameModel gameModel = new GameModel(3, settingsModel.getBoardSize());

        for (int[] row : gameModel.getGameMoves()) {
            Arrays.fill(row, gameModel.NOT_USED_CELL_VALUE);
        }

        // check row
        gameModel.getGameMoves()[0][0] = human1.getPlayerID();
        assertFalse("Test, Should be no winner", GameLogic.checkWinner(human1.getPlayerID(), gameModel));
        gameModel.getGameMoves()[0][1] = human1.getPlayerID();
        assertFalse("Test, Should be no winner", GameLogic.checkWinner(human1.getPlayerID(), gameModel));
        gameModel.getGameMoves()[0][2] = human1.getPlayerID();
        assertTrue("Test, Should be winner", GameLogic.checkWinner(human1.getPlayerID(), gameModel));

        for (int[] row : gameModel.getGameMoves()) {
            Arrays.fill(row, gameModel.NOT_USED_CELL_VALUE);
        }

        //check diagonal
        gameModel.getGameMoves()[0][0] = human1.getPlayerID();
        assertFalse("Test, Should be no winner", GameLogic.checkWinner(human1.getPlayerID(), gameModel));
        gameModel.getGameMoves()[1][1] = human1.getPlayerID();
        assertFalse("Test, Should be no winner", GameLogic.checkWinner(human1.getPlayerID(), gameModel));
        gameModel.getGameMoves()[2][2] = human1.getPlayerID();
        assertTrue("Test, Should be winner", GameLogic.checkWinner(human1.getPlayerID(), gameModel));

        for (int[] row : gameModel.getGameMoves()) {
            Arrays.fill(row, gameModel.NOT_USED_CELL_VALUE);
        }

        //check column
        gameModel.getGameMoves()[0][0] = human1.getPlayerID();
        assertFalse("Test, Should be no winner", GameLogic.checkWinner(human1.getPlayerID(), gameModel));
        gameModel.getGameMoves()[1][0] = human1.getPlayerID();
        assertFalse("Test, Should be no winner", GameLogic.checkWinner(human1.getPlayerID(), gameModel));
        gameModel.getGameMoves()[2][0] = human1.getPlayerID();
        assertTrue("Test, Should be winner", GameLogic.checkWinner(human1.getPlayerID(), gameModel));
    }
}
