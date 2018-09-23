/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe2.settings;

import tictactoe2.settings.SettingsModel;

/**
 *
 * @author Al3x3i
 */
public class ValidateSettingsModel {

    private final static int minBoardSize = 3;
    private final static int maxBoardSize = 10;

    private ValidateSettingsModel() {
    }

    public static boolean validateSettingsModel(SettingsModel model) {

        if (model.getBoardSize() >= minBoardSize && model.getBoardSize() <= maxBoardSize) {

            if (!Character.isWhitespace(model.getPlayerOneChar())) {
                if (!Character.isWhitespace(model.getPlayerTwoChar())) {
                    if (!Character.isWhitespace(model.getAiPlayerChar())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
