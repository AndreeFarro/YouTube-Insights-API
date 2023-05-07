package com.project.apiyoutubeinterplay.services.impl;

import com.project.apiyoutubeinterplay.dtos.VideoDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Video;
import com.project.apiyoutubeinterplay.repositories.ChannelRepository;
import com.project.apiyoutubeinterplay.repositories.VideoRepository;
import com.project.apiyoutubeinterplay.services.VideoService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final ChannelRepository channelRepository;
    private final ModelMapper mapper;

    public VideoServiceImpl(VideoRepository videoRepository, ChannelRepository channelRepository, ModelMapper mapper) {
        this.videoRepository = videoRepository;
        this.channelRepository = channelRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Video> getAllVideosByChannelId(Long channelId){
        return videoRepository.findAllByChannelId(channelId);
    }

    @Override
    public Video getByVideoIdAndChannelId(Long videoId, Long channelId){
        return videoRepository
                .findByIdAndChannelId(videoId,channelId)
                .orElseThrow(()->new EntityNotFoundException(
                        "Video not Found with channel.id: "+channelId+" and video.id: "+ videoId
                ));
    }

    public Channel getChannelById(Long channelId){
        return channelRepository
                .findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Follow not Found with channel.id: " + channelId
                ));
    }

    @Override
    public Video createVideo(Long channelId, VideoDTO videoDto){
        Channel channel = getChannelById(channelId);
        Video video = mapper.map(videoDto, Video.class);
        video.setChannel(channel);

        return videoRepository.save(video);
    }

    @Override
    public Video updateVideo(Long channelId, Long videoId, VideoDTO videoDto){
        Video video = getByVideoIdAndChannelId(videoId, channelId);
        mapper.map(videoDto, video);

        return videoRepository.save(video);
    }

    @Override
    public void deleteById(Long channelId, Long videoId){
        Video existingVideo = getByVideoIdAndChannelId(videoId, channelId);
        videoRepository.delete(existingVideo);
    }
}
