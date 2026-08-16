package com.microlearning.model;

import java.time.LocalDateTime;

public class CommunityOpportunity {

    private int opportunityId;
    private int userId;
    private String username;
    private String opportunityType;
    private String title;
    private String description;
    private String location;
    private LocalDateTime eventDate;
    private String contactInformation;
    private LocalDateTime createdAt;

    public CommunityOpportunity() {
    }

    public CommunityOpportunity(
            int opportunityId,
            int userId,
            String username,
            String opportunityType,
            String title,
            String description,
            String location,
            LocalDateTime eventDate,
            String contactInformation,
            LocalDateTime createdAt) {

        this.opportunityId = opportunityId;
        this.userId = userId;
        this.username = username;
        this.opportunityType = opportunityType;
        this.title = title;
        this.description = description;
        this.location = location;
        this.eventDate = eventDate;
        this.contactInformation = contactInformation;
        this.createdAt = createdAt;
    }

    public int getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(int opportunityId) {
        this.opportunityId = opportunityId;
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

    public String getOpportunityType() {
        return opportunityType;
    }

    public void setOpportunityType(String opportunityType) {
        this.opportunityType = opportunityType;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}