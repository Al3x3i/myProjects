/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.service.impl;

import com.manager.dao.ConferenceRoomDAO;
import com.manager.models.ConferenceRoomModel;
import com.manager.service.IConferenceRoomSerice;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Al3x3i
 */
@Service
public class ConferenceRoomSerice implements IConferenceRoomSerice {

    @Autowired
    private ConferenceRoomDAO conferenceRoomDAO;

    @Override
    public List<ConferenceRoomModel> getAllConferenceRooms() {
        return conferenceRoomDAO.getAllConferenceRooms();
    }

}
