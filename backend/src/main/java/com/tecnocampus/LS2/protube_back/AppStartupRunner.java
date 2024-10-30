package com.tecnocampus.LS2.protube_back;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.application.services.VideoService;
import com.tecnocampus.LS2.protube_back.domain.Video;
import com.tecnocampus.LS2.protube_back.persistance.VideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
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


    private final VideoRepository videoRepository;

    // Example variables from our implementation. 
    // Feel free to adapt them to your needs
    private final Environment env;
    private final Path rootPath;
    //private final ResourceLoader resourceLoader;
    private final Boolean loadInitialData;

    public AppStartupRunner(VideoRepository videoRepository, Environment env) {
        this.videoRepository = videoRepository;
        this.env = env;
        final var rootDir = env.getProperty("pro_tube.store.dir");
        this.rootPath = Paths.get(rootDir);
        this.loadInitialData = env.getProperty("pro_tube.load_initial_data", Boolean.class);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Should your backend perform any task during the bootstrap, do it here
        /*if (loadInitialData) {*/
            loadInitialData();

    }

    private void loadInitialData() {
        try {
            loadVideoData();
            loadThumbnailData();
        } catch (IOException e) {
            LOG.error("Failed to load initial data", e);
        }
    }

    private void loadVideoData() throws IOException {
        try (Stream<Path> paths = Files.walk(rootPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(this::processVideoFile);
        }
    }

    private void loadThumbnailData() throws IOException {
        try (Stream<Path> paths = Files.walk(rootPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".webp"))
                    .forEach(this::processThumbnailFile);
        }
    }

    private void processVideoFile(Path path) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Video video = objectMapper.readValue(path.toFile(), Video.class);
            videoRepository.save(video);
            LOG.info("Loaded video: " + video.getTitle());
        } catch (IOException e) {
            LOG.error("Failed to process video file: " + path, e);
        }
    }

    private void processThumbnailFile(Path path) {
        String filename = path.getFileName().toString();
        String videoIdStr = filename.replace(".webp", "");
        try {
            Long videoId = Long.parseLong(videoIdStr);
            Video video = videoRepository.findById(videoId);
            if (video != null) {
                String correctPath = "http://localhost:8080/media/" + videoId + ".webp";
                video.setImagePath(correctPath);

                videoRepository.save(video);
                LOG.info("Loaded thumbnail for video ID " + videoId + ": " + path);
            } else {
                LOG.warn("No video found for thumbnail ID: " + videoId);
            }
        } catch (NumberFormatException e) {
            LOG.error("Invalid video ID in thumbnail filename: " + filename, e);
        } catch (Exception e) {
            LOG.error("Failed to process thumbnail file: " + path, e);
        }

    }
}
