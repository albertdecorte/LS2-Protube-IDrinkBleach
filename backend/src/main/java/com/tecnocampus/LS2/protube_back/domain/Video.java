package com.tecnocampus.LS2.protube_back.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "videos")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int width;
    private int height;
    private double duration;
    private String title;
    private String userName;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "meta_id")
    private Meta meta;

    private String videoPath;

    @JsonProperty("image")
    private String imagePath;

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
        this.userName = user;
        this.meta = meta;
        this.videoPath = "http://localhost:8080/media/" + id + ".mp4";
        this.imagePath = "http://localhost:8080/media/" + id + ".webp";
    }

    public Video() {
        this.id = ThreadLocalRandom.current().nextLong(100, 999999);;
    }

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

    public String getUserName() { return userName; }
    public void setUserName(String user) { this.userName = user; }

    public Meta getMeta() { return meta; }
    public void setMeta(Meta meta) { this.meta = meta; }

    public String getVideoPath() { return videoPath; }
    public void setVideoPath(String videoPath) { this.videoPath = videoPath; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}