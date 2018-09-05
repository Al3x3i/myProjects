/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;

import java.util.Arrays;
import java.util.Scanner;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import tictactoe2.main.GameModel;
import tictactoe2.player.AIPlayer;
import tictactoe2.player.HumanPlayer;
import tictactoe2.player.Player;
import tictactoe2.settings.GameSettingsLoaderFactory;
import tictactoe2.settings.SettingsLoaderInterface;
import tictactoe2.settings.SettingsModel;

/**
 *
 * @author asasin
 */
public class AIPlayerTest {

    @Test
    public void testAIPlayerInitialization() {

        Player player = new AIPlayer(1, 'X', 5);

        player.getPlayerCharacter();
        player.getPlayerID();
        player.getSizeOfBoard();
        assertTrue("Test, character should be same", player.getPlayerCharacter() == 'X');
        assertTrue("Test, id should be same", player.getPlayerID() == 1);
        assertTrue("Test, board size should be same", player.getSizeOfBoard() == 5);
    }

    @Test
    public void testAIPlayerMovement() {

        SettingsLoaderInterface loader = GameSettingsLoaderFactory.getInstance().makeFactory(GameSettingsLoaderFactory.FILETYPE.XML);
        SettingsModel settingsModel = loader.loadSettings("gameSettings.xml");

        Player human1 = new HumanPlayer(0, 'Y', settingsModel.getBoardSize());
        Player human2 = new HumanPlayer(1, 'Y', settingsModel.getBoardSize());
        Player aIplayer = new AIPlayer(2, 'X', settingsModel.getBoardSize());

        GameModel gameModel = new GameModel(3, settingsModel.getBoardSize());
        gameModel.addPlayer(human1);
        gameModel.addPlayer(human2);
        gameModel.addPlayer(aIplayer);

        for (int[] row : gameModel.getGameMoves()) {
            Arrays.fill(row, gameModel.NOT_USED_CELL_VALUE);
        }
        Scanner scanner = new Scanner(System.in);

        String m = aIplayer.move(scanner, gameModel.getGameMoves()); //1
        String[] moves = m.split(",");
        int x = Integer.valueOf(moves[0]) - 1;
        int y = Integer.valueOf(moves[1]) - 1;
        gameModel.getGameMoves()[x][y] = aIplayer.getPlayerID();

        m = aIplayer.move(scanner, gameModel.getGameMoves()); //2
        moves = m.split(",");
        x = Integer.valueOf(moves[0]) - 1;
        y = Integer.valueOf(moves[1]) - 1;
        gameModel.getGameMoves()[x][y] = aIplayer.getPlayerID();

        m = aIplayer.move(scanner, gameModel.getGameMoves()); //3
        moves = m.split(",");
        x = Integer.valueOf(moves[0]) - 1;
        y = Integer.valueOf(moves[1]) - 1;
        gameModel.getGameMoves()[x][y] = aIplayer.getPlayerID();

        int checkCounter = 0;
        for (int[] row : gameModel.getGameMoves()) {

            for (int value : row) {
                if (value != gameModel.NOT_USED_CELL_VALUE) {
                    checkCounter++;
                }
            }
        }
        assertTrue("Test, wrong amount of movement by AI player", checkCounter == 3);
    }
}
