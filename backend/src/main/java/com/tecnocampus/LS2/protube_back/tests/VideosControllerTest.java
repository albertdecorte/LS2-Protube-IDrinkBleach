/*package com.tecnocampus.LS2.protube_back.tests;

import com.tecnocampus.LS2.protube_back.api.VideosController;
import com.tecnocampus.LS2.protube_back.application.DTO.VideoDTO;
import com.tecnocampus.LS2.protube_back.application.services.VideoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(VideosController.class)
public class VideosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private VideoService videoService;

    private List<VideoDTO> videoDTOList;

    @BeforeEach
    void setUp() {
        videoDTOList = new ArrayList<>();
        videoDTOList.add(new VideoDTO(1L, 1920, 1080, 120.0, "Sample Video", "User", "Description", new ArrayList<>(), new ArrayList<>(), "videoPath", "imagePath"));
    }

    @Test
    void testGetAllVideos() throws Exception {
        // Mock the service to return a list of video DTOs
        when(videoService.getAllVideos()).thenReturn(videoDTOList);

        // Perform GET request and validate the response
        mockMvc.perform(get("/api/videos/Json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))  // Check the length of the returned list
                .andExpect(jsonPath("$[0].title").value("Sample Video")); // Check the first video's title
    }

    @Test
    void testGetVideoById() throws Exception {
        when(videoService.getVideoById(1L)).thenReturn(videoDTOList.get(0));

        mockMvc.perform(get("/api/videos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Sample Video"))
                .andExpect(jsonPath("$.description").value("Description"));
    }

    @Test
    void testLoadInitialData() throws Exception {
        // Mock the service to return the list of videos
        when(videoService.getAllVideos()).thenReturn(videoDTOList);

        // Perform GET request and validate the response
        mockMvc.perform(get("/api/videos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))  // Check the length of the returned list
                .andExpect(jsonPath("$[0].title").value("Sample Video")); // Check the first video's title
    }

    @Test
    void testGetVideosWithCommentsByAuthor() throws Exception {
        // Mock the service to return a list of videos by a specific author
        when(videoService.getVideosWithCommentsByAuthor("User")).thenReturn(videoDTOList);

        // Perform GET request and validate the response
        mockMvc.perform(get("/api/videos/videos/comments/User"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))  // Check the length of the returned list
                .andExpect(jsonPath("$[0].title").value("Sample Video")); // Check the first video's title
    }
}*/
