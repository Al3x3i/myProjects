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

/**
 *
 * @author asasin
 */
public class GameSettingsLoaderFactoryTest {

    @Test
    public void testSettingLoaderFactory() {

        SettingsLoaderInterface loader = GameSettingsLoaderFactory.getInstance().makeFactory(GameSettingsLoaderFactory.FILETYPE.XML);

        assertTrue("Test, Loader should be not empty", loader != null);

    }
}
