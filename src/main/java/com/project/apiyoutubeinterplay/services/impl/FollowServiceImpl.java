package com.project.apiyoutubeinterplay.services.impl;

import com.project.apiyoutubeinterplay.dtos.FollowDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Follow;
import com.project.apiyoutubeinterplay.repositories.ChannelRepository;
import com.project.apiyoutubeinterplay.repositories.FollowRepository;
import com.project.apiyoutubeinterplay.services.FollowService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final ChannelRepository channelRepository;
    private final ModelMapper mapper;

    public FollowServiceImpl(FollowRepository followRepository, ChannelRepository channelRepository, ModelMapper modelMapper) {
        this.followRepository = followRepository;
        this.channelRepository = channelRepository;
        this.mapper = modelMapper;
    }

    @Override
    public List<Follow> getAllFollowByChannelID(Long channelID){
        return followRepository.findAllByChannelId(channelID);
    }

    @Override
    public Follow getFollowByIdAndChannelId(Long followID,Long channelID){
        return followRepository
                .findByIdAndChannelId(followID,channelID)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Follow not Found with channel.id: " + channelID + " and follow.id: "+followID
                ));
    }

    public Channel getChannelById(Long channelID){
        return channelRepository
                .findById(channelID)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Follow not Found with channel.id: " + channelID
                ));
    }

    @Override
    public Follow createFollow(Long channelId, FollowDTO followDto){
        Channel channel = getChannelById(channelId);
        Follow follow = mapper.map(followDto, Follow.class);
        follow.setChannel(channel);

        return followRepository.save(follow);
    }

    @Override
    public Follow updateFollow(Long channelId, Long followId, FollowDTO followDto){
        Follow existingFollow = getFollowByIdAndChannelId(followId, channelId);
        mapper.map(followDto, existingFollow);

        return followRepository.save(existingFollow);
    }

    @Override
    public void deleteById(Long channelId, Long followId){
        Follow existingFollow = getFollowByIdAndChannelId(followId, channelId);
        followRepository.delete(existingFollow);
    }
}
