/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe2.settings;

/**
 *
 * @author asasin
 */
public class SettingsModel {

    public static final String ROOT_SETTINGS = "settings";
    public static final String BOARD_SIZE = "boardsize";
    public static final String FIRST_PLAYER_CHARACTER = "firstplayercharacter";
    public static final String SECOND_PLAYER_CHARACTER = "secondplayercharacter";
    public static final String AI_PLAYER_CHARACTER = "aiplayercharacter";

    public static final String PLAYER_ATR_ID = "id";
    private int totalPlayers = -1;

    private int boardSize;
    private char playerOneChar;
    private char playerTwoChar;
    private char aiPlayerChar;


    public SettingsModel(int boardSize, char playerOneChar, char playerTwoChar, char aiPlayerChar) {
        this.boardSize = boardSize;
        this.totalPlayers = 3;
        this.playerOneChar = playerOneChar;
        this.playerTwoChar = playerTwoChar;
        this.aiPlayerChar = aiPlayerChar;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public char getPlayerOneChar() {
        return playerOneChar;
    }

    public char getPlayerTwoChar() {
        return playerTwoChar;
    }

    public char getAiPlayerChar() {
        return aiPlayerChar;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }
}
