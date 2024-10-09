package com.tecnocampus.LS2.protube_back.api;

import com.tecnocampus.LS2.protube_back.application.DTO.VideoDTO;
import com.tecnocampus.LS2.protube_back.application.services.VideoService;
import com.tecnocampus.LS2.protube_back.domain.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideosController {
    private final VideoService videoService;

    @Autowired
    public VideosController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public List<VideoDTO> getAllVideos() {
        return videoService.getAllVideos();
    }

    @GetMapping("/generate-thumbnails")
    public String generateThumbnails() {
        try {
            List<String> generatedThumbnails = videoService.generateAllThumbnails();
            return "Generated thumbnails for: " + generatedThumbnails;
        } catch (Exception e) {
            return "Error generating thumbnails: " + e.getMessage();
        }
    }
}