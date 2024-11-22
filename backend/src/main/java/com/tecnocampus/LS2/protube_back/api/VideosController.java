package com.tecnocampus.LS2.protube_back.api;

import com.tecnocampus.LS2.protube_back.AppStartupRunner;
import com.tecnocampus.LS2.protube_back.ProtubeBackApplication;
import com.tecnocampus.LS2.protube_back.application.DTO.VideoDTO;
import com.tecnocampus.LS2.protube_back.application.services.VideoService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideosController {
    private final VideoService videoService;
    private final AppStartupRunner appStartupRunner;

    @Autowired
    public VideosController(VideoService videoService, AppStartupRunner appStartupRunner) {
        this.videoService = videoService;
        this.appStartupRunner = appStartupRunner;
    }

    @GetMapping("/Json")
    public List<VideoDTO> getAllVideos() {
        return videoService.getAllVideos();
    }

    @GetMapping("/{id}")
    public VideoDTO getVideoById(@PathVariable Long id) {
        return videoService.getVideoById(id);
    }

    @GetMapping
    public ResponseEntity<List<VideoDTO>> loadInitialData() {

        List<VideoDTO> videos = videoService.getAllVideos(); // Obtener todos los videos
        return ResponseEntity.ok(videos);  // Devolver los videos

    }
    @GetMapping("/comments/{author}")
    public ResponseEntity<List<VideoDTO>> getVideosWithCommentsByAuthor(@PathVariable String author) {
        List<VideoDTO> videosWithComments = videoService.getVideosWithCommentsByAuthor(author);
        return ResponseEntity.ok(videosWithComments);
    }
    @GetMapping("/comments/all")
    public ResponseEntity<List<VideoDTO.CommentDTO>> getAllCommentsFromVideos() {
        List<VideoDTO.CommentDTO> allComments = videoService.getAllCommentsFromVideos();
        return ResponseEntity.ok(allComments);
    }
}