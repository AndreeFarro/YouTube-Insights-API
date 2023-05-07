package com.project.apiyoutubeinterplay.services.impl;

import com.project.apiyoutubeinterplay.dtos.LinkDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Link;
import com.project.apiyoutubeinterplay.repositories.ChannelRepository;
import com.project.apiyoutubeinterplay.repositories.LinkRepository;
import com.project.apiyoutubeinterplay.services.LinkService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {
    private final LinkRepository linkRepository;
    private final ChannelRepository channelRepository;
    private final ModelMapper mapper;

    public LinkServiceImpl(LinkRepository linkRepository, ChannelRepository channelRepository, ModelMapper modelMapper) {
        this.linkRepository = linkRepository;
        this.channelRepository = channelRepository;
        this.mapper = modelMapper;
    }

    @Override
    public Link getLinkByIdAndChannelId(Long linkId, Long channelId){
        return linkRepository
                .findByIdAndChannelId(linkId,channelId)
                .orElseThrow(()->new EntityNotFoundException(
                        "Link not Found with channel.id: " + channelId + " and link.id: " + linkId
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
    public List<Link> getALlLinksByChannelId(Long channelId){
        return linkRepository.findAllByChannelId(channelId);
    }

    @Override
    public Link createLink(Long channelId, LinkDTO linkDto){
        Channel existingChannel = getChannelById(channelId);
        Link link = mapper.map(linkDto,Link.class);
        link.setChannel(existingChannel);

        return linkRepository.save(link);
    }

    @Override
    public Link updateLink(Long channelId, Long linkId, LinkDTO linkDto){
        Link existingLink = getLinkByIdAndChannelId(linkId,channelId);
        mapper.map(linkDto, existingLink);

        return linkRepository.save(existingLink);
    }

    @Override
    public void deleteById(Long channelId, Long linkId){
        Link existingLink = getLinkByIdAndChannelId(linkId,channelId);
        linkRepository.delete(existingLink);
    }
}
