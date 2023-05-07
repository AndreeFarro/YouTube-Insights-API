package com.project.apiyoutubeinterplay.services;

import com.project.apiyoutubeinterplay.dtos.FollowDTO;
import com.project.apiyoutubeinterplay.models.Follow;

import java.util.List;

public interface FollowService {

    List<Follow> getAllFollowByChannelID(Long channelID);

    Follow getFollowByIdAndChannelId(Long followID,Long channelID);

    void deleteById(Long channelId, Long followId);

    Follow createFollow(Long channelId, FollowDTO followDto);

    Follow updateFollow(Long channelId, Long followId, FollowDTO followDto);
}
