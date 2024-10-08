package com.tecnocampus.LS2.protube_back.application.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private int id;
    private String description;

    public void setText(String text) {
        description = text;
    }
}
