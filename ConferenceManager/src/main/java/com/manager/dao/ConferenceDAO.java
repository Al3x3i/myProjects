/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.dao;

import com.manager.dto.ConferenceDTO;
import com.manager.models.ConferenceModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Al3x3i
 */
public class ConferenceDAO {

    private DataSource dataSource;

    public ConferenceDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<ConferenceModel> getAllConferences() {
        String sql = "SELECT * FROM conference";
        Connection conn = null;
        List<ConferenceModel> result = new ArrayList<ConferenceModel>();
        try {
            conn = dataSource.getConnection();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String conferenceName = rs.getString("conference_name");
                String expectedParticipants = rs.getString("expected_participants");
                String roomID = rs.getString("room_id");
                ConferenceModel model = new ConferenceModel(id, conferenceName, expectedParticipants, roomID);
                result.add(model);
            }
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            closeConnection(conn);
        }
        return result;
    }

    public List<ConferenceDTO> getAllConferencesRoomParticipant() {
        String sql = "SELECT *, (SELECT count(*) FROM participant p WHERE c.id=p.conference_id) as registered FROM conference c LEFT JOIN conferenceRoom r ON c.room_id=r.id GROUP BY c.id;";
        Connection conn = null;
        List<ConferenceDTO> result = new ArrayList<ConferenceDTO>();
        try {
            conn = dataSource.getConnection();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String conferenceName = rs.getString("conference_name");
                String expectedParticipants = rs.getString("expected_participants");
                String roomID = rs.getString("room_id");
                String room_name = rs.getString("room_name");
                String max_size = rs.getString("max_size");
                String registered = rs.getString("registered");
                ConferenceDTO model = new ConferenceDTO(id, conferenceName, expectedParticipants, roomID, room_name, max_size, registered);
                result.add(model);
            }
            stmt.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            closeConnection(conn);
        }
        return result;
    }

    public ConferenceDTO getConferenceRoomParticipant(String conference_id) {
        String sql = "SELECT *, (SELECT count(*) FROM participant p WHERE c.id=p.conference_id) as registered FROM conference c LEFT JOIN conferenceRoom r ON c.room_id=r.id WHERE c.id=? GROUP BY c.id;";
        Connection conn = null;
        ConferenceDTO result = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(conference_id));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String conferenceName = rs.getString("conference_name");
                String expectedParticipants = rs.getString("expected_participants");
                String roomID = rs.getString("room_id");
                String room_name = rs.getString("room_name");
                String max_size = rs.getString("max_size");
                String registered = rs.getString("registered");
                result = new ConferenceDTO(id, conferenceName, expectedParticipants, roomID, room_name, max_size, registered);
                break;
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeConnection(conn);
        }
        return result;
    }

    public boolean addConference(ConferenceModel model) throws SQLException {
        String sql = "INSERT INTO conference (conference_name, expected_participants, room_id) VALUES(?,?,?)";
        Connection conn = null;
        int resultValue = 0;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            conn.createStatement().execute("PRAGMA foreign_keys = ON");

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, model.getConference_name());
            ps.setInt(2, Integer.valueOf(model.getExpected_participants()));
            ps.setInt(3, Integer.valueOf(model.getRoom_id()));
            resultValue = ps.executeUpdate();
            conn.commit();
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            if (conn != null) {
                conn.rollback();
            }
        } finally {
            closeConnection(conn);
        }
        return resultValue == 1;
    }

    public boolean deleteConference(String id) throws SQLException {
        String sql = "DELETE FROM conference WHERE id = ?";
        Connection conn = null;
        int resultValue = 0;
        try {
            conn = dataSource.getConnection();
            conn.createStatement().execute("PRAGMA foreign_keys = ON");
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Integer.valueOf(id));
            resultValue = ps.executeUpdate();
            conn.commit();
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            if (conn != null) {
                conn.rollback();
            }
        } finally {
            closeConnection(conn);
        }
        return resultValue == 1;
    }

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
