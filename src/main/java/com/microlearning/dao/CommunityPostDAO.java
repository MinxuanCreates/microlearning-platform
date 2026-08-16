package com.microlearning.dao;

import com.microlearning.model.CommunityPost;
import com.microlearning.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handles database operations related to community posts.
 */
public class CommunityPostDAO {

    /**
     * Saves a new community post to the database.
     */
    public boolean addPost(CommunityPost post) {
        String sql = "INSERT INTO community_posts (user_id, title, content) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, post.getUserId());
            statement.setString(2, post.getTitle());
            statement.setString(3, post.getContent());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets all community posts with the username of the author.
     */
    public ArrayList<CommunityPost> getAllPosts() {
        ArrayList<CommunityPost> posts = new ArrayList<>();

        String sql = """
                SELECT p.post_id, p.user_id, p.title, p.content, p.created_at, u.username
                FROM community_posts p
                JOIN users u ON p.user_id = u.user_id
                ORDER BY p.created_at DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                CommunityPost post = new CommunityPost();

                post.setPostId(resultSet.getInt("post_id"));
                post.setUserId(resultSet.getInt("user_id"));
                post.setUsername(resultSet.getString("username"));
                post.setTitle(resultSet.getString("title"));
                post.setContent(resultSet.getString("content"));
                post.setCreatedAt(resultSet.getString("created_at"));

                posts.add(post);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    /**
     * Searches community posts by keyword.
     */
    public ArrayList<CommunityPost> searchPosts(String keyword) {
        ArrayList<CommunityPost> posts = new ArrayList<>();

        String sql = """
            SELECT p.post_id, p.user_id, p.title, p.content, p.created_at, u.username
            FROM community_posts p
            JOIN users u ON p.user_id = u.user_id
            WHERE p.title LIKE ? OR p.content LIKE ? OR u.username LIKE ?
            ORDER BY p.created_at DESC
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";

            statement.setString(1, searchKeyword);
            statement.setString(2, searchKeyword);
            statement.setString(3, searchKeyword);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CommunityPost post = new CommunityPost();

                post.setPostId(resultSet.getInt("post_id"));
                post.setUserId(resultSet.getInt("user_id"));
                post.setUsername(resultSet.getString("username"));
                post.setTitle(resultSet.getString("title"));
                post.setContent(resultSet.getString("content"));
                post.setCreatedAt(resultSet.getString("created_at"));

                posts.add(post);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    /**
     * Gets one community post by its ID.
     */
    public CommunityPost getPostById(int postId) {
        String sql = """
            SELECT p.post_id, p.user_id, p.title, p.content, p.created_at, u.username
            FROM community_posts p
            JOIN users u ON p.user_id = u.user_id
            WHERE p.post_id = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, postId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                CommunityPost post = new CommunityPost();

                post.setPostId(resultSet.getInt("post_id"));
                post.setUserId(resultSet.getInt("user_id"));
                post.setUsername(resultSet.getString("username"));
                post.setTitle(resultSet.getString("title"));
                post.setContent(resultSet.getString("content"));
                post.setCreatedAt(resultSet.getString("created_at"));

                return post;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}