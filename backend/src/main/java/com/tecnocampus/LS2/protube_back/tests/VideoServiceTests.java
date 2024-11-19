package com.tecnocampus.LS2.protube_back.tests;

import com.tecnocampus.LS2.protube_back.application.DTO.VideoDTO;
import com.tecnocampus.LS2.protube_back.application.services.VideoService;
import com.tecnocampus.LS2.protube_back.domain.Video;
import com.tecnocampus.LS2.protube_back.persistance.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VideoServiceTests {

    @InjectMocks
    private VideoService videoService;

    @Mock
    private VideoRepository videoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllVideos() {
        List<Video> videos = new ArrayList<>();
        videos.add(new Video(1L, 1920, 1080, 120.0, "Sample Video", "User", new Video.Meta("Description", new ArrayList<>(), new ArrayList<>(), new ArrayList<>())));

        when(videoRepository.findAll()).thenReturn(videos);

        List<VideoDTO> videoDTOs = videoService.getAllVideos();

        assertEquals("Sample Video", videoDTOs.get(0).getTitle());
    }

    @Test
    void testGetVideoById() {
        Video video = new Video(1L, 1920, 1080, 120.0, "Sample Video", "User", new Video.Meta("Description", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

        when(videoRepository.findById(1L)).thenReturn(video);

        VideoDTO videoDTO = videoService.getVideoById(1L);

        assertEquals("Sample Video", videoDTO.getTitle());
    }
}
