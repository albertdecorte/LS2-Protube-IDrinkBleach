package com.tecnocampus.LS2.protube_back.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "meta")
public class Meta {

    // Getters i Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(name = "description", columnDefinition = "CLOB", length = 100000)
    private String description;

    @ElementCollection
    private List<String> categories;

    @ElementCollection
    private List<String> tags;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "meta_id")
    private List<Comment> comments;

    public Meta(String description, List<String> categories, List<String> tags, List<Comment> comments) {
        this.description = description;
        this.categories = categories;
        this.tags = tags;
        this.comments = comments;
    }

    public Meta() {
    }

}

