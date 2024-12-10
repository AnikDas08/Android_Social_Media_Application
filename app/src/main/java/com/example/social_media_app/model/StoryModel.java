package com.example.social_media_app.model;

import java.util.ArrayList;

public class StoryModel {
    String storyBy;
    long storyAt;
    ArrayList<UserStory> stories;

    public StoryModel(String storyBy, long storyAt, ArrayList<UserStory> stories) {
        this.storyBy = storyBy;
        this.storyAt = storyAt;
        this.stories = stories;
    }
    public StoryModel() {
    }

    public ArrayList<UserStory> getStories() {
        return stories;
    }

    public void setStories(ArrayList<UserStory> stories) {
        this.stories = stories;
    }

    public String getStoryBy() {
        return storyBy;
    }

    public void setStoryBy(String storyBy) {
        this.storyBy = storyBy;
    }

    public long getStoryAt() {
        return storyAt;
    }

    public void setStoryAt(long storyAt) {
        this.storyAt = storyAt;
    }
}
