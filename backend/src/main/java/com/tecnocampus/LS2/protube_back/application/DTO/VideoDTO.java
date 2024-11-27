package com.tecnocampus.LS2.protube_back.application.DTO;

import com.tecnocampus.LS2.protube_back.domain.Comment;
import com.tecnocampus.LS2.protube_back.domain.Video;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class VideoDTO {
    // Getters i Setters
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

    public VideoDTO(Video video) {
        this.id = video.getId();
        this.width = video.getWidth();
        this.height = video.getHeight();
        this.duration = video.getDuration();
        this.title = video.getTitle();
        this.user = video.getUserName();
        this.description = video.getMeta().getDescription();
        this.categories = video.getMeta().getCategories();
        this.tags = video.getMeta().getTags();
        this.videoPath = video.getVideoPath();
        this.imagePath = video.getImagePath();
    }

    public VideoDTO() {
    }

    @Setter
    @Getter
    public static class CommentDTO {
        private String text;
        private String author;
        //private String videoTitle;
        private Long videoId;

        public CommentDTO(String text, String author, Long videoId) {
            this.text = text;
            this.author = author;
            //this.videoTitle=videoTitle;
            this.videoId = videoId;
        }

        public CommentDTO(Comment comment) {
            this.text = comment.getText();
            this.author = comment.getAuthor();
        }

        /*
        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

         */
    }
}
