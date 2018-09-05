/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe2.settings;

import java.io.File;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 *
 * @author asasin
 */
public class XMLSettingsLoader implements SettingsLoaderInterface {

    @Override
    public SettingsModel loadSettings(String fileName) {

        if (fileName == null || fileName.isEmpty()) {
            return null;
        }

        try {

            //URL fileUrl = getClass().getResource(fileName);

            File xmlFile = new File(fileName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            //Reduction of Redundancies, whitespaces,line breaks, superflows
            document.getDocumentElement().normalize();

            String root_node = document.getDocumentElement().getNodeName();

            if (root_node.equals(SettingsModel.ROOT_SETTINGS)) {

                String boardSizeTemp = document.getElementsByTagName(SettingsModel.BOARD_SIZE).item(0).getTextContent();
                String playerOne = document.getElementsByTagName(SettingsModel.FIRST_PLAYER_CHARACTER).item(0).getTextContent();
                String playerTwo = document.getElementsByTagName(SettingsModel.SECOND_PLAYER_CHARACTER).item(0).getTextContent();
                String playerAI = document.getElementsByTagName(SettingsModel.AI_PLAYER_CHARACTER).item(0).getTextContent();

                int boardSize = boardSizeTemp.matches("\\d+") ? Integer.parseInt(boardSizeTemp) : -1;

                SettingsModel model = new SettingsModel(boardSize, playerOne.charAt(0), playerTwo.charAt(0), playerAI.charAt(0));
                return model;
            }
        } catch (Exception ex) {
            System.out.println("Error, occured error while reading settings from xml file");
            return null;
        }

        return null;
    }
}
