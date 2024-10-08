package com.tecnocampus.LS2.protube_back;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.application.services.VideoService;
import com.tecnocampus.LS2.protube_back.domain.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class AppStartupRunner implements ApplicationRunner {
    private static final Logger LOG =
            LoggerFactory.getLogger(AppStartupRunner.class);


    private final VideoService videoService;

    // Example variables from our implementation. 
    // Feel free to adapt them to your needs
    private final Environment env;
    private final Path rootPath;
    private final Boolean loadInitialData;

    public AppStartupRunner(VideoService videoService, Environment env) {
        this.videoService = videoService;
        this.env = env;
        final var rootDir = env.getProperty("pro_tube.store.dir", "C:/Users/decor/Desktop/videos-10");
        this.rootPath = Paths.get(rootDir);
        this.loadInitialData = env.getProperty("pro_tube.load_initial_data", Boolean.class);

        // A function that obtains every video .json and stores the video
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Should your backend perform any task during the bootstrap, do it here
        if (loadInitialData) {
            loadInitialData();
        }
    }

    private void loadInitialData() {
        try (Stream<Path> paths = Files.walk(rootPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(this::processFile);
        } catch (IOException e) {
            LOG.error("Failed to load initial data", e);
        }
    }

    private void processFile(Path path) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Video video = objectMapper.readValue(path.toFile(), Video.class);
            videoService.saveVideo(video);
        } catch (IOException e) {
            LOG.error("Failed to process file: " + path, e);
        }
    }
}
