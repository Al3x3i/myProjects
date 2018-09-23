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
public class ConferenceRoomModel {
    
    private String id;
    private String room_name;
    private String max_size;

    public ConferenceRoomModel(String id, String room_name, String max_size) {
        this.id = id;
        this.room_name = room_name;
        this.max_size = max_size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
