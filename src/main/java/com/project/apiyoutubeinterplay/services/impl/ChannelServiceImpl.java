package com.project.apiyoutubeinterplay.services.impl;

import com.project.apiyoutubeinterplay.dtos.ChannelDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.repositories.ChannelRepository;
import com.project.apiyoutubeinterplay.services.ChannelService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final ModelMapper mapper;

    public ChannelServiceImpl(ChannelRepository channelRepository, ModelMapper modelMapper) {
        this.channelRepository = channelRepository;
        this.mapper = modelMapper;
    }

    @Override
    public List<Channel> getAll(){
        return channelRepository.findAll();
    }

    @Override
    public Channel getChannelById(Long id){
        return channelRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Channel not found with id: "+ id));
    }

    @Override
    public void deleteById(Long id) {
        Channel channel = getChannelById(id);
        channelRepository.deleteById(channel.getId());
    }

    @Override
    public Channel updateChannel(Long id, ChannelDTO channelDTO) {
        Channel existingChannel = getChannelById(id);
        mapper.map(channelDTO, existingChannel);

        return channelRepository.save(existingChannel);
    }

    @Override
    public Channel createChannel(ChannelDTO channelDTO) {
        Channel channel = mapper.map(channelDTO, Channel.class);
        return channelRepository.save(channel);
    }

    @Override
    public boolean isValidChannelByHandler(String handler) {
        return channelRepository.findByHandler(handler).isEmpty();
    }

}
