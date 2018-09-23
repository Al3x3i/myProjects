/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.dao;

import com.manager.models.Participant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Al3x3i
 */
public class ParticipantDAO {

    private DataSource dataSource;

    public ParticipantDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Participant> getParticipantConference(String conference_id) {
        String sql = "SELECT * FROM participant WHERE conference_id=?";
        Connection conn = null;
        List<Participant> result = new ArrayList<Participant>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(conference_id));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String first_name = rs.getString("first_name");
                String second_name = rs.getString("second_name");
                String conf_id = rs.getString("conference_id");

                Participant model = new Participant(id, first_name, second_name, conf_id);
                result.add(model);
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

    public int getTotalRegisteredForConference(String conference_id) {
        String sql = "SELECT count(*) as total FROM participant p WHERE p.conference_id=?";
        Connection conn = null;
        String id = "-1";
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(conference_id));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                id = rs.getString("total");
                break;
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            closeConnection(conn);
        }
        return Integer.valueOf(id);
    }

    public boolean addParticipant(String f_name, String s_name, String conference_id) throws SQLException {
        String sql = "INSERT INTO participant (first_name, second_name, conference_id) VALUES(?,?,?)";
        Connection conn = null;
        int resultValue = 0;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            conn.createStatement().execute("PRAGMA foreign_keys = ON");

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, f_name);
            ps.setString(2, s_name);
            ps.setInt(3, Integer.valueOf(conference_id));
            resultValue = ps.executeUpdate();
            conn.commit();
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            if (conn != null) {
                conn.rollback();
            }
            resultValue = 0;
        } finally {
            closeConnection(conn);
        }
        return resultValue == 1;
    }

    public boolean deleteParticipant(String participant_id, String conference_id) throws SQLException {
        String sql = "DELETE FROM participant WHERE id=? AND conference_id=? ";
        Connection conn = null;
        int resultValue = 0;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Integer.valueOf(participant_id));
            ps.setInt(2, Integer.valueOf(conference_id));
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
        return resultValue > 0;
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
