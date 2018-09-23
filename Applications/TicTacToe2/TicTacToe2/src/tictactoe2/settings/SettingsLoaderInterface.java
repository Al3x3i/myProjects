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
public interface SettingsLoaderInterface {
    
    public SettingsModel loadSettings(String fileName);
    
}
