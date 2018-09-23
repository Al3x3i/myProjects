/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.service.impl;

import com.manager.dao.ConferenceDAO;
import com.manager.dao.ConferenceRoomDAO;
import com.manager.dto.ConferenceDTO;
import com.manager.models.ConferenceModel;
import com.manager.models.ConferenceRoomModel;
import com.manager.service.IConferenceService;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Al3x3i
 */
@Service
public class ConferenceService implements IConferenceService {

    @Autowired
    private ConferenceDAO conferenceDAO;

    @Autowired
    private ConferenceRoomDAO conferenceRoomDAO;

    @Override
    public List<ConferenceModel> getAllConferences() {
        return conferenceDAO.getAllConferences();
    }
    
    @Override
    public List<ConferenceDTO> getAllConferencesRoomParticipant() {
        return conferenceDAO.getAllConferencesRoomParticipant();
    }

    @Override
    public boolean addConference(ConferenceModel model) {

        if (model.getConference_name().isEmpty() || model.getExpected_participants().isEmpty() || model.getRoom_id().isEmpty()) {
            return false;
        }
        try {
            ConferenceRoomModel confRoom = conferenceRoomDAO.getConferenceRoom(model.getRoom_id());
            int max = Integer.valueOf(confRoom.getMax_size());
            int checkMax = Integer.valueOf(model.getExpected_participants());

            if (checkMax > max) {
                return false;
            }
            return conferenceDAO.addConference(model);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteConference(String id) {
        try {
            return conferenceDAO.deleteConference(id);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

}
