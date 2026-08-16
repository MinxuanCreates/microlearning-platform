package com.microlearning.dao;

import com.microlearning.model.Module;
import com.microlearning.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handles database operations related to learning modules.
 */
public class ModuleDAO {

    /**
     * Gets all learning modules from the database.
     */
    public ArrayList<Module> getAllModules() {
        ArrayList<Module> modules = new ArrayList<>();

        String sql = "SELECT * FROM modules ORDER BY module_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Module module = new Module();

                module.setModuleId(resultSet.getInt("module_id"));
                module.setTitle(resultSet.getString("title"));
                module.setDescription(resultSet.getString("description"));
                module.setCategory(resultSet.getString("category"));
                module.setDuration(resultSet.getString("duration"));
                module.setAchievementLabel(resultSet.getString("achievement_label"));

                modules.add(module);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modules;
    }

    /**
     * Gets one learning module by its ID.
     */
    public Module getModuleById(int moduleId) {
        String sql = "SELECT * FROM modules WHERE module_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, moduleId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Module module = new Module();

                module.setModuleId(resultSet.getInt("module_id"));
                module.setTitle(resultSet.getString("title"));
                module.setDescription(resultSet.getString("description"));
                module.setCategory(resultSet.getString("category"));
                module.setDuration(resultSet.getString("duration"));
                module.setAchievementLabel(resultSet.getString("achievement_label"));

                return module;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Counts all available learning modules.
     */
    public int countAllModules() {
        String sql = "SELECT COUNT(*) AS module_count FROM modules";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("module_count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Searches learning modules by keyword.
     */
    public ArrayList<Module> searchModules(String keyword) {
        ArrayList<Module> modules = new ArrayList<>();

        String sql = "SELECT * FROM modules " +
                "WHERE title LIKE ? OR description LIKE ? OR category LIKE ? " +
                "ORDER BY module_id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";

            statement.setString(1, searchKeyword);
            statement.setString(2, searchKeyword);
            statement.setString(3, searchKeyword);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Module module = new Module();

                module.setModuleId(resultSet.getInt("module_id"));
                module.setTitle(resultSet.getString("title"));
                module.setDescription(resultSet.getString("description"));
                module.setCategory(resultSet.getString("category"));
                module.setDuration(resultSet.getString("duration"));
                module.setAchievementLabel(resultSet.getString("achievement_label"));

                modules.add(module);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modules;
    }
}