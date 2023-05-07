package com.project.apiyoutubeinterplay.repositories;

import com.project.apiyoutubeinterplay.models.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByIdAndChannelId(Long id, Long channelId);
    List<Follow> findAllByChannelId(Long channelId);
}
