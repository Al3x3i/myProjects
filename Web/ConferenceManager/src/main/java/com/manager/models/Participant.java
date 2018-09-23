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
public class Participant {

    private String id;
    private String first_name;
    private String second_name;
    private String conference_id;

    public Participant() {
    }

    public Participant(String id, String first_name, String second_name, String conference_id) {
        this.id = id;
        this.first_name = first_name;
        this.second_name = second_name;
        this.conference_id = conference_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getConference_id() {
        return conference_id;
    }

    public void setConference_id(String conference_id) {
        this.conference_id = conference_id;
    }



}
