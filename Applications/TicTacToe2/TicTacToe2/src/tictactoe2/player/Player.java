/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe2.player;

import java.util.Scanner;

/**
 *
 * @author Al3x3i
 */
public abstract class Player {

    protected int playerID;
    protected String name;
    protected char playerCharacter;
    protected int sizeOfBoard;

    public Player(int playerID, char playerCharacter, int sizeOfBoard) {
        this.playerID = playerID;
        this.playerCharacter = playerCharacter;
        this.sizeOfBoard = sizeOfBoard;
    }

    public String move(Scanner scanner, int[][] gameMoves) {
        System.out.printf("Player %s, your turn (row, column):", this.playerID + 1);
        String playerMove = scanner.next();
        return playerMove;
    }

    public int getPlayerID() {
        return playerID;
    }

    public char getPlayerCharacter() {
        return playerCharacter;
    }

    public int getSizeOfBoard() {
        return sizeOfBoard;
    }

}
