package com.microlearning.model;

/**
 * Represents a learning module shown on the Learning Topics page.
 */
public class Module {

    private int moduleId;
    private String title;
    private String description;
    private String category;
    private String duration;
    private String achievementLabel;

    public Module() {
    }

    public Module(int moduleId, String title, String description,
                  String category, String duration, String achievementLabel) {
        this.moduleId = moduleId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.duration = duration;
        this.achievementLabel = achievementLabel;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAchievementLabel() {
        return achievementLabel;
    }

    public void setAchievementLabel(String achievementLabel) {
        this.achievementLabel = achievementLabel;
    }
}