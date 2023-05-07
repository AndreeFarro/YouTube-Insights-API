package com.project.apiyoutubeinterplay.services.impl;

import com.project.apiyoutubeinterplay.dtos.LinkDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Link;
import com.project.apiyoutubeinterplay.mothers.dtos.LinkDTOMother;
import com.project.apiyoutubeinterplay.mothers.models.ChannelMother;
import com.project.apiyoutubeinterplay.mothers.models.LinkMother;
import com.project.apiyoutubeinterplay.repositories.ChannelRepository;
import com.project.apiyoutubeinterplay.repositories.LinkRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LinkServiceImplTest {
    @Mock
    private LinkRepository linkRepository;

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private LinkServiceImpl linkService;

    @Test
    void getLinkById() {
        Channel expectedChannel = ChannelMother.createChannelValid();
        Link expectedLink = LinkMother.createLinkValid(expectedChannel);

        given(linkRepository.findByIdAndChannelId(expectedLink.getId(),expectedChannel.getId())).willReturn(Optional.of(expectedLink));

        Link actualLink = linkService.getLinkByIdAndChannelId(expectedLink.getId(),expectedChannel.getId());

        assertThat(actualLink).isNotNull();
        assertThat(actualLink).isEqualTo(expectedLink);

    }
    @Test
    void getLinkById_shouldThrowExceptionEntityNotFound() {
        Long linkId = 1L;
        Long channelId = 2L;

        assertThatThrownBy(() -> linkService.getLinkByIdAndChannelId(linkId,channelId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getALlLinksByChannelId() {
        Channel expectedChannel = ChannelMother.createChannelValid();
        List<Link> linkList = LinkMother.createListLinksValid(expectedChannel);

        given(linkRepository.findAllByChannelId(expectedChannel.getId())).willReturn(linkList);

        List<Link> actualLinks = linkService.getALlLinksByChannelId(expectedChannel.getId());

        assertThat(actualLinks).isNotNull();
        assertThat(actualLinks).isEqualTo(linkList);
    }

    @Test
    void getChannelById(){
        Channel channel = ChannelMother.createChannelValid();

        given(channelRepository.findById(channel.getId())).willReturn(Optional.of(channel));

        Channel actualChannel = linkService.getChannelById(channel.getId());

        assertThat(actualChannel).isNotNull();
        assertEquals(actualChannel,channel);
    }

    @Test
    void getChannelById_shouldThrowExceptionEntityNotFound(){
        Long channelId = 1L;

        assertThatThrownBy(() -> linkService.getChannelById(channelId))
                .isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    void createLink() {

        Channel channel = ChannelMother.createChannelValid();
        Link link = LinkMother.createLinkValid(channel);
        LinkDTO linkDTO = LinkDTOMother.createLinkDTOValidFrom(link);

        given(channelRepository.findById(channel.getId())).willReturn(Optional.of(channel));
        given(mapper.map(linkDTO,Link.class)).willReturn(link);
        given(linkRepository.save(link)).willReturn(link);

        Link actualLink = linkService.createLink(channel.getId(),linkDTO);

        assertThat(actualLink).isNotNull();
        assertThat(actualLink).isEqualTo(link);
    }

    @Test
    void updateLink() {

        Channel channel = ChannelMother.createChannelValid();
        Link expectedLink = LinkMother.createLinkValid(channel);
        LinkDTO linkDTO = LinkDTOMother.createLinkDTOValidFrom(expectedLink);

        given(linkRepository.findByIdAndChannelId(expectedLink.getId(),channel.getId())).willReturn(Optional.of(expectedLink));
        willDoNothing().given(mapper).map(linkDTO, expectedLink);
        given(linkRepository.save(expectedLink)).willReturn(expectedLink);

        Link actualLink = linkService.updateLink(channel.getId(),expectedLink.getId(),linkDTO);

        assertThat(actualLink).isNotNull();
        assertEquals(actualLink.getName(),expectedLink.getName());
        verify(mapper).map(linkDTO, expectedLink);
    }

    @Test
    void deleteById() {
        Channel channel = ChannelMother.createChannelValid();
        Link link = LinkMother.createLinkValid(channel);

        given(linkRepository.findByIdAndChannelId(link.getId(),channel.getId())).willReturn(Optional.of(link));
        willDoNothing().given(linkRepository).delete(link);

        linkService.deleteById(channel.getId(),link.getId());

        verify(linkRepository).delete(link);

    }
}