package com.tecnocampus.LS2.protube_back;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.application.services.VideoService;
import com.tecnocampus.LS2.protube_back.domain.Comment;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class AppStartupRunner implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(AppStartupRunner.class);

    private final VideoRepository videoRepository;
    private final Environment env;
    private final Path rootPath;
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
        loadInitialData();
    }

    private void loadInitialData() {
        try {
            loadVideoData();
            addCommentsToVideos();  // Afegim comentaris després de carregar els vídeos
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

    private void processVideoFile(Path path) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Video video = objectMapper.readValue(path.toFile(), Video.class);
            Optional<Video> existingVideo = videoRepository.findById(video.getId());

            if (!existingVideo.isPresent()) {
                videoRepository.save(video);
                LOG.info("Loaded new video: " + video.getTitle());
            } else {
                LOG.info("Video already exists: " + video.getTitle());
            }

        } catch (IOException e) {
            LOG.error("Failed to process video file: " + path, e);
        }
    }

    // Mètode per afegir comentaris als primers 4 vídeos
    private void addCommentsToVideos() {
        List<Video> videos = videoRepository.findAll(); // Obtinc tots els vídeos

        // Afegir comentaris als primers 4 vídeos
        for (int i = 0; i < 4 && i < videos.size(); i++) {
            Video video = videos.get(i);
            addCommentToVideo(video.getId(), new Comment("User1", "Great video! Loved it."));
            addCommentToVideo(video.getId(), new Comment("User2", "This was really helpful, thanks!"));
            addCommentToVideo(video.getId(), new Comment("User3", "Amazing content, very informative!"));
            addCommentToVideo(video.getId(), new Comment("User4", "I disagree with some points, but still good."));
            addCommentToVideo(video.getId(), new Comment("User5", "Looking forward to more videos like this!"));
        }
    }

    // Mètode per afegir un comentari a un vídeo
    private void addCommentToVideo(Long videoId, Comment comment) {
        // Obtenim el vídeo utilitzant .orElse(null) per obtenir el valor o null si no existeix
        Video video = videoRepository.findById(videoId).orElse(null);

        if (video != null) {
            // Afegim el comentari al vídeo
            video.getMeta().getComments().add(comment);
            videoRepository.save(video); // Guardem el vídeo amb els comentaris afegits
            LOG.info("Added comment to video: " + video.getTitle());
            LOG.info("Comment: " + video.getMeta().getComments().get(0).getText());
        } else {
            LOG.warn("Video with ID " + videoId + " not found.");
        }
    }

}