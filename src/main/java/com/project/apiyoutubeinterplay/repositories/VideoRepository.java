package com.project.apiyoutubeinterplay.repositories;

import com.project.apiyoutubeinterplay.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Video> findByIdAndChannelId(Long id, Long channelId);

    List<Video> findAllByChannelId(Long channelId);
}
