package com.tecnocampus.LS2.protube_back.persistance;

import com.tecnocampus.LS2.protube_back.domain.Video;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InVideoRepository implements VideoRepository {
    private final List<Video> videos = new ArrayList<>();

    @Override
    public void save(Video video) {
        videos.add(video);
    }

    @Override
    public List<Video> findAll() {
        return new ArrayList<>(videos);
    }

    @Override
    public Optional<Video> findById(Long id) {
        return videos.stream()
                .filter(video -> video.getId().equals(id))
                .findFirst();  // Retorna un Optional<Video>
    }

    @Override
    public List<Video> findByUser(String userName) {
        return videos.stream()
                .filter(video -> video.getUser().equals(userName))
                .collect(Collectors.toList());
    }
}
