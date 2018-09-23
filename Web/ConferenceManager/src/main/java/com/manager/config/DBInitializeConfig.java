/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.config;

import com.manager.dao.ConferenceRoomDAO;
import com.manager.models.ConferenceRoomModel;
import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

/**
 *
 * @author Al3x3i
 */
@Configuration
public class DBInitializeConfig {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initialize() throws IOException {
        Connection connection = null;
        try {

            System.out.println("INITIALIZE DATABASE");

            connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table';");
            if (rs.next()) {
                return; // the database already exist do re-create it
            }
            File file = new ClassPathResource("script.sql").getFile();
            Statement statement = connection.createStatement();
            ScriptUtils.executeSqlScript(connection, new FileSystemResource(file.getAbsoluteFile()));
            rs.close();
            statement.close();
//            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection(connection);
        }

        generateDummyConferenceRooms();
    }

    private void generateDummyConferenceRooms() {

        try {
            ConferenceRoomDAO dao = new ConferenceRoomDAO(dataSource);
            dao.addConferenceRoom(new ConferenceRoomModel("1", "Port_A", "6"));
            dao.addConferenceRoom(new ConferenceRoomModel("2", "Port_B", "10"));
            dao.addConferenceRoom(new ConferenceRoomModel("3", "Port_C", "15"));
            dao.addConferenceRoom(new ConferenceRoomModel("4", "Port_D", "20"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
