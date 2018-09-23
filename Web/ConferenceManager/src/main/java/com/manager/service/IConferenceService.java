/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.service;

import com.manager.dto.ConferenceDTO;
import com.manager.models.ConferenceModel;
import java.util.List;

/**
 *
 * @author Al3x3i
 */
public interface IConferenceService {

    public List<ConferenceModel> getAllConferences();

    public List<ConferenceDTO> getAllConferencesRoomParticipant();

    public boolean addConference(ConferenceModel model);

    public boolean deleteConference(String id);
}
