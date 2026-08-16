package com.microlearning.dao;

import com.microlearning.model.Note;
import com.microlearning.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

/**
 * Handles database operations related to learner notes.
 */
public class NoteDAO {

    /**
     * Saves a new learner note.
     * A note may be linked to a learning module.
     */
    public boolean addNote(Note note) {

        String sql =
                "INSERT INTO notes " +
                        "(user_id, module_id, title, content) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, note.getUserId());

            // Save NULL when the note is not linked to a module.
            if (note.getModuleId() != null) {
                statement.setInt(2, note.getModuleId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }

            statement.setString(3, note.getTitle());
            statement.setString(4, note.getContent());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets all notes created by one user.
     * Module details are included when available.
     */
    public ArrayList<Note> getNotesByUserId(int userId) {

        ArrayList<Note> notes = new ArrayList<>();

        String sql =
                "SELECT " +
                        "n.note_id, " +
                        "n.user_id, " +
                        "n.module_id, " +
                        "n.title, " +
                        "n.content, " +
                        "n.created_at, " +
                        "m.title AS module_title " +
                        "FROM notes n " +
                        "LEFT JOIN modules m " +
                        "ON n.module_id = m.module_id " +
                        "WHERE n.user_id = ? " +
                        "ORDER BY n.created_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Note note = new Note();

                    note.setNoteId(
                            resultSet.getInt("note_id")
                    );

                    note.setUserId(
                            resultSet.getInt("user_id")
                    );

                    // Keep SQL NULL as Java null.
                    Integer moduleId =
                            resultSet.getObject(
                                    "module_id",
                                    Integer.class
                            );

                    note.setModuleId(moduleId);

                    note.setModuleTitle(
                            resultSet.getString("module_title")
                    );

                    note.setTitle(
                            resultSet.getString("title")
                    );

                    note.setContent(
                            resultSet.getString("content")
                    );

                    note.setCreatedAt(
                            resultSet.getString("created_at")
                    );

                    notes.add(note);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notes;
    }
}