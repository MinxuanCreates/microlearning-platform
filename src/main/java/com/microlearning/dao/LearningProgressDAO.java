package com.microlearning.dao;

import com.microlearning.model.LearningProgress;
import com.microlearning.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handles database operations related to user learning progress.
 */
public class LearningProgressDAO {

    /**
     * Marks a module as completed for a user.
     * If progress already exists, it updates the existing record.
     */
    public boolean markModuleCompleted(int userId, int moduleId) {
        String sql = """
                INSERT INTO learning_progress (user_id, module_id, progress_status, completed_at)
                VALUES (?, ?, 'completed', CURRENT_TIMESTAMP)
                ON DUPLICATE KEY UPDATE
                    progress_status = 'completed',
                    completed_at = CURRENT_TIMESTAMP,
                    updated_at = CURRENT_TIMESTAMP
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, moduleId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets the user's progress records with module information.
     */
    public ArrayList<LearningProgress> getProgressByUserId(int userId) {
        ArrayList<LearningProgress> progressList = new ArrayList<>();

        String sql = """
                SELECT lp.progress_id,
                       lp.user_id,
                       lp.module_id,
                       lp.progress_status,
                       lp.completed_at,
                       lp.updated_at,
                       m.title,
                       m.category,
                       m.achievement_label
                FROM learning_progress lp
                JOIN modules m ON lp.module_id = m.module_id
                WHERE lp.user_id = ?
                ORDER BY lp.updated_at DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                LearningProgress progress = new LearningProgress();

                progress.setProgressId(resultSet.getInt("progress_id"));
                progress.setUserId(resultSet.getInt("user_id"));
                progress.setModuleId(resultSet.getInt("module_id"));
                progress.setProgressStatus(resultSet.getString("progress_status"));
                progress.setCompletedAt(resultSet.getString("completed_at"));
                progress.setUpdatedAt(resultSet.getString("updated_at"));

                progress.setModuleTitle(resultSet.getString("title"));
                progress.setCategory(resultSet.getString("category"));
                progress.setAchievementLabel(resultSet.getString("achievement_label"));

                progressList.add(progress);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return progressList;
    }

    /**
     * Counts how many modules a user has completed.
     */
    public int countCompletedModules(int userId) {
        String sql = """
                SELECT COUNT(*) AS completed_count
                FROM learning_progress
                WHERE user_id = ? AND progress_status = 'completed'
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("completed_count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Checks whether a module has already been completed by a user.
     */
    public boolean isModuleCompleted(int userId, int moduleId) {
        String sql = """
                SELECT COUNT(*) AS completed_count
                FROM learning_progress
                WHERE user_id = ?
                  AND module_id = ?
                  AND progress_status = 'completed'
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, moduleId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("completed_count") > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}