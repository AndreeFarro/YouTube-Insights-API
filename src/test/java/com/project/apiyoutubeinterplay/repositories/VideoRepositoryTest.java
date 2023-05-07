package com.project.apiyoutubeinterplay.repositories;

import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Video;
import com.project.apiyoutubeinterplay.mothers.models.ChannelMother;
import com.project.apiyoutubeinterplay.mothers.models.VideoMother;
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
class VideoRepositoryTest {

    @Mock
    private VideoRepository videoRepository;

    @Test
    void findByIdAndChannelId() {
        Channel channel = ChannelMother.createChannelValid();
        Video video = VideoMother.createVideoValid(channel);

        given(videoRepository.findByIdAndChannelId(video.getId(), channel.getId())).willReturn(Optional.of(video));

        Video expectedVideo = videoRepository.findByIdAndChannelId(video.getId(), channel.getId()).get();

        assertNotNull(expectedVideo);
        assertEquals(expectedVideo,video);

    }

    @Test
    void findAllByChannelId() {
        Channel channel = ChannelMother.createChannelValid();
        List<Video> videos = VideoMother.createVideoListValid(channel);

        given(videoRepository.findAllByChannelId(channel.getId())).willReturn(videos);

        List<Video> expectedVideos = videoRepository.findAllByChannelId(channel.getId());

        assertNotNull(expectedVideos);
        assertEquals(expectedVideos,videos);

    }
}