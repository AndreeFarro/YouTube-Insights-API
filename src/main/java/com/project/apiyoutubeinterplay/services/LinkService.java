package com.project.apiyoutubeinterplay.services;

import com.project.apiyoutubeinterplay.dtos.LinkDTO;
import com.project.apiyoutubeinterplay.models.Link;

import java.util.List;

public interface LinkService {
    Link getLinkByIdAndChannelId(Long linkId, Long channelId);

    List<Link> getALlLinksByChannelId(Long channelId);

    void deleteById(Long channelId, Long linkId);

    Link createLink(Long channelId, LinkDTO linkDto);

    Link updateLink(Long channelId, Long linkId, LinkDTO linkDto);
}
