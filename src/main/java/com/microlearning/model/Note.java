package com.microlearning.model;

/**
 * Represents a note created by a learner.
 */
public class Note {

    private int noteId;
    private int userId;

    /*
     * Integer allows module ID to be null for general notes.
     */
    private Integer moduleId;

    private String moduleTitle;
    private String title;
    private String content;
    private String createdAt;

    /**
     * Empty constructor used when creating objects from database results.
     */
    public Note() {
    }

    /**
     * Creates a general note without a linked module.
     */
    public Note(int userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    /**
     * Creates a note linked to a learning module.
     */
    public Note(
            int userId,
            Integer moduleId,
            String title,
            String content) {

        this.userId = userId;
        this.moduleId = moduleId;
        this.title = title;
        this.content = content;
    }

    /**
     * Creates a general note loaded from the database.
     */
    public Note(
            int noteId,
            int userId,
            String title,
            String content,
            String createdAt) {

        this.noteId = noteId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    /**
     * Creates a module-linked note loaded from the database.
     */
    public Note(
            int noteId,
            int userId,
            Integer moduleId,
            String title,
            String content,
            String createdAt) {

        this.noteId = noteId;
        this.userId = userId;
        this.moduleId = moduleId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    /**
     * Creates a note with its module title for display.
     */
    public Note(
            int noteId,
            int userId,
            Integer moduleId,
            String moduleTitle,
            String title,
            String content,
            String createdAt) {

        this.noteId = noteId;
        this.userId = userId;
        this.moduleId = moduleId;
        this.moduleTitle = moduleTitle;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}