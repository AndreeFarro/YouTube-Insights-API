package com.project.apiyoutubeinterplay.repositories;

import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Link;
import com.project.apiyoutubeinterplay.mothers.models.ChannelMother;
import com.project.apiyoutubeinterplay.mothers.models.LinkMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class LinkRepositoryTest {

    @Mock
    private LinkRepository linkRepository;

    @Test
    void findByIdAndChannelId() {
        Channel channel = ChannelMother.createChannelValid();
        Link link = LinkMother.createLinkValid(channel);

        given(linkRepository.findByIdAndChannelId(link.getId(), channel.getId())).willReturn(Optional.of(link));

        Link expectedLink = linkRepository.findByIdAndChannelId(link.getId(), channel.getId()).get();

        assertNotNull(expectedLink);
        assertEquals(expectedLink,link);
    }

    @Test
    void findAllByChannelId() {
        Channel channel = ChannelMother.createChannelValid();
        List<Link> links = LinkMother.createListLinksValid(channel);

        given(linkRepository.findAllByChannelId(channel.getId())).willReturn(links);

        List<Link> expectedLinks = linkRepository.findAllByChannelId(channel.getId());

        assertNotNull(expectedLinks);
        assertEquals(expectedLinks,links);
    }
}