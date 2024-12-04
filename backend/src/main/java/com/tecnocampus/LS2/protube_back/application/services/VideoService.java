package com.tecnocampus.LS2.protube_back.application.services;

import ch.qos.logback.classic.tyler.TylerConfiguratorBase;
import com.tecnocampus.LS2.protube_back.application.DTO.VideoDTO;
import com.tecnocampus.LS2.protube_back.domain.Comment;
import com.tecnocampus.LS2.protube_back.domain.Meta;
import com.tecnocampus.LS2.protube_back.domain.Video;
import com.tecnocampus.LS2.protube_back.persistance.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final Environment env;
    private final Path rootPath;
    @Autowired
    public VideoService(VideoRepository videoRepository,Environment env) {
        this.videoRepository = videoRepository;
        this.env = env;
        final var rootDir = env.getProperty("pro_tube.store.dir");
        this.rootPath = Paths.get(rootDir);
    }

    public void saveVideo(Video video) {
        videoRepository.save(video);
    }

    public List<VideoDTO> getAllVideos() {
        return videoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public VideoDTO getVideoById(Long id) {
        return videoRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Video not found with id: " + id));
    }

    public List<VideoDTO> getVideosWithCommentsByAuthor(String author) {
        System.out.println("Searching comments by author: " + author);

        return videoRepository.findAll().stream()
                .distinct()
                .filter(video -> video.getMeta() != null && video.getMeta().getComments() != null)
                .map(video -> {
                    List<VideoDTO.CommentDTO> authorComments = video.getMeta().getComments().stream()
                            .filter(comment -> {
                                System.out.println("Checking comment author: " + comment.getAuthor());
                                return author.equalsIgnoreCase(comment.getAuthor());
                            })
                            .map(comment -> new VideoDTO.CommentDTO(comment.getText(), comment.getAuthor(),comment.getVideoId()))
                            .collect(Collectors.toList());

                    if (!authorComments.isEmpty()) {
                        VideoDTO videoDTO = convertToDTO(video);
                        videoDTO.setComments(authorComments);
                        return videoDTO;
                    }
                    return null;
                })
                .filter(videoDTO -> videoDTO != null)
                .collect(Collectors.toList());
    }

    private VideoDTO convertToDTO(Video video) {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setId(video.getId());
        videoDTO.setWidth(video.getWidth());
        videoDTO.setHeight(video.getHeight());
        videoDTO.setDuration(video.getDuration());
        videoDTO.setTitle(video.getTitle());
        videoDTO.setUser(video.getUserName());
        videoDTO.setComments(video.getMeta().getComments().stream().map(VideoDTO.CommentDTO::new).toList());

        if (video.getMeta() != null) {
            videoDTO.setDescription(video.getMeta().getDescription());
            videoDTO.setCategories(video.getMeta().getCategories());
            videoDTO.setTags(video.getMeta().getTags());
        }

        videoDTO.setVideoPath(video.getVideoPath());
        videoDTO.setImagePath(video.getImagePath());
        return videoDTO;
    }
    public List<VideoDTO.CommentDTO> getAllCommentsFromVideos() {
        return videoRepository.findAll().stream()
                .filter(video -> video.getMeta() != null && video.getMeta().getComments() != null)
                .flatMap(video -> video.getMeta().getComments().stream()
                        .map(comment -> new VideoDTO.CommentDTO(
                                comment.getText(),
                                comment.getAuthor(),
                                video.getId()))) // Incluimos el título del video
                .sorted(Comparator.comparing(VideoDTO.CommentDTO::getAuthor, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }
    public List<VideoDTO.CommentDTO> getAllCommentsForVideo(Long videoId) {
        return videoRepository.findById(videoId)
                .map(video -> video.getMeta().getComments().stream()
                        .map(comment -> new VideoDTO.CommentDTO(comment.getText(), comment.getAuthor(), video.getId()))
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new IllegalArgumentException("Video not found with id: " + videoId));
    }
    public Comment addCommentToVideo(Long videoId, Comment comment) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Vídeo no trobat"));

        // Inicialitzar la llista de comentaris si està nul·la
        if (video.getMeta().getComments() == null) {
            video.getMeta().setComments(new ArrayList<>());
        }

        video.getMeta().getComments().add(comment);
        videoRepository.save(video);
        return comment;
    }

    public Set<String> getAllAuthors() {
        return videoRepository.findAll().stream()
                .filter(video -> video.getMeta() != null && video.getMeta().getComments() != null)
                .flatMap(video -> video.getMeta().getComments().stream())
                .map(Comment::getAuthor)
                .collect(Collectors.toSet());
    }

    public List<VideoDTO.CommentDTO> getAllCommentsByAuthor(String author) {
        return videoRepository.findAll().stream()
                .filter(video -> video.getMeta() != null && video.getMeta().getComments() != null)
                .flatMap(video -> video.getMeta().getComments().stream()
                        .filter(comment -> comment.getAuthor().equalsIgnoreCase(author))
                        .map(comment -> new VideoDTO.CommentDTO(comment.getText(), comment.getAuthor(), video.getId())))
                .collect(Collectors.toList());
    }


    public Video addVideo(VideoDTO videoDTO) {
        Video video = new Video();
        video.setVideoPath(videoDTO.getVideoPath());
        video.setTitle(videoDTO.getTitle());
        video.setUserName(videoDTO.getUser());

        // Handle optional metadata
        Meta meta = new Meta();
        meta.setDescription(videoDTO.getDescription());
        meta.setCategories(videoDTO.getCategories() != null ? videoDTO.getCategories() : new ArrayList<>());
        meta.setTags(videoDTO.getTags() != null ? videoDTO.getTags() : new ArrayList<>());

        meta.setComments(new ArrayList<>()); // Empty comment list for new videos
        video.setMeta(meta);

        videoRepository.save(video);
        return video;
    }

    public Set<String> getAllVideoAuthors() {
        return videoRepository.findAll().stream()
                .filter(video -> video.getUserName() != null)
                .map(Video::getUserName)
                .collect(Collectors.toSet());
    }

    public List<VideoDTO> findVideosByAuthor(String author) {
        List<VideoDTO> videos = videoRepository.findAll().stream()
                .filter(video -> video.getUserName() != null && video.getUserName().equalsIgnoreCase(author))
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        if (videos.isEmpty()) {
            throw new IllegalArgumentException("No videos found for author: " + author);
        }

        return videos;
    }
    public Video addVideoWithFile(MultipartFile videoFile, MultipartFile thumbnailFile, String title, String description,
                                  List<String> categories, List<String> tags, String user) throws IOException {

        // Obtenir l'últim ID de la base de dades
        Long nextId = videoRepository.findTopByOrderByIdDesc()
                .map(Video::getId)
                .map(id -> id + 1) // Incrementar l'últim ID
                .orElse(1L); // Si no hi ha vídeos, comença amb 1

        // Crear un nou objecte Video
        Video video = new Video();
        video.setId(nextId); // Assignar ID
        video.setTitle(title);
        video.setUserName(user);

        // Desa el vídeo al directori principal
        File videoTargetFile = new File(rootPath.toFile(), nextId + "_" + videoFile.getOriginalFilename());
        videoFile.transferTo(videoTargetFile);

        // Assignar l'URL del vídeo
        String videoUrl = "http://localhost:8080/media/" + nextId + "_" + videoFile.getOriginalFilename();
        video.setVideoPath(videoUrl);

        // Desa el thumbnail si existeix
        if (thumbnailFile != null) {
            File thumbnailTargetFile = new File(rootPath.toFile(), nextId + "_thumbnail_" + thumbnailFile.getOriginalFilename());
            thumbnailFile.transferTo(thumbnailTargetFile);

            // Assignar l'URL del thumbnail
            String thumbnailUrl = "http://localhost:8080/media/" + nextId + "_thumbnail_" + thumbnailFile.getOriginalFilename();
            video.setImagePath(thumbnailUrl);
        }

        // Assignar metadades
        Meta meta = new Meta();
        meta.setDescription(description);
        meta.setCategories(categories != null ? categories : new ArrayList<>());
        meta.setTags(tags != null ? tags : new ArrayList<>());
        meta.setComments(new ArrayList<>()); // Llista buida per comentaris
        video.setMeta(meta);

        // Desa el vídeo a la base de dades
        return videoRepository.save(video);
    }


}
