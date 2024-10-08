package com.tecnocampus.LS2.protube_back.application.DTO;

import com.tecnocampus.LS2.protube_back.domain.Video;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

import java.util.List;

public class VideoDTO {
    private Long id;
    private int width;
    private int height;
    private double duration;
    private String title;
    private String user;
    private String description;
    private List<String> categories;
    private List<String> tags;

    public VideoDTO(Long id, int width, int height, double duration,
                    String title, String user, String description,
                    List<String> categories, List<String> tags) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.title = title;
        this.user = user;
        this.description = description;
        this.categories = categories;
        this.tags = tags;
    }

    public VideoDTO() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public double getDuration() { return duration; }
    public void setDuration(double duration) { this.duration = duration; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}
