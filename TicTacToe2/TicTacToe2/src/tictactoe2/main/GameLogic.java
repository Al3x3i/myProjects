/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe2.main;

/**
 *
 * @author Al3x3i
 */
public class GameLogic {
    
    public static boolean checkWinner(int playerID, GameModel gameModel){
        int checkColumn = 0;
        int checkRow = 0;

        //Check row and Column
        for (int i = 0; i < gameModel.getBoardSize(); i++) {
            checkRow = 0;
            checkColumn = 0;
            for (int j = 0; j < gameModel.getBoardSize(); j++) {

                if (gameModel.getGameMoves()[i][j] == playerID) {
                    checkRow++;
                }
                
                if (gameModel.getGameMoves()[j][i] == playerID) {
                    checkColumn++;
                }
            }
            if (checkRow == gameModel.getBoardSize() || checkColumn == gameModel.getBoardSize()) {
                return true;
            }
        }

        int checkCounter = 0;

        //check diagonal right
        for (int index = 0; index < gameModel.getGameMoves().length; index++) {
            if (gameModel.getGameMoves()[index][index] == playerID) {
                checkCounter++;
            }
        }
        if (checkCounter == gameModel.getGameMoves().length) {
            return true;
        }

        //check diagonal left
        for (int i = gameModel.getGameMoves().length - 1, j = gameModel.getGameMoves().length - 1; i >= 0; i--, j--) {
            if (gameModel.getGameMoves()[i][j] == playerID) {
                checkCounter++;
            }
        }
        if (checkCounter == gameModel.getGameMoves().length) {
            return true;
        }
        return false;
    }
}
