package com.project.apiyoutubeinterplay.repositories;

import com.project.apiyoutubeinterplay.models.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Optional<Reaction> findByIdAndVideoId(Long id, Long videoId);
    List<Reaction> findAllByVideoId(Long videoId);
}
