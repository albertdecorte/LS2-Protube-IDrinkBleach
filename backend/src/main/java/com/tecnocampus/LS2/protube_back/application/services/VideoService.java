package com.tecnocampus.LS2.protube_back.application.services;

import com.tecnocampus.LS2.protube_back.application.DTO.VideoDTO;
import com.tecnocampus.LS2.protube_back.domain.Video;
import com.tecnocampus.LS2.protube_back.persistance.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
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

    private final String thumbnailDirectory = "src/main/resources/thumbnails/";
    // HEM DE CREAR UNA CARPETA A RESOURCES QUE ES DIGUI thumbnails

    public List<String> generateAllThumbnails() throws IOException, InterruptedException {
        List<Video> videos = videoRepository.findAll();  // Fetch all videos from the database
        List<String> generatedThumbnails = new ArrayList<>();

        for (Video video : videos) {
            try {
                String thumbnailPath = generateThumbnail(video);
                generatedThumbnails.add(video.getTitle() + ": " + thumbnailPath);
            } catch (Exception e) {
                System.err.println("Error generating thumbnail for video " + video.getTitle() + ": " + e.getMessage());
            }
        }

        return generatedThumbnails; // Return a list of successfully generated thumbnails
    }

    public String generateThumbnail(Video video) throws IOException, InterruptedException {
        String thumbnailPath = thumbnailDirectory + video.getId() + "_thumbnail.png";

        // Ensure the thumbnail directory exists
        File thumbnailDir = new File(thumbnailDirectory);
        if (!thumbnailDir.exists()) {
            thumbnailDir.mkdirs();
        }

        // Use FFmpeg to capture the first frame (screenshot) from the video
        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg", "-i", video.getVideoPath(), "-ss", "00:00:01.000", "-vframes", "1", thumbnailPath
        );

        // Start the process
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            return "/thumbnails/" + video.getId() + "_thumbnail.png"; // Return the relative path for frontend
        } else {
            throw new RuntimeException("Failed to generate thumbnail for video: " + video.getVideoPath());
        }
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
        return videoDTO;
    }
}

