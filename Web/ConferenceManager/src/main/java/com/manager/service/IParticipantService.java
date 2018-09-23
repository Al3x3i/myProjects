/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.service;

import com.manager.models.Participant;
import java.util.List;

/**
 *
 * @author Al3x3i
 */
public interface IParticipantService {

    List<Participant> getParticipantConference(String conference_id);

    boolean addParticipant(String f_name, String s_name, String conference_id);

    boolean deleteParticipant(String participant_id, String conference_id);

}
