package com.example.social_media_app.model;

import java.sql.Struct;

public class Comment {
    String commentDescripti;
    long commentAt;
    String commentBy;

    public Comment(String commentDescripti, long commentAt, String commentBy) {
        this.commentDescripti = commentDescripti;
        this.commentAt = commentAt;
        this.commentBy = commentBy;
    }

    public Comment() {
    }

    public String getCommentDescripti() {
        return commentDescripti;
    }

    public void setCommentDescripti(String commentDescripti) {
        this.commentDescripti = commentDescripti;
    }

    public long getCommentAt() {
        return commentAt;
    }

    public void setCommentAt(long commentAt) {
        this.commentAt = commentAt;
    }

    public String getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }
}
