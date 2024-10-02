package com.tecnocampus.LS2.protube_back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideosController {

    @GetMapping("")
    public ResponseEntity<List<String>> GetVideo(){
        return ResponseEntity.ok().body(List.of("hola"));
    }
}
