/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.config;

import com.manager.dao.ConferenceDAO;
import com.manager.dao.ConferenceRoomDAO;
import com.manager.dao.ParticipantDAO;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Al3x3i
 */
import javax.sql.DataSource;

@Configuration
public class DBConfig {

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:mydb.db");
        return dataSourceBuilder.build();
    }

    @Bean
    public ConferenceDAO conferenceDAO() {
        return new ConferenceDAO(dataSource());
    }
    
    @Bean
    public ConferenceRoomDAO conferenceRoomDAO() {
        return new ConferenceRoomDAO(dataSource());
    }
    
    @Bean
    public ParticipantDAO ParticipantDAO() {
        return new ParticipantDAO(dataSource());
    }
}
