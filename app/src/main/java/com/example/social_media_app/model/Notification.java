package com.example.social_media_app.model;

public class Notification {
    String notificationBy;
    String type;
    String postId;
    long notificationAt;
    String notificationId;
    String postBy;
    boolean check;

    public Notification(String notificationBy, String type, String postId, long notificationAt, String postBy, boolean check, String notificationId) {
        this.notificationBy = notificationBy;
        this.type = type;
        this.postId = postId;
        this.notificationAt = notificationAt;
        this.postBy = postBy;
        this.check = check;
        this.notificationId=notificationId;
    }

    public Notification() {
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationBy() {
        return notificationBy;
    }

    public void setNotificationBy(String notificationBy) {
        this.notificationBy = notificationBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public long getNotificationAt() {
        return notificationAt;
    }

    public void setNotificationAt(long notificationAt) {
        this.notificationAt = notificationAt;
    }

    public String getPostBy() {
        return postBy;
    }

    public void setPostBy(String postBy) {
        this.postBy = postBy;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
