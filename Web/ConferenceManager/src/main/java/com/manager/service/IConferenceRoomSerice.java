/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.service;

import com.manager.models.ConferenceRoomModel;
import java.util.List;

/**
 *
 * @author Al3x3i
 */
public interface IConferenceRoomSerice {

    public List<ConferenceRoomModel> getAllConferenceRooms();
}
