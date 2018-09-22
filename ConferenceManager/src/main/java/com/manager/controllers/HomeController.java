/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.controllers;

import com.manager.dto.ConferenceDTO;
import com.manager.models.ConferenceModel;
import com.manager.models.ConferenceRoomModel;
import com.manager.models.Participant;
import com.manager.service.impl.ConferenceRoomSerice;
import com.manager.service.impl.ConferenceService;
import com.manager.service.impl.ParticipantService;
import java.util.List;
import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Al3x3i
 */
@Controller
public class HomeController {

    @Autowired
    private ConferenceService conferenceService;

    @Autowired
    private ConferenceRoomSerice conferenceRoomService;

    @Autowired
    private ParticipantService participantService;

    @GetMapping("/greeting")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/manager")
    public String manager() {
        return "managerpage.html";
    }

    @GetMapping("/getAllConferences")
    public @ResponseBody
    List<ConferenceModel> getAllConferences() {
        List<ConferenceModel> conferences = conferenceService.getAllConferences();
        return conferences;
    }
    
    @GetMapping("/getAllConferencesExtended")
    public @ResponseBody
    List<ConferenceDTO> getAllConferencesExtended() {
        List<ConferenceDTO> conferences = conferenceService.getAllConferencesRoomParticipant();
        return conferences;
    }

    @PostMapping("/addConference")
    public @ResponseBody
    Boolean addConference(@RequestBody ConferenceModel model) {
        return conferenceService.addConference(model);
    }

    @DeleteMapping("/deleteConference/{id}")
    public @ResponseBody
    boolean deleteConference(@PathVariable String id) {

        if (id != null && id.length() != 0) {
            return conferenceService.deleteConference(id);
        }

        return false;
    }

    @GetMapping("/getAllConferenceRooms")
    public @ResponseBody
    List<ConferenceRoomModel> getAllConferenceRooms() {
        List<ConferenceRoomModel> models = conferenceRoomService.getAllConferenceRooms();
        return models;
    }

    @GetMapping("/getConferenceParticipants/{id}")
    public @ResponseBody
    List<Participant> getConferenceParticipants(@PathVariable String id) {
        List<Participant> models = participantService.getParticipantConference(id);
        return models;
    }

    @PutMapping("/addConferenceParticipant")
    public @ResponseBody
    boolean addConferenceParticipant(@RequestParam("f_name") String f_name, @RequestParam("s_name") String s_name, @RequestParam("c_id") String c_id) {
        boolean result = participantService.addParticipant(f_name, s_name, c_id);
        return result;
    }

    @DeleteMapping("/deleteConferenceParticipant")
    public @ResponseBody
    boolean deleteConference(@RequestParam("p_id") String p_id, @RequestParam("c_id") String c_id) {

        if (StringHelper.isNullOrEmptyString(p_id) || StringHelper.isNullOrEmptyString(c_id)) {
            return false;
        }
        return participantService.deleteParticipant(p_id, c_id);
    }
}
