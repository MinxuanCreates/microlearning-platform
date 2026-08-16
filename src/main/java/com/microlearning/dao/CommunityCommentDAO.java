package com.microlearning.dao;

import com.microlearning.model.CommunityComment;
import com.microlearning.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handles database operations related to community comments.
 */
public class CommunityCommentDAO {

    /**
     * Saves a new comment to the database.
     */
    public boolean addComment(CommunityComment comment) {

        String sql = """
                INSERT INTO community_comments
                    (post_id, user_id, comment_content)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, comment.getPostId());
            statement.setInt(2, comment.getUserId());
            statement.setString(3, comment.getCommentContent());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets all comments belonging to one community post.
     */
    public ArrayList<CommunityComment> getCommentsByPostId(int postId) {

        ArrayList<CommunityComment> comments = new ArrayList<>();

        String sql = """
                SELECT
                    c.comment_id,
                    c.post_id,
                    c.user_id,
                    c.comment_content,
                    c.created_at,
                    u.username
                FROM community_comments c
                JOIN users u ON c.user_id = u.user_id
                WHERE c.post_id = ?
                ORDER BY c.created_at ASC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, postId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    CommunityComment comment =
                            new CommunityComment();

                    comment.setCommentId(
                            resultSet.getInt("comment_id")
                    );

                    comment.setPostId(
                            resultSet.getInt("post_id")
                    );

                    comment.setUserId(
                            resultSet.getInt("user_id")
                    );

                    comment.setUsername(
                            resultSet.getString("username")
                    );

                    comment.setCommentContent(
                            resultSet.getString("comment_content")
                    );

                    comment.setCreatedAt(
                            resultSet.getTimestamp("created_at")
                    );

                    comments.add(comment);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }
}