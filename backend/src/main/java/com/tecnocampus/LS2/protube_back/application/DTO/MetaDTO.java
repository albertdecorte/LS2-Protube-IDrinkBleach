package com.tecnocampus.LS2.protube_back.application.DTO;

import java.util.List;

public class MetaDTO {
    private String description;
    private List<String> categories;
    private List<String> tags;
    private List<VideoDTO.CommentDTO> comments;
    private Long id;

    public MetaDTO() {
    }

    public MetaDTO(String description, List<String> categories, List<String> tags, List<VideoDTO.CommentDTO> comments, Long id) {
        this.description = description;
        this.categories = categories;
        this.tags = tags;
        this.comments = comments;
        this.id = id;
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

    public List<VideoDTO.CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<VideoDTO.CommentDTO> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}