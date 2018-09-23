/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.dto;

/**
 *
 * @author Al3x3i
 */
public class ConferenceDTO {

    private String id;
    private String conference_name;
    private String expected_participants;
    private String room_id;
    private String room_name;
    private String max_size;
    private String registered_participants;

    public ConferenceDTO() {
    }

    public ConferenceDTO(String id, String conference_name, String expected_participants, String room_id, String room_name, String max_size, String registered_participants) {
        this.id = id;
        this.conference_name = conference_name;
        this.expected_participants = expected_participants;
        this.room_id = room_id;
        this.room_name = room_name;
        this.max_size = max_size;
        this.registered_participants = registered_participants;
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

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getMax_size() {
        return max_size;
    }

    public void setMax_size(String max_size) {
        this.max_size = max_size;
    }

    public String getRegistered_participants() {
        return registered_participants;
    }

    public void setRegistered_participants(String registered_participants) {
        this.registered_participants = registered_participants;
    }


}
