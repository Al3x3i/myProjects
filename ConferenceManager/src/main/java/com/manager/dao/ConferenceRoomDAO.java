/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.dao;

import com.manager.models.ConferenceRoomModel;
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
public class ConferenceRoomDAO {

    private DataSource dataSource;

    public ConferenceRoomDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<ConferenceRoomModel> getAllConferenceRooms() {
        String sql = "SELECT * FROM conferenceRoom";
        Connection conn = null;
        List<ConferenceRoomModel> result = new ArrayList<ConferenceRoomModel>();
        try {
            conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String roomName = rs.getString("room_name");
                String maxSize = rs.getString("max_size");
                ConferenceRoomModel model = new ConferenceRoomModel(id, roomName, maxSize);
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

    public ConferenceRoomModel getConferenceRoom(String id) {
        String sql = "SELECT * FROM conferenceRoom WHERE id=?";
        Connection conn = null;
        ConferenceRoomModel result = null;
        try {
            conn = dataSource.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(id));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String c_id = rs.getString("id");
                String roomName = rs.getString("room_name");
                String maxSize = rs.getString("max_size");
                result = new ConferenceRoomModel(id, roomName, maxSize);
                break;
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeConnection(conn);
        }
        return result;
    }

    public boolean addConferenceRoom(ConferenceRoomModel model) throws SQLException {
        String sql = "INSERT INTO conferenceRoom (room_name, max_size) VALUES(?,?)";
        Connection conn = null;
        int resultValue = 0;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, model.getRoom_name());
            ps.setInt(2, Integer.valueOf(model.getMax_size()));

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
