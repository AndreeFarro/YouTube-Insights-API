package com.project.apiyoutubeinterplay.repositories;

import com.project.apiyoutubeinterplay.models.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByIdAndChannelId(Long id, Long channelId);
    List<Link> findAllByChannelId(Long channelId);
}
