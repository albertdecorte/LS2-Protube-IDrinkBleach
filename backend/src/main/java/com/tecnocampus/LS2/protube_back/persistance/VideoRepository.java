package com.tecnocampus.LS2.protube_back.persistance;

import aj.org.objectweb.asm.commons.Remapper;
import com.tecnocampus.LS2.protube_back.application.DTO.VideoDTO;
import com.tecnocampus.LS2.protube_back.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Video> findVideoById(Long videoId);

    List<Video> findByUserName(String userName);

    Optional<Video> findTopByOrderByIdDesc();
}
