package com.project.apiyoutubeinterplay.repositories;

import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.mothers.models.ChannelMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class ChannelRepositoryTest {

    @Mock
    private ChannelRepository channelRepository;

    @Test
    void findChannelByHandler() {
        Channel channel = ChannelMother.createChannelValid();
        given(channelRepository.findByHandler(channel.getHandler())).willReturn(Optional.of(channel));

        Channel existingChannel = channelRepository.findByHandler(channel.getHandler()).get();

        assertThat(existingChannel).isNotNull();
    }
}