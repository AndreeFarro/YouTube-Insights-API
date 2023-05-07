package com.project.apiyoutubeinterplay.services;

import com.project.apiyoutubeinterplay.dtos.VideoDTO;
import com.project.apiyoutubeinterplay.models.Video;

import java.util.List;

public interface VideoService {
    List<Video> getAllVideosByChannelId(Long channelId);

    Video getByVideoIdAndChannelId(Long videoId, Long channelId);

    void deleteById(Long channelId, Long videoId);

    Video createVideo(Long channelId, VideoDTO videoDto);

    Video updateVideo(Long channelId, Long videoId, VideoDTO videoDto);
}
