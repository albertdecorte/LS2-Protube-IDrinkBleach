package com.tecnocampus.LS2.protube_back.application.services;

import com.tecnocampus.LS2.protube_back.application.DTO.VideoDTO;
import com.tecnocampus.LS2.protube_back.domain.Video;
import com.tecnocampus.LS2.protube_back.persistance.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoService {
    private final VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public void saveVideo(Video video) {
        videoRepository.save(video);
    }

    public List<VideoDTO> getAllVideos() {
        return videoRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private VideoDTO convertToDTO(Video video) {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setId(video.getId());
        videoDTO.setWidth(video.getWidth());
        videoDTO.setHeight(video.getHeight());
        videoDTO.setDuration(video.getDuration());
        videoDTO.setTitle(video.getTitle());
        videoDTO.setUser(video.getUser());
        videoDTO.setDescription(video.getMeta().getDescription());
        videoDTO.setCategories(video.getMeta().getCategories());
        videoDTO.setTags(video.getMeta().getTags());
        videoDTO.setVideoPath(video.getVideoPath());
        videoDTO.setImagePath(video.getImagePath());
        return videoDTO;
    }

    public VideoDTO getVideoById(Long id) {
        Video video = videoRepository.findById(id);
        VideoDTO videoDTO = convertToDTO(video);
        return videoDTO;
    }

    public List<VideoDTO> getVideosWithCommentsByAuthor(String author) {
            List<Video> videos = videoRepository.findAll();
            List<VideoDTO> videosWithComments = new ArrayList<>();

            for (Video video : videos) {
                List<Video.Meta.Comment> videoComments = video.getMeta().getComments();
                List<VideoDTO.CommentDTO> authorComments = new ArrayList<>();

                for (Video.Meta.Comment comment : videoComments) {
                    if (comment.getAuthor().equalsIgnoreCase(author)) {
                        authorComments.add(new VideoDTO.CommentDTO(comment.getText(), comment.getAuthor()));
                    }
                }

                if (!authorComments.isEmpty()) {
                    VideoDTO videoDTO = new VideoDTO(
                            video.getId(), video.getWidth(), video.getHeight(),
                            video.getDuration(), video.getTitle(), video.getUser(),
                            video.getMeta().getDescription(), video.getMeta().getCategories(),
                            video.getMeta().getTags(), video.getVideoPath(), video.getImagePath(),
                            authorComments // Afegeix només els comentaris de l'autor
                    );
                    videosWithComments.add(videoDTO);
                }
            }
            return videosWithComments;
    }
}

