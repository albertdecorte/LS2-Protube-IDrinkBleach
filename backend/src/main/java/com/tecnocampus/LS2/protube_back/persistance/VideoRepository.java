package com.tecnocampus.LS2.protube_back.persistance;

import com.tecnocampus.LS2.protube_back.application.DTO.VideoDTO;
import com.tecnocampus.LS2.protube_back.domain.Video;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface VideoRepository {
    void save(Video video);
    List<Video> findAll();

    Video findById(Long videoId);

    List<Video> findByUser(String userName);

}
