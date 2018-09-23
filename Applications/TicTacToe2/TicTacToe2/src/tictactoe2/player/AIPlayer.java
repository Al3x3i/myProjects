/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe2.player;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import tictactoe2.main.GameModel;

/**
 *
 * @author Al3x3i
 */
public class AIPlayer extends Player {

    private boolean isFirstMoveDone = false;
    private int lastMoveRow = -1;
    private int lastMoveColumn = -1;

    private int[][] preferredMoves;
    private Integer[] preferredRowMoves;

    public AIPlayer(int playerID, char playerCharacter, int sizeOfBoard) {
        super(playerID, playerCharacter, sizeOfBoard);
        this.generatePreferedMoves();
    }

    private void generatePreferedMoves() {
        preferredMoves = new int[this.sizeOfBoard][this.sizeOfBoard];
        preferredRowMoves = new Integer[sizeOfBoard];

        Integer[] columnArray = new Integer[sizeOfBoard];

        for (int index = 0; index < sizeOfBoard; index++) {
            columnArray[index] = index;
            preferredRowMoves[index] = index;
        }

        Collections.shuffle(Arrays.asList(preferredRowMoves));

        for (int rowIndex = 0; rowIndex < sizeOfBoard; rowIndex++) {
            Collections.shuffle(Arrays.asList(columnArray));
            for (int columnIndex = 0; columnIndex < sizeOfBoard; columnIndex++) {
                preferredMoves[rowIndex][columnIndex] = columnArray[columnIndex];
            }
        }
    }

    @Override
    public String move(Scanner scanner, int[][] gameMoves) {

        if (!isFirstMoveDone) {
            Random random = new Random();
            lastMoveRow = random.nextInt(this.sizeOfBoard) + 1;
            lastMoveColumn = random.nextInt(this.sizeOfBoard) + 1;
            isFirstMoveDone = true;
        } else {

            boolean isFound = false;
            for (int x = 0; x < this.sizeOfBoard; x++) {

                int tempRow = preferredRowMoves[x];

                for (int y = 0; y < this.sizeOfBoard; y++) {

                    int tempColumn = preferredMoves[tempRow][y];

                    if (gameMoves[tempRow][tempColumn] == GameModel.NOT_USED_CELL_VALUE) {
                        lastMoveRow = tempRow + 1;
                        lastMoveColumn = tempColumn + 1;
                        isFound = !isFound;
                        break;
                    }
                }
                if (isFound) {
                    break;
                }
            }
        }
        return lastMoveRow + "," + lastMoveColumn;
    }
}
