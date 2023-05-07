package com.project.apiyoutubeinterplay.repositories;

import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Follow;
import com.project.apiyoutubeinterplay.mothers.models.ChannelMother;
import com.project.apiyoutubeinterplay.mothers.models.FollowMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class FollowRepositoryTest {

    @Mock
    private FollowRepository followRepository;

    @Test
    void findByIdAndChannelId() {
        Channel channel = ChannelMother.createChannelValid();
        Follow follow = FollowMother.createFollowValid(channel);

        given(followRepository.findByIdAndChannelId(follow.getId(), channel.getId())).willReturn(Optional.of(follow));
        Follow actualFollow = followRepository.findByIdAndChannelId(follow.getId(), channel.getId()).get();

        assertNotNull(actualFollow);
        assertEquals(actualFollow,follow);
    }

    @Test
    void findAllByChannelId() {
        Channel channel = ChannelMother.createChannelValid();
        List<Follow> follows = FollowMother.createListFollowsValid(channel);

        given(followRepository.findAllByChannelId(channel.getId())).willReturn(follows);

        List<Follow> actualFollows = followRepository.findAllByChannelId(channel.getId());

        assertNotNull(actualFollows);
        assertEquals(actualFollows,follows);

    }
}