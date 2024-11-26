package com.tecnocampus.LS2.protube_back.tests;

import com.tecnocampus.LS2.protube_back.application.DTO.VideoDTO;
import com.tecnocampus.LS2.protube_back.application.services.VideoService;
import com.tecnocampus.LS2.protube_back.domain.Meta;
import com.tecnocampus.LS2.protube_back.domain.Video;
import com.tecnocampus.LS2.protube_back.persistance.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
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
        videos.add(new Video(1L, 1920, 1080, 120.0, "Sample Video", "User", new Meta("Description", new ArrayList<>(), new ArrayList<>(), new ArrayList<>())));

        when(videoRepository.findAll()).thenReturn(videos);

        List<VideoDTO> videoDTOs = videoService.getAllVideos();

        assertEquals("Sample Video", videoDTOs.get(0).getTitle());
    }

    @Test
    void testGetVideoById() {
        Video video = new Video(1L, 1920, 1080, 120.0, "Sample Video", "User", new Meta("Description", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));

        VideoDTO videoDTO = videoService.getVideoById(1L);

        assertEquals("Sample Video", videoDTO.getTitle());
    }

    @Test
    void testGetVideoByIdNotFound() {
        // Simula que el vídeo no existeix en el repositori
        when(videoRepository.findById(1L)).thenReturn(Optional.empty());

        // Es llança una excepció si no es troba el vídeo
        assertThrows(IllegalArgumentException.class, () -> videoService.getVideoById(1L));
    }

    @Test
    public void testAddVideo() {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setVideoPath("http://localhost:8080/media/3.mp4");
        videoDTO.setTitle("Test Video");
        videoDTO.setDescription("Description");
        videoDTO.setCategories(List.of("Category1", "Category2"));
        videoDTO.setTags(List.of("tag1", "tag2"));
        videoDTO.setUser("Fictional User 1");

        Video savedVideo = videoService.addVideo(videoDTO);

        assertNotNull(savedVideo.getId());
        assertEquals("Test Video", savedVideo.getTitle());
        assertEquals("Fictional User 1", savedVideo.getUserName());
    }
}