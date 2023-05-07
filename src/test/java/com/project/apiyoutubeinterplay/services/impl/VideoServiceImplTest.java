package com.project.apiyoutubeinterplay.services.impl;

import com.project.apiyoutubeinterplay.dtos.VideoDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Video;
import com.project.apiyoutubeinterplay.mothers.dtos.VideoDTOMother;
import com.project.apiyoutubeinterplay.mothers.models.ChannelMother;
import com.project.apiyoutubeinterplay.mothers.models.VideoMother;
import com.project.apiyoutubeinterplay.repositories.ChannelRepository;
import com.project.apiyoutubeinterplay.repositories.VideoRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VideoServiceImplTest {
    @Mock
    private VideoRepository videoRepository;

    @Mock
    private ModelMapper mapper;

    @Mock
    private ChannelRepository channelRepository;

    @InjectMocks
    private VideoServiceImpl videoService;

    @Test
    void getAllVideosByChannelId() {
        Channel channel = ChannelMother.createChannelValid();
        List<Video> videoList = VideoMother.createVideoListValid(channel);

        given(videoRepository.findAllByChannelId(channel.getId())).willReturn(videoList);

        List<Video> actualVideoList = videoService.getAllVideosByChannelId(channel.getId());

        assertThat(actualVideoList).isNotNull();
        assertEquals(actualVideoList,videoList);
    }

    @Test
    void getByVideoIdAndChannelId() {
        Channel channel = ChannelMother.createChannelValid();
        Video video = VideoMother.createVideoValid(channel);

        given(videoRepository.findByIdAndChannelId(video.getId(), channel.getId())).willReturn(Optional.of(video));

        Video actualVideo = videoService.getByVideoIdAndChannelId(video.getId(), channel.getId());

        assertThat(actualVideo).isNotNull();
        assertEquals(actualVideo,video);
    }
    @Test
    void getByVideoIdAndChannelId_shouldThrowExceptionEntityNotFound() {
        Long channelId = 1L;
        Long videoId = 2L;

        assertThatThrownBy(() -> videoService.getByVideoIdAndChannelId(videoId, channelId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getChannelById(){
        Channel channel = ChannelMother.createChannelValid();

        given(channelRepository.findById(channel.getId())).willReturn(Optional.of(channel));

        Channel actualChannel = videoService.getChannelById(channel.getId());

        assertThat(actualChannel).isNotNull();
        assertEquals(actualChannel,channel);
    }

    @Test
    void getChannelById_shouldThrowExceptionEntityNotFound(){
        Long channelId = 1L;

        assertThatThrownBy(() -> videoService.getChannelById(channelId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void createVideo() {
        Channel channel = ChannelMother.createChannelValid();
        Video video = VideoMother.createVideoValid(channel);
        VideoDTO videoDTO = VideoDTOMother.createVideoDTOValidFrom(video);

        given(channelRepository.findById(channel.getId())).willReturn(Optional.of(channel));
        given(mapper.map(videoDTO,Video.class)).willReturn(video);
        given(videoRepository.save(video)).willReturn(video);

        Video actualVideo = videoService.createVideo(channel.getId(),videoDTO);

        assertThat(actualVideo).isNotNull();
        assertEquals(actualVideo,video);
    }

    @Test
    void updateVideo() {
        Channel channel = ChannelMother.createChannelValid();
        Video video = VideoMother.createVideoValid(channel);
        VideoDTO videoDTO = VideoDTOMother.createVideoDTOValidFrom(video);

        given(videoRepository.findByIdAndChannelId(video.getId(), channel.getId())).willReturn(Optional.of(video));
        willDoNothing().given(mapper).map(videoDTO,video);
        given(videoRepository.save(video)).willReturn(video);

        Video actualVideo = videoService.updateVideo(channel.getId(), video.getId(),videoDTO);

        assertThat(actualVideo).isNotNull();
        assertEquals(actualVideo,video);
        verify(mapper).map(videoDTO,video);
    }

    @Test
    void deleteById() {
        Channel channel = ChannelMother.createChannelValid();
        Video video = VideoMother.createVideoValid(channel);

        given(videoRepository.findByIdAndChannelId(video.getId(), channel.getId())).willReturn(Optional.of(video));
        willDoNothing().given(videoRepository).delete(video);

        videoService.deleteById(channel.getId(), video.getId());

        verify(videoRepository).delete(video);
    }
}