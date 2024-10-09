package com.tecnocampus.LS2.protube_back.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;

public class Video {
    private Long id;
    private int width;
    private int height;
    private double duration;
    private String title;
    private String user;
    private Meta meta;
    private String videoPath;
    //"C:\Users\decor\Desktop\videos-10\0.mp4"

    @JsonCreator
    public Video(
            @JsonProperty("id") Long id,
            @JsonProperty("width") int width,
            @JsonProperty("height") int height,
            @JsonProperty("duration") double duration,
            @JsonProperty("title") String title,
            @JsonProperty("user") String user,
            @JsonProperty("meta") Meta meta) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.duration = duration;
        this.title = title;
        this.user = user;
        this.meta = meta;
        this.videoPath = "C:/Users/decor/Desktop/videos-10/" + id + ".mp4";
        // TODO SI dona problemes repassar el .toString()
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public String getVideoPath() {
        return this.videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }


    public static class Meta {
        private String description;
        private List<String> categories;
        private List<String> tags;
        private List<Comment> comments;

        @JsonCreator
        public Meta(
                @JsonProperty("description") String description,
                @JsonProperty("categories") List<String> categories,
                @JsonProperty("tags") List<String> tags,
                @JsonProperty("comments") List<Comment> comments) {
            this.description = description;
            this.categories = categories;
            this.tags = tags;
            this.comments = comments;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<String> getCategories() {
            return categories;
        }

        public void setCategories(List<String> categories) {
            this.categories = categories;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }

        // Nested class for Comment
        public static class Comment {
            private String text;
            private String author;

            @JsonCreator
            public Comment(
                    @JsonProperty("text") String text,
                    @JsonProperty("author") String author) {
                this.text = text;
                this.author = author;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getAuthor() {
                return this.author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }
        }
    }
}

