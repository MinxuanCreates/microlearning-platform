package com.microlearning.model;

/**
 * Represents a user's progress for a learning module.
 */
public class LearningProgress {

    private int progressId;
    private int userId;
    private int moduleId;
    private String moduleTitle;
    private String category;
    private String achievementLabel;
    private String progressStatus;
    private String completedAt;
    private String updatedAt;

    public LearningProgress() {
    }

    public LearningProgress(int userId, int moduleId, String progressStatus) {
        this.userId = userId;
        this.moduleId = moduleId;
        this.progressStatus = progressStatus;
    }

    public int getProgressId() {
        return progressId;
    }

    public void setProgressId(int progressId) {
        this.progressId = progressId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAchievementLabel() {
        return achievementLabel;
    }

    public void setAchievementLabel(String achievementLabel) {
        this.achievementLabel = achievementLabel;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}