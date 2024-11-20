package com.tecnocampus.LS2.protube_back.application.DTO;

import com.tecnocampus.LS2.protube_back.domain.Video;
import java.util.ArrayList;
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
    private String videoPath;
    private String imagePath;
    private List<CommentDTO> comments = new ArrayList<>();

    public VideoDTO(Long id, int width, int height, double duration,
                    String title, String user, String description,
                    List<String> categories, List<String> tags,
                    String videoPath, String imagePath) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.title = title;
        this.user = user;
        this.description = description;
        this.categories = categories;
        this.tags = tags;
        this.videoPath = videoPath;
        this.imagePath = imagePath;
    }

    public VideoDTO() {}

    // Getters i Setters
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

    public String getVideoPath() { return videoPath; }
    public void setVideoPath(String videoPath) { this.videoPath = videoPath; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public List<CommentDTO> getComments() { return comments; }
    public void setComments(List<CommentDTO> comments) { this.comments = comments; }

    public static class CommentDTO {
        private String text;
        private String author;

        public CommentDTO(String text, String author) {
            this.text = text;
            this.author = author;
        }

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }

        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
    }
}
