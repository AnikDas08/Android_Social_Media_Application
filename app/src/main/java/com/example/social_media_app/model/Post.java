package com.example.social_media_app.model;

public class Post {
    String postId;
    String postBy;
    String postImage;
    String postDescription;
    long postAt;
    int postLike;
    int postComment;

    public Post(String postId, String postBy, String postImage, String postDescription, long postAt, int postLike, int postComment) {
        this.postId = postId;
        this.postBy = postBy;
        this.postImage = postImage;
        this.postDescription = postDescription;
        this.postAt = postAt;
        this.postLike=postLike;
        this.postComment=postComment;
    }
    public Post() {
    }

    public int getPostComment() {
        return postComment;
    }

    public void setPostComment(int postComment) {
        this.postComment = postComment;
    }

    public int getPostLike() {
        return postLike;
    }

    public void setPostLike(int postLike) {
        this.postLike = postLike;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostBy() {
        return postBy;
    }

    public void setPostBy(String postBy) {
        this.postBy = postBy;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public long getPostAt() {
        return postAt;
    }

    public void setPostAt(long postAt) {
        this.postAt = postAt;
    }
}
