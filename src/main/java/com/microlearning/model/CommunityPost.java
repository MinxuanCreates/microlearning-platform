package com.microlearning.model;

import java.util.ArrayList;

/**
 * Represents a community post created by a learner.
 */
public class CommunityPost {

    private int postId;
    private int userId;
    private String username;
    private String title;
    private String content;
    private String createdAt;
    private ArrayList<CommunityComment> comments = new ArrayList<>();

    public CommunityPost() {
    }

    public CommunityPost(int userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public ArrayList<CommunityComment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommunityComment> comments) {
        this.comments = comments;
    }
}