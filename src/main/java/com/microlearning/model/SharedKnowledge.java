package com.microlearning.model;

import java.sql.Timestamp;

public class SharedKnowledge {

    private int knowledgeId;
    private int userId;
    private String username;
    private String title;
    private String category;
    private String experienceType;
    private String description;
    private String content;
    private String keyTakeaway;
    private String readingTime;
    private String imageUrl;
    private String videoUrl;
    private Timestamp createdAt;

    public SharedKnowledge() {
    }

    public SharedKnowledge(
            int userId,
            String title,
            String category,
            String experienceType,
            String description,
            String content,
            String keyTakeaway,
            String readingTime,
            String imageUrl,
            String videoUrl) {

        this.userId = userId;
        this.title = title;
        this.category = category;
        this.experienceType = experienceType;
        this.description = description;
        this.content = content;
        this.keyTakeaway = keyTakeaway;
        this.readingTime = readingTime;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
    }

    public int getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(int knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExperienceType() {
        return experienceType;
    }

    public void setExperienceType(String experienceType) {
        this.experienceType = experienceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKeyTakeaway() {
        return keyTakeaway;
    }

    public void setKeyTakeaway(String keyTakeaway) {
        this.keyTakeaway = keyTakeaway;
    }

    public String getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(String readingTime) {
        this.readingTime = readingTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}