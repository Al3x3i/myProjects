/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.models;

/**
 *
 * @author Al3x3i
 */
public class ConferenceModel {

    private String id;
    private String conference_name;
    private String expected_participants;
    private String room_id;
    
    public ConferenceModel()
    {
        
    }

    public ConferenceModel(String id, String conference_name, String expected_participants, String room_id) {
        this.id = id;
        this.conference_name = conference_name;
        this.expected_participants = expected_participants;
        this.room_id = room_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConference_name() {
        return conference_name;
    }

    public void setConference_name(String conference_name) {
        this.conference_name = conference_name;
    }

    public String getExpected_participants() {
        return expected_participants;
    }

    public void setExpected_participants(String expected_participants) {
        this.expected_participants = expected_participants;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    
}
