/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe2.main;

import tictactoe2.player.Player;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Al3x3i
 */
public class GameEngine {

    public GameStatus gameStatus = GameStatus.playing;

    Scanner scanner = new Scanner(System.in);
    GameModel gameModel;

    public GameEngine(GameModel gameModel) {
        this.gameModel = gameModel;
        this.initGame();
    }

    private void initGame() {
        // Set all cells to -1
        for (int[] row : gameModel.getGameMoves()) {
            Arrays.fill(row, gameModel.NOT_USED_CELL_VALUE);
        }
        this.gameStatus = GameStatus.playing;
        printBoard();
        playGame();
    }

    public void printBoard() {

        StringBuilder rowDelimiter = new StringBuilder();
        StringBuilder columnNr = new StringBuilder();
        columnNr.append("  ");
        rowDelimiter.append(" ");

        int boardSize = this.gameModel.getBoardSize();
        //Generate visualDelimeter '+---+'
        for (int columnIndex = 0; columnIndex < boardSize; columnIndex++) {
            rowDelimiter.append("+---");
            columnNr.append("  " + (columnIndex + 1) + " ");
        }
        rowDelimiter.append("+");

        System.out.println(columnNr);

        for (int rowIndex = 0; rowIndex < boardSize; rowIndex += 1) {
            System.out.println(" " + rowDelimiter);
            System.out.print((rowIndex + 1) + " ");

            for (int columnIndex = 0; columnIndex < boardSize; columnIndex++) {

                int playerID = this.gameModel.getGameMoves()[rowIndex][columnIndex];

                if (playerID < 0) {
                    System.out.printf("| %s ", " ");
                } else {
                    System.out.printf("| %s ", this.gameModel.getGamePlayer(playerID).getPlayerCharacter());
                }
            }
            System.out.println("|");
        }
        System.out.println(" " + rowDelimiter);
    }

    private void playGame() {

        int playerIndex = 0;
        int movesCounter = 0;
        while (gameStatus == GameStatus.playing) {

            Player currentPlayer = this.gameModel.getGamePlayer(playerIndex);
            String playerMove = currentPlayer.move(this.scanner, this.gameModel.getGameMoves());

            boolean isTurnAccepted = false;
            int colValue = -1;
            int rowValue = -1;
            if (playerMove.length() >= 3) {
                String[] temp = playerMove.split(",");
                if (temp.length == 2) {

                    colValue = temp[0].matches("\\d+") ? Integer.parseInt(temp[0]) : -1;
                    rowValue = temp[1].matches("\\d+") ? Integer.parseInt(temp[1]) : -1;

                    int max = Math.max(colValue, rowValue);
                    int min = Math.max(colValue, rowValue);
                    if (max <= this.gameModel.getBoardSize() && min >= 1) {

                        // check wheter this cell is ocupied or not
                        if (this.gameModel.getGameMoves()[colValue - 1][rowValue - 1] == this.gameModel.NOT_USED_CELL_VALUE) {
                            this.gameModel.getGameMoves()[colValue - 1][rowValue - 1] = currentPlayer.getPlayerID();
                            isTurnAccepted = true;
                        }
                    }
                }
            }

            if (!isTurnAccepted) {
                if (playerMove.length() == 1 && playerMove.toUpperCase().equals("Q")) {
                    gameStatus = GameStatus.quiting;
                } else {
                    System.out.println("Warning, entered format is not accepted. Please repeat again.");
                }
                continue;
            } else {
                movesCounter++;

                // validate game result
                boolean winner = hasWinner(currentPlayer.getPlayerID());
                if (winner) {
                    System.out.printf("Player: %s won the game. Congratualtions", currentPlayer.getPlayerID() + 1);
                    System.out.println();
                    gameStatus = GameStatus.winer;
                } else if (movesCounter == this.gameModel.getBoardSize() * this.gameModel.getBoardSize()) {
                    gameStatus = GameStatus.draw;
                }
            }

            printBoard();

            if (playerIndex + 1 == this.gameModel.getGamePlayers().length) {
                playerIndex = 0;
            } else {
                playerIndex++;
            }
        }

        if (gameStatus != GameStatus.quiting) {
            this.gameEnded();
        }
    }

    private void gameEnded() {

        boolean isNewGame = false;
        while (true) {
            System.out.print("Do you want to repeat the game? Enter 'yes' or 'no': ");
            String answer = this.scanner.next();
            if (answer.toLowerCase().equals("yes")) {
                isNewGame = true;
                break;
            } else if (answer.toLowerCase().equals("no")) {
                gameStatus = GameStatus.quiting;
                break;
            }
        }
        if (isNewGame) {
            this.initGame();
        }
    }

    public boolean hasWinner(int playerID) {

        return GameLogic.checkWinner(playerID, this.gameModel);
    }
}
