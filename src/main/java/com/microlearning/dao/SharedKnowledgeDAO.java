package com.microlearning.dao;

import com.microlearning.model.SharedKnowledge;
import com.microlearning.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Handles database operations related to shared knowledge.
 */
public class SharedKnowledgeDAO {

    /**
     * Adds a new shared knowledge post to the database.
     */
    public boolean addSharedKnowledge(SharedKnowledge knowledge) {

        String sql = "INSERT INTO shared_knowledge " +
                "(user_id, title, category, experience_type, description, " +
                "content, key_takeaway, reading_time, image_url, video_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, knowledge.getUserId());
            statement.setString(2, knowledge.getTitle());
            statement.setString(3, knowledge.getCategory());
            statement.setString(4, knowledge.getExperienceType());
            statement.setString(5, knowledge.getDescription());
            statement.setString(6, knowledge.getContent());
            statement.setString(7, knowledge.getKeyTakeaway());
            statement.setString(8, knowledge.getReadingTime());
            statement.setString(9, knowledge.getImageUrl());
            statement.setString(10, knowledge.getVideoUrl());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {

                    if (generatedKeys.next()) {
                        knowledge.setKnowledgeId(
                                generatedKeys.getInt(1));
                    }
                }

                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Gets all shared knowledge posts from the database.
     */
    public ArrayList<SharedKnowledge> getAllSharedKnowledge() {

        ArrayList<SharedKnowledge> sharedKnowledgeList =
                new ArrayList<>();

        String sql = "SELECT sk.*, u.username " +
                "FROM shared_knowledge sk " +
                "JOIN users u ON sk.user_id = u.user_id " +
                "ORDER BY sk.created_at DESC, sk.knowledge_id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                SharedKnowledge knowledge =
                        createSharedKnowledgeFromResultSet(resultSet);

                sharedKnowledgeList.add(knowledge);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sharedKnowledgeList;
    }

    /**
     * Gets one shared knowledge post by its ID.
     */
    public SharedKnowledge getSharedKnowledgeById(int knowledgeId) {

        String sql = "SELECT sk.*, u.username " +
                "FROM shared_knowledge sk " +
                "JOIN users u ON sk.user_id = u.user_id " +
                "WHERE sk.knowledge_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, knowledgeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return createSharedKnowledgeFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Searches shared knowledge posts by keyword.
     */
    public ArrayList<SharedKnowledge> searchSharedKnowledge(
            String keyword) {

        ArrayList<SharedKnowledge> sharedKnowledgeList =
                new ArrayList<>();

        String sql = "SELECT sk.*, u.username " +
                "FROM shared_knowledge sk " +
                "JOIN users u ON sk.user_id = u.user_id " +
                "WHERE sk.title LIKE ? " +
                "OR sk.description LIKE ? " +
                "OR sk.category LIKE ? " +
                "OR sk.experience_type LIKE ? " +
                "OR sk.content LIKE ? " +
                "OR u.username LIKE ? " +
                "ORDER BY sk.created_at DESC, sk.knowledge_id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";

            statement.setString(1, searchKeyword);
            statement.setString(2, searchKeyword);
            statement.setString(3, searchKeyword);
            statement.setString(4, searchKeyword);
            statement.setString(5, searchKeyword);
            statement.setString(6, searchKeyword);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    SharedKnowledge knowledge =
                            createSharedKnowledgeFromResultSet(resultSet);

                    sharedKnowledgeList.add(knowledge);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sharedKnowledgeList;
    }

    /**
     * Counts all shared knowledge posts.
     */
    public int countAllSharedKnowledge() {

        String sql =
                "SELECT COUNT(*) AS knowledge_count " +
                        "FROM shared_knowledge";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("knowledge_count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Creates a SharedKnowledge object from a database result.
     */
    private SharedKnowledge createSharedKnowledgeFromResultSet(
            ResultSet resultSet)
            throws SQLException {

        SharedKnowledge knowledge = new SharedKnowledge();

        knowledge.setKnowledgeId(
                resultSet.getInt("knowledge_id"));

        knowledge.setUserId(
                resultSet.getInt("user_id"));

        knowledge.setUsername(
                resultSet.getString("username"));

        knowledge.setTitle(
                resultSet.getString("title"));

        knowledge.setCategory(
                resultSet.getString("category"));

        knowledge.setExperienceType(
                resultSet.getString("experience_type"));

        knowledge.setDescription(
                resultSet.getString("description"));

        knowledge.setContent(
                resultSet.getString("content"));

        knowledge.setKeyTakeaway(
                resultSet.getString("key_takeaway"));

        knowledge.setReadingTime(
                resultSet.getString("reading_time"));

        knowledge.setImageUrl(
                resultSet.getString("image_url"));

        knowledge.setVideoUrl(
                resultSet.getString("video_url"));

        knowledge.setCreatedAt(
                resultSet.getTimestamp("created_at"));

        return knowledge;
    }
}