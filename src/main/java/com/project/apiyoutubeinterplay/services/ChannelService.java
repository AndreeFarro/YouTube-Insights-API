package com.project.apiyoutubeinterplay.services;

import com.project.apiyoutubeinterplay.dtos.ChannelDTO;
import com.project.apiyoutubeinterplay.models.Channel;

import java.util.Collection;
import java.util.List;

public interface ChannelService {

    List<Channel> getAll();

    Channel getChannelById(Long id);

    void deleteById(Long id);

    Channel updateChannel(Long id, ChannelDTO channelDTO);

    Channel createChannel(ChannelDTO channelDTO);

    boolean isValidChannelByHandler(String handler);
}
