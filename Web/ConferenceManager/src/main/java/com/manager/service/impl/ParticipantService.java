/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.service.impl;

import com.manager.dao.ConferenceDAO;
import com.manager.dao.ParticipantDAO;
import com.manager.dto.ConferenceDTO;
import com.manager.models.Participant;
import com.manager.service.IParticipantService;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Al3x3i
 */
@Service
public class ParticipantService implements IParticipantService {

    @Autowired
    private ParticipantDAO participantDAO;

    @Autowired
    private ConferenceDAO conferenceDAO;

    @Override
    public List<Participant> getParticipantConference(String conference_id) {
        return participantDAO.getParticipantConference(conference_id);
    }

    @Override
    public boolean addParticipant(String f_name, String s_name, String conference_id) {

        try {
            if (f_name.isEmpty() || s_name.isEmpty()) {
                return false;
            }

            ConferenceDTO conferenceData = conferenceDAO.getConferenceRoomParticipant(conference_id);

            if (conferenceData != null && !StringHelper.isNullOrEmptyString(conferenceData.getMax_size()) && !StringHelper.isNullOrEmptyString(conferenceData.getRegistered_participants())) {
                int totalRegistered = Integer.valueOf(conferenceData.getRegistered_participants());
                int max = Integer.valueOf(conferenceData.getMax_size());
                if (totalRegistered >= max) {
                    return false;
                }
            }else{
                return false;
            }

            return participantDAO.addParticipant(f_name, s_name, conference_id);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteParticipant(String participant_id, String conference_id) {
        try {
            return participantDAO.deleteParticipant(participant_id, conference_id);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
