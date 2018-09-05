/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings;

import static org.junit.Assert.assertFalse;
import tictactoe2.settings.SettingsModel;
import tictactoe2.settings.ValidateSettingsModel;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Al3x3i
 */
public class SettingsTest {

    @Test
    public void testSettingValidation() {

        char playerOne = 'X';
        char playerTwo = 'Y';
        char playerAI = 'Z';

        SettingsModel stModel = new SettingsModel(3, playerOne, playerTwo, playerAI);

        //Test valid settings
        boolean isValid = ValidateSettingsModel.validateSettingsModel(stModel);
        assertTrue("Valid settings failed", isValid);

        //Test invalid settings
        stModel = new SettingsModel(1, playerOne, playerTwo, playerAI);
        isValid = ValidateSettingsModel.validateSettingsModel(stModel);
        assertFalse("Invalid settings failed", isValid);

        stModel = new SettingsModel(5, ' ', playerTwo, playerAI);
        isValid = ValidateSettingsModel.validateSettingsModel(stModel);
        assertFalse("Invalid settings failed", isValid);

        stModel = new SettingsModel(11, playerOne, playerTwo, playerAI);
        isValid = ValidateSettingsModel.validateSettingsModel(stModel);
        assertFalse("Invalid settings failed", isValid);
    }
}
