/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe2.main;

import tictactoe2.player.Player;

/**
 *
 * @author Al3x3i
 */
public class GameModel {

    public final static int NOT_USED_CELL_VALUE = -1;
    private Player[] gamePlayers;
    private int boardSize = 0;
    private int[][] gameMoves;

    public GameModel(int totalPlayers, int boardSize) {
        this.gamePlayers = new Player[totalPlayers];
        this.gameMoves = new int[boardSize][boardSize];
        this.boardSize = boardSize;
    }

    public boolean addPlayer(Player p) {
        
        for (int i = 0; i < gamePlayers.length; i++) {
            Player player = gamePlayers[i];
            if (player == null) {
                gamePlayers[i] = p;
                return true;
            }
        }
        
        for (Player player : gamePlayers) {
            if (player == null) {
                player = p;
                return true;
            }
        }
        return false;
    }

    public Player[] getGamePlayers() {
        return gamePlayers;
    }
    public Player getGamePlayer(int index) {
        return gamePlayers[index];
    }

    public int[][] getGameMoves() {
        return gameMoves;
    }

    public void setGameMoves(int[][] gameMoves) {
        this.gameMoves = gameMoves;
    }

    public int getBoardSize() {
        return boardSize;
    }
    
    
}
