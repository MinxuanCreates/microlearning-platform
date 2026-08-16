package com.microlearning.dao;

import com.microlearning.model.CommunityOpportunity;
import com.microlearning.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles database operations for community opportunities.
 */
public class CommunityOpportunityDAO {

    /**
     * Returns all community opportunities.
     */
    public List<CommunityOpportunity> getAllOpportunities() {

        List<CommunityOpportunity> opportunities =
                new ArrayList<>();

        String sql =
                "SELECT co.opportunity_id, " +
                        "co.user_id, " +
                        "u.username, " +
                        "co.opportunity_type, " +
                        "co.title, " +
                        "co.description, " +
                        "co.location, " +
                        "co.event_date, " +
                        "co.contact_information, " +
                        "co.created_at " +
                        "FROM community_opportunities co " +
                        "JOIN users u ON co.user_id = u.user_id " +
                        "ORDER BY co.created_at DESC";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                CommunityOpportunity opportunity =
                        mapResultSetToOpportunity(resultSet);

                opportunities.add(opportunity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return opportunities;
    }

    /**
     * Returns opportunities for one selected type.
     */
    public List<CommunityOpportunity> getOpportunitiesByType(
            String opportunityType) {

        List<CommunityOpportunity> opportunities =
                new ArrayList<>();

        String sql =
                "SELECT co.opportunity_id, " +
                        "co.user_id, " +
                        "u.username, " +
                        "co.opportunity_type, " +
                        "co.title, " +
                        "co.description, " +
                        "co.location, " +
                        "co.event_date, " +
                        "co.contact_information, " +
                        "co.created_at " +
                        "FROM community_opportunities co " +
                        "JOIN users u ON co.user_id = u.user_id " +
                        "WHERE co.opportunity_type = ? " +
                        "ORDER BY co.created_at DESC";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    opportunityType
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {

                    CommunityOpportunity opportunity =
                            mapResultSetToOpportunity(resultSet);

                    opportunities.add(opportunity);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return opportunities;
    }

    /**
     * Returns one opportunity by its ID.
     */
    public CommunityOpportunity getOpportunityById(
            int opportunityId) {

        String sql =
                "SELECT co.opportunity_id, " +
                        "co.user_id, " +
                        "u.username, " +
                        "co.opportunity_type, " +
                        "co.title, " +
                        "co.description, " +
                        "co.location, " +
                        "co.event_date, " +
                        "co.contact_information, " +
                        "co.created_at " +
                        "FROM community_opportunities co " +
                        "JOIN users u ON co.user_id = u.user_id " +
                        "WHERE co.opportunity_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    opportunityId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {
                    return mapResultSetToOpportunity(
                            resultSet
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Adds a new community opportunity.
     */
    public boolean addOpportunity(
            CommunityOpportunity opportunity) {

        String sql =
                "INSERT INTO community_opportunities " +
                        "(user_id, opportunity_type, title, description, " +
                        "location, event_date, contact_information) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    opportunity.getUserId()
            );

            statement.setString(
                    2,
                    opportunity.getOpportunityType()
            );

            statement.setString(
                    3,
                    opportunity.getTitle()
            );

            statement.setString(
                    4,
                    opportunity.getDescription()
            );

            statement.setString(
                    5,
                    opportunity.getLocation()
            );

            if (opportunity.getEventDate() != null) {

                statement.setTimestamp(
                        6,
                        Timestamp.valueOf(
                                opportunity.getEventDate()
                        )
                );

            } else {

                statement.setNull(
                        6,
                        java.sql.Types.TIMESTAMP
                );
            }

            statement.setString(
                    7,
                    opportunity.getContactInformation()
            );

            int rowsAffected =
                    statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Converts one database row into a CommunityOpportunity object.
     */
    private CommunityOpportunity mapResultSetToOpportunity(
            ResultSet resultSet)
            throws SQLException {

        CommunityOpportunity opportunity =
                new CommunityOpportunity();

        opportunity.setOpportunityId(
                resultSet.getInt("opportunity_id")
        );

        opportunity.setUserId(
                resultSet.getInt("user_id")
        );

        opportunity.setUsername(
                resultSet.getString("username")
        );

        opportunity.setOpportunityType(
                resultSet.getString("opportunity_type")
        );

        opportunity.setTitle(
                resultSet.getString("title")
        );

        opportunity.setDescription(
                resultSet.getString("description")
        );

        opportunity.setLocation(
                resultSet.getString("location")
        );

        Timestamp eventDate =
                resultSet.getTimestamp("event_date");

        if (eventDate != null) {
            opportunity.setEventDate(
                    eventDate.toLocalDateTime()
            );
        }

        opportunity.setContactInformation(
                resultSet.getString("contact_information")
        );

        Timestamp createdAt =
                resultSet.getTimestamp("created_at");

        if (createdAt != null) {
            opportunity.setCreatedAt(
                    createdAt.toLocalDateTime()
            );
        }

        return opportunity;
    }
}