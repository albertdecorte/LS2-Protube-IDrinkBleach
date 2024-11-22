package com.tecnocampus.LS2.protube_back.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "LONGTEXT", length = 100000)
    private String text;
    private String author;
    private String VideoTitle;

    public Comment(String text, String author,String videoTitle) {
        this.text = text;
        this.author = author;
        this.VideoTitle=videoTitle;
    }


    public Comment() {}
}
