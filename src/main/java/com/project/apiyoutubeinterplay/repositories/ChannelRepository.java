package com.project.apiyoutubeinterplay.repositories;

import com.project.apiyoutubeinterplay.models.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel,Long> {
    Optional<Channel> findByHandler(String handler);
}
