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
public class GameSettingsLoaderFactory {

    public enum FILETYPE {
        XML, SVC
    }

    private static GameSettingsLoaderFactory instance = new GameSettingsLoaderFactory();

    private GameSettingsLoaderFactory() {
    }

    public static GameSettingsLoaderFactory getInstance() {
        return instance;
    }

    public SettingsLoaderInterface makeFactory(FILETYPE fileType) {

        SettingsLoaderInterface settingsLoader = null;

        switch (fileType) {
            case XML:
                settingsLoader = new XMLSettingsLoader();
                break;
            case SVC:
                //TODO
                break;
            default:
                settingsLoader = null;
        }
        return settingsLoader;
    }
}
