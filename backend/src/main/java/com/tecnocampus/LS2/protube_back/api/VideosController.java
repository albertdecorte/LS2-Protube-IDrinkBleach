package com.tecnocampus.LS2.protube_back.api;

import com.tecnocampus.LS2.protube_back.AppStartupRunner;
import com.tecnocampus.LS2.protube_back.ProtubeBackApplication;
import com.tecnocampus.LS2.protube_back.application.DTO.VideoDTO;
import com.tecnocampus.LS2.protube_back.application.services.VideoService;
import com.tecnocampus.LS2.protube_back.domain.Comment;
import com.tecnocampus.LS2.protube_back.domain.Video;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

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
    @GetMapping("/{videoId}/comments")
    public ResponseEntity<List<VideoDTO.CommentDTO>> getAllCommentsForVideo(@PathVariable Long videoId) {
        List<VideoDTO.CommentDTO> comments = videoService.getAllCommentsForVideo(videoId);
        return ResponseEntity.ok(comments);
    }
    @PostMapping("/{videoId}/comments")
    public ResponseEntity<Comment> addCommentToVideo(
            @PathVariable Long videoId,
            @RequestBody Comment comment,
            Principal principal // Obtindrà l'usuari autenticat
    ) {
        String username = principal.getName(); // Nom d'usuari des del context d'autenticació
        comment.setAuthor(username); // Assignar l'usuari com a autor del comentari
        Comment savedComment = videoService.addCommentToVideo(videoId, comment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }
    @GetMapping("/comments/authors")
    public ResponseEntity<Set<String>> getAllAuthors() {
        Set<String> authors = videoService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }
    @GetMapping("/comments/author/{author}")
    public ResponseEntity<List<VideoDTO.CommentDTO>> getAllCommentsByAuthor(@PathVariable String author) {
        List<VideoDTO.CommentDTO> comments = videoService.getAllCommentsByAuthor(author);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/upload")
    public ResponseEntity<Video> uploadVideo(
            @RequestBody VideoDTO videoDTO, // JSON payload with video details
            Principal principal
    ) {
        if (videoDTO.getVideoPath() == null || videoDTO.getVideoPath().isEmpty() ||
                videoDTO.getTitle() == null || videoDTO.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        videoDTO.setUser(principal.getName());
        Video savedVideo = videoService.addVideo(videoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVideo);
    }
}