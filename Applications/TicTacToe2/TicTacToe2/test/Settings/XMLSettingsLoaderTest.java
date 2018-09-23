/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import tictactoe2.settings.GameSettingsLoaderFactory;
import tictactoe2.settings.SettingsLoaderInterface;
import tictactoe2.settings.SettingsModel;

/**
 *
 * @author asasin
 */
public class XMLSettingsLoaderTest {
    @Test
    public void testXMLSettingsLoader() {

        SettingsLoaderInterface loader = GameSettingsLoaderFactory.getInstance().makeFactory(GameSettingsLoaderFactory.FILETYPE.XML);
        SettingsModel model = loader.loadSettings("gameSettings.xml");
        
        assertTrue("Test, The models should not be empty", model != null);

    }
}
